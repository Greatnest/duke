import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Duke {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        ArrayList<Task> taskList = new ArrayList<>();
        readFromFile(taskList);
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        printMessage(logo);
        printMessage("Hello! I'm Duke\nWhat can I do for you?");

        String input;

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
        if (taskNumber > taskList.size()) {
            printMessage("You have entered a number larger than the number of tasks.");
            return;
        }
        Task item = taskList.get(taskNumber-1);
        if (item.getIsDone()) {
            printMessage("Task is already done.");
            return;
        }
        item.markAsDone();
        printMessage("Nice! I've marked this task as done: \n  " + item.toString());
        saveToFile(taskList);
    }

    public static void parseEvent(String input, ArrayList<Task> taskList ) {
        int dateIndex = input.indexOf("/at ");
        if (dateIndex == -1) {
            printMessage("☹ OOPS!!! Please indicate the event timing after \"/at\"");
            return;
        }
        String task = input.substring(0, dateIndex-1);

        String at = input.substring(dateIndex+4);

        LocalDateTime atValue = parseDate(at);
        if (atValue == null ) {
            return;
        }
        Event toAdd = new Event(task, atValue);
        taskList.add(toAdd);
        printMessage("Got it. I've added this task: \n  " + toAdd.toString() + "\nNow you have " + taskList.size() + " task(s) in the list.");
        saveToFile(taskList);
    }
    public static void parseDeadline(String input, ArrayList<Task> taskList ) {
        int dateIndex = input.indexOf("/by ");
        if (dateIndex == -1) {
            printMessage("☹ OOPS!!! Please indicate the deadline after \"/by\"");
            return;
        }
        String by = input.substring(dateIndex+4);
        String task = input.substring(0, dateIndex-1);
        LocalDateTime byValue = parseDate(by);
        if (byValue == null) {
            return;
        }
        Deadline toAdd = new Deadline(task, byValue);
        taskList.add(toAdd);
        printMessage("Got it. I've added this task: \n  " + toAdd.toString() + "\nNow you have " + taskList.size() + " task(s) in the list.");
        saveToFile(taskList);
    }

    public static void parseToDo(String input, ArrayList<Task> taskList) {
        ToDo toAdd = new ToDo(input);
        taskList.add(toAdd);
        printMessage("Got it. I've added this task: \n  " + toAdd.toString() + "\nNow you have " + taskList.size() + " task(s) in the list.");
        saveToFile(taskList);
    }
    public static void saveToFile(ArrayList<Task> taskList) {
        String fileName = "data/duke.txt";
        String toSave = "";
        createFileAndDirectory();

        for (Task value : taskList) {
            String taskType = "";
            String className = value.getClass().getSimpleName();
            int isDone = 0;
            String description = value.description;
            String newDate = "";

            if (className == "ToDo") {
                taskType = "T";
            } else if (className == "Deadline") {
                taskType = "D";
                newDate = unparseDate(((Deadline) value).by);
            } else if (className == "Event") {
                taskType = "E";
                newDate = unparseDate(((Event) value).at);
            }

            if (value.isDone) {
                isDone = 1;
            } else {
                isDone = 0;
            }
            if (newDate != "") {
                toSave += taskType + " | " + Integer.toString(isDone) + " | " + description + " | " + newDate + "\n";
            } else {
                toSave += taskType + " | " + Integer.toString(isDone) + " | " + description + "\n";
            }
        }
        try {
            Files.writeString(Paths.get(fileName), toSave, StandardOpenOption.CREATE);
        } catch (IOException e) {
            saveToFile(taskList);
        }
    }
    public static void readFromFile(ArrayList<Task> taskList) {
        try {
            if (Files.isDirectory(Paths.get("data")) && Files.isRegularFile(Paths.get("data/duke.txt"))) {
                List<String> input = Files.readAllLines(Paths.get("data/duke.txt"));
                for (String value : input) {
                    String[] splitInput = value.split(" \\| ");

                    if (value.charAt(0) == 'E') {
                        Event newEvent = new Event(splitInput[2], parseDate(splitInput[3]));
                        if (splitInput[1].equals("1")) {
                            newEvent.markAsDone();
                        }
                        taskList.add(newEvent);
                    } else if (value.charAt(0) == 'T') {
                        ToDo newToDo = new ToDo(splitInput[2]);
                        if (splitInput[1].equals("1")) {
                            newToDo.markAsDone();
                        }
                        taskList.add(newToDo);
                    } else if (value.charAt(0) == 'D') {
                        Deadline newDeadline = new Deadline(splitInput[2], parseDate(splitInput[3]));
                        if (splitInput[1].equals("1")) {
                            newDeadline.markAsDone();
                        }
                        taskList.add(newDeadline);
                    }
                }
            }
        } catch (IOException e) {
            createFileAndDirectory();
        }
    }
    public static LocalDateTime parseDate(String dateToParse) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HHmm");
            LocalDateTime dateTime = LocalDateTime.parse(dateToParse, formatter);
            return dateTime;
        } catch (DateTimeParseException e) {
            printMessage("☹ OOPS!!! Please format your date and time in this format \"20/12/2019 1859\"");
        }
        return null;
    }

    public static String unparseDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HHmm");
        return  dateTime.format(formatter);
    }

    public static  void createFileAndDirectory(){
        try {
            Files.createDirectory(Paths.get("data"));
            Files.createFile(Paths.get("data/duke.txt"));
        } catch(FileAlreadyExistsException e){
          return;
        } catch (IOException e) {
            createFileAndDirectory();
        }
    }
}
