import java.util.ArrayList;

public class Command {

    public boolean isExit;
    protected String input;

    public Command(boolean isExit, String input) {
        this.isExit = isExit;
        this.input = input;
    }

    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {

    }
}
