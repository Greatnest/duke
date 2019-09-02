public class FindTaskCommand extends Command {
    public FindTaskCommand(boolean isExit, String input) {
        super(isExit, input);
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (input.length() < 6) {
            throw new DukeException("☹ OOPS!!! The task to find cannot be empty.");
        }

        input = input.substring(5);

        if (taskList.getSize() == 0) {
            throw new DukeException("You have no tasks in your list");
        }
        int start = 1;
        String outputString = "";
        boolean exists = false;
        outputString += "Here are the matching tasks in your list:\n";

        for (int i = 0; i < taskList.getSize(); ++i) {
            Task value = taskList.getTask(i);
            if (value.description.contains(input)) {
                outputString += start + "." + value.toString() + "\n";
                start++;
                exists = true;
            }
        }
        if (exists) {
            outputString = outputString.substring(0, outputString.length() - 1);
            ui.showMessage(outputString);
        } else {
            ui.showMessage("There are no matching tasks in your list.");
        }
    }

}
