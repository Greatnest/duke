public class ExitCommand extends  Command{

    public ExitCommand(boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        ui.output = ui.showGoodByeMessage();
    }
}
