import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        ArrayList<String> taskList = new ArrayList<>();

        printMessage("Hello I'm Duke\nWhat can I do for you?");

        String input;
        //should use nextLine and not next, else sentences will be split by spaces.
        while(!(input=inputScanner.nextLine()).equals("bye")){
            if (input.equals("list")) {
                printList(taskList);
            } else {
                taskList.add(input);
                printMessage("added: " + input);
            }
        }

        printMessage("Bye. Hope to see you again soon!");

    }

    public static void printMessage(String msg) {
        System.out.println(msg);
    }

    public static void printList(ArrayList<String> taskList) {
        int start = 1;
        String outputString = "";

        for (String value : taskList) {
            outputString += start + ". " + value + "\n";
            start++;
        }
        printMessage(outputString);
    }
}
