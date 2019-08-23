import java.lang.reflect.Array;
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
            if (input.startsWith("todo ")) {
                parseToDo(input.substring(5), taskList);
            } else if (input.startsWith("event ")) {
                parseEvent(input.substring(6), taskList);
            } else if (input.startsWith("deadline ")) {
                parseDeadline(input.substring(9), taskList);
            } else if (input.startsWith("done ")) {
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
                outputString += start + "." + value.toString();
            } else {
                outputString += start + "." + value.toString() + '\n';
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
        printMessage("Nice! I've marked this task as done: \n  " + item.toString());
    }

    public static void parseEvent(String input, ArrayList<Task> taskList ) {
        int dateIndex = input.indexOf("/at ");
        String by = input.substring(dateIndex+4);
        String task = input.substring(0, dateIndex-1);
        Event toAdd = new Event(task, by);
        taskList.add(toAdd);
        printMessage("Got it. I've added this task: \n  " + toAdd.toString() + "\nNow you have " + taskList.size() + " task(s) in the list.");
    }
    public static void parseDeadline(String input, ArrayList<Task> taskList ) {
        int dateIndex = input.indexOf("/by ");
        String at = input.substring(dateIndex+4);
        String task = input.substring(0, dateIndex-1);
        Deadline toAdd = new Deadline(task, at);
        taskList.add(toAdd);
        printMessage("Got it. I've added this task: \n  " + toAdd.toString() + "\nNow you have " + taskList.size() + " task(s) in the list.");
    }

    public static void parseToDo(String input, ArrayList<Task> taskList) {
        ToDo toAdd = new ToDo(input);
        taskList.add(toAdd);
        printMessage("Got it. I've added this task: \n  " + toAdd.toString() + "\nNow you have " + taskList.size() + " task(s) in the list.");
    }
}
