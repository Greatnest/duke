import java.util.Scanner;

public class Ui {
    private Scanner inputScanner;

    public Ui() {

    }

    public void showWelcome() {
        inputScanner = new Scanner(System.in);
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo + "Hello! I'm Duke\nWhat can I do for you?");

    }

    public String readCommand() {
        String input;
        if (inputScanner.hasNextLine()) {
            return inputScanner.nextLine();
        }
        return "";
    }

    public void showGoodByeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showLoadingError() {
        System.out.println("☹ OOPS!!! File not found or is empty. Creating a new task list!");
    }
    public void showError(String s) {
        System.out.println("Error: " + s);
    }

    public void printException(DukeException e) {
        System.out.println(e);
    }

    public void showMessage(String s) { System.out.println(s); }

    public void printList() throws DukeException {
        int taskListSize = TaskList.getSize();

        if (TaskList.getSize() == 0) {
            showMessage("You have no tasks in your list");
            return;
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
        showMessage(outputString);
    }

}
