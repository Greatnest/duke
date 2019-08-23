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
                if (input.length() > 5) {
                    parseToDo(input.substring(5), taskList);
                } else {
                    printMessage("☹ OOPS!!! The description of a todo cannot be empty.");
                }
            } else if (input.startsWith("event ")) {
                if (input.length() > 6) {
                    parseEvent(input.substring(6), taskList);
                } else {
                    printMessage("☹ OOPS!!! The description of an event cannot be empty.");
                }
            } else if (input.startsWith("deadline ")) {
                if (input.length() > 9) {
                    parseDeadline(input.substring(9), taskList);
                } else {
                    printMessage("☹ OOPS!!! The description of a deadline cannot be empty.");
                }
            } else if (input.startsWith("done ")) {
                if (input.length() > 5) {
                    markTaskAsDone(input, taskList);
                } else {
                    printMessage("☹ OOPS!!! The task to be marked as done cannot be empty.");
                }
            } else if (input.equals("list")) {
                if (taskList.size() > 0) {
                    printList(taskList);
                } else {
                    printMessage("You have no tasks in your list.");
                }
            } else {
                printMessage("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
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
        if (dateIndex == -1) {
            printMessage("☹ OOPS!!! Please indicate the event timing after \"/at\"");
            return;
        }
        String by = input.substring(dateIndex+4);
        String task = input.substring(0, dateIndex-1);
        Event toAdd = new Event(task, by);
        taskList.add(toAdd);
        printMessage("Got it. I've added this task: \n  " + toAdd.toString() + "\nNow you have " + taskList.size() + " task(s) in the list.");
    }
    public static void parseDeadline(String input, ArrayList<Task> taskList ) {
        int dateIndex = input.indexOf("/by ");
        if (dateIndex == -1) {
            printMessage("☹ OOPS!!! Please indicate the deadline after \"/by\"");
            return;
        }
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
