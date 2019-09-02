public class MarkTaskAsDoneCommand extends Command{

    public MarkTaskAsDoneCommand(Boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        int taskNumber;

        if (input.length() < 6) {
            throw new DukeException("☹ OOPS!!! The task to be marked as done cannot be empty.");
        }
        try {
            taskNumber = Integer.parseInt(input.substring(5));
        } catch (NumberFormatException e) {
            ui.showError("Please enter a number.");
            return;
        }

        if (taskNumber > taskList.getSize())  {
            throw new DukeException("The task number is larger than the number of tasks in the list");
        }

        Task item = taskList.getTask(taskNumber-1);
        if (item.getIsDone()) {
            throw new DukeException("Task is already done.");
        }
        item.markAsDone();
        ui.showMessage("Nice! I've marked this task as done: \n  " + item.toString());
        storage.saveToFile();
    }
}
