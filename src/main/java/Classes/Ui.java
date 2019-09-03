import java.util.Scanner;

public class Ui {
    public String output;

    public Ui() {

    }

    public String printOutputGUI() {
        return output;
    }

    public void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo + "Hello! I'm Duke\nWhat can I do for you?");

    }

    public String readCommand() {
        String input;
        Scanner inputScanner = new Scanner(System.in);

        return inputScanner.nextLine();
    }

    public String showGoodByeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    public void showLoadingError() {
        System.out.println("OOPS!!! File not found or is empty. Creating a new task list!");
    }
    public void showError(String s) {
        System.out.println("Error: " + s);
    }

    public String printException(DukeException e) {
        return e.getMessage();
    }

    public void showMessage(String s) { System.out.println(s); }

    public String printList() throws DukeException {
        int taskListSize = TaskList.getSize();

        if (TaskList.getSize() == 0) {
            return "You have no tasks in your list";
        }
        int start = 1;
        String outputString = "";
        outputString += "Here are the tasks in your list:\n";

        for (int i = 0; i < taskListSize; ++i) {
            if (start == taskListSize) {
                outputString += start + "." + TaskList.getTask(i).toString();
            } else {
                outputString += start + "." + TaskList.getTask(i).toString() + '\n';
                start++;
            }
        }
        return outputString;
    }

}
