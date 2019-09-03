import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddDeadLineCommand extends Command {

    public AddDeadLineCommand(boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (input.length() < 10) {
            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
        }
        input = input.substring(9);
        int dateIndex = input.indexOf("/by ");
        if (dateIndex == -1) {
            throw new DukeException("OOPS!!! Please indicate the deadline after \"/by\"");
        }

        String by = input.substring(dateIndex+4);
        String task = input.substring(0, dateIndex-1);
        LocalDateTime byValue = parseDate(by);
        if (byValue == null) {
            return;
        }
        Deadline toAdd = new Deadline(task, byValue);
        taskList.addToArrayList(toAdd);
        //ui.showMessage("Got it. I've added this task: \n  " + toAdd.toString() + "\nNow you have " + taskList.getSize() + " task(s) in the list.");
        ui.output = "Got it. I've added this task: \n  " + toAdd.toString() + "\nNow you have " + taskList.getSize() + " task(s) in the list.";
        storage.saveToFile();
    }

    private LocalDateTime parseDate(String dateToParse) throws DukeException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateToParse, formatter);
        } catch (DateTimeParseException e) {
            throw new DukeException("OOPS!!! Please format your date and time in this format \"20/12/2019 1859\"");
        }
    }

}
