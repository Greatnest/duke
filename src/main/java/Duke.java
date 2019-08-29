import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        printMessage("Hello I'm Duke\nWhat can I do for you?");

        String input;

        while(!(input=inputScanner.nextLine()).equals("bye")){
            printMessage((input));
        }

        printMessage("Bye. Hope to see you again soon!");

    }

    public static void printMessage(String msg) {
        System.out.println(msg);
    }
}
