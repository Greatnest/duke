/**
 * Class that represents the exit command
 * Subclass of Command
 */
public class ExitCommand extends  Command{
    /**
     * Constructor that takes in a flag to represent if it should exit and the input given by the User
     * @param isExit True if the program should exit after running this command, false otherwise. Value should be true in this class
     * @param input Input given by the user
     */
    public ExitCommand(boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        ui.output = ui.showGoodByeMessage();
    }
}
