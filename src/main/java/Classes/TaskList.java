import java.util.ArrayList;

public class TaskList {

    private static ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.taskList = tasks;
    }

    public void addToArrayList(Task value) {
        this.taskList.add(value);
    }

    public Task deleteFromArrayList(int num) {
        return this.taskList.remove(num);
    }

    public static int getSize() {
        return taskList.size();
    }

    public static Task getTask(int num) {
        return taskList.get(num);
    }
}
