public class Duke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Duke() {
        ui = new Ui();
        storage = new Storage("data/tasks.txt");
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit;
            } catch (DukeException e) {
                ui.printException(e);
            }
        }
    }

    protected String getResponse(String input) {
        String response;
        boolean isExit = false;
        try {
            Command c = Parser.parse(input);
            c.execute(tasks, ui, storage);
            isExit = c.isExit;
            if (isExit) {
                return ui.showGoodByeMessage();
            }
            return ui.printOutputGUI();
        } catch (DukeException e) {
            return ui.printException(e);
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }

}
