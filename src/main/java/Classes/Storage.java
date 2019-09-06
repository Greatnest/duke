import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws DukeException {
        ArrayList<Task> taskList = new ArrayList<>();
        try {
            if (Files.isRegularFile(Paths.get(this.filePath))) {
                List<String> input = Files.readAllLines(Paths.get(this.filePath));

                if (input.isEmpty()) throw new DukeException("");
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
            } else {
                throw new DukeException("");
            }
        } catch (IOException e) {
            createFileAndDirectory();
        }
        return taskList;
    }

    private void createFileAndDirectory() {
            try {
                File myNewFile = new File(this.filePath);
                Files.createDirectory(Paths.get(myNewFile.getParent()));
                Files.createFile(Paths.get(this.filePath));
            } catch(FileAlreadyExistsException e){
                return;
            } catch (IOException e) {
                createFileAndDirectory();
            }
    }
    private LocalDateTime parseDate(String dateToParse) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateToParse, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date read from file.");
            return null;
        }
    }

    private String unparseDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return dateTime.format(formatter);
    }

    public void saveToFile() {
        String toSave = "";
        createFileAndDirectory();

        for (int i = 0; i < TaskList.getSize(); ++i) {
            Task value = TaskList.getTask(i);
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
            Files.writeString(Paths.get(this.filePath), toSave);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
