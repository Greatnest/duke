public class AddToDoCommand extends Command{

    public AddToDoCommand(Boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (input.length() < 6) {
            throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
        }

        input = input.substring(5);

        ToDo toAdd = new ToDo(input);
        taskList.addToArrayList(toAdd);
        ui.showMessage("Got it. I've added this task: \n  " + toAdd.toString() + "\nNow you have " + taskList.getSize() + " task(s) in the list.");
        storage.saveToFile();
    }

}
