import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();

        printMessage("Hello! I'm Duke\nWhat can I do for you?");

        String input;
        //should use nextLine and not next, else sentences will be split by spaces.
        while(!(input=inputScanner.nextLine()).equals("bye")){
            if (input.startsWith("done ")) {
                markTaskAsDone(input, taskList);
            } else if (input.equals("list")) {
                printList(taskList);
            } else {
                taskList.add(new Task(input));
                printMessage("added: " + input);
            }
        }

        printMessage("Bye. Hope to see you again soon!");
    }

    public static void printMessage(String msg) {
        System.out.println(msg);
    }

    public static void printList(ArrayList<Task> taskList) {
        int start = 1;
        String outputString = "";
        outputString += "Here are the tasks in your list:\n";

        for (Task value : taskList) {
            if (start == taskList.size()) {
                outputString += start + ".[" + value.getStatusIcon() + "] " + value.getDescription();
            } else {
                outputString += start + ".[" + value.getStatusIcon() + "] " + value.getDescription() + '\n';
                start++;
            }
        }
        printMessage(outputString);
    }

    public static void markTaskAsDone(String input, ArrayList<Task> taskList) {
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(input.substring(5));
        } catch (NumberFormatException e) {
            printMessage("Please enter a number.");
            return;
        }

        Task item = taskList.get(taskNumber-1);
        if (item.getIsDone()) {
            printMessage("Task is already done.");
            return;
        }
        item.markAsDone();
        printMessage("Nice! I've marked this task as done: \n  [" + item.getStatusIcon() + "] " + item.getDescription());
    }
}
