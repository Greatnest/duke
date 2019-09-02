public class Parser {

    public Parser() {

    }

    public static Command parse(String input) throws DukeException {
        if (input.startsWith("todo ")) {
            return new AddToDoCommand(false, input);
        } else if (input.startsWith("event ")) {
            return new AddEventCommand(false, input);
        } else if (input.startsWith("deadline ")) {
            return new AddDeadLineCommand(false, input);
        } else if (input.startsWith("done ")) {
            return new MarkTaskAsDoneCommand(false, input);
        } else if (input.equals("list")) {
            return new ListTaskCommand(false, "");
        }  else if (input.startsWith("find ")) {
            return new FindTaskCommand(false, input);
        } else if (input.startsWith("delete ")) {
            return new DeleteTaskCommand(false, input);
        } else if (input.equals("bye")) {
            return new ExitCommand(true, "");
        } else {
            throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
