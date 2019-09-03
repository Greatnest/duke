import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddEventCommand extends Command {

    public AddEventCommand(Boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (this.input.length() < 7) {
            throw new DukeException("OOPS!!! The description of an event cannot be empty.");
        }
        input = input.substring(6);

        int dateIndex = input.indexOf("/at ");
        if (dateIndex == -1) {
            throw new DukeException("OOPS!!! Please indicate the event timing after \"/at\"");

        }
        String task = input.substring(0, dateIndex-1);

        String at = input.substring(dateIndex+4);

        LocalDateTime atValue = parseDate(at);
        if (atValue == null) {
            return;
        }
        Event toAdd = new Event(task, atValue);
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
            throw new DukeException("OOPS!!! Please format your date and time in this format \"20/12/2019 1859\".");
        }
    }
}

