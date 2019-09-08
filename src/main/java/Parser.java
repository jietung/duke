import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a Parser that deals with making sense of the user command.
 */
public class Parser {
    private Ui ui = new Ui();

    /**
     * Making sense of done command.
     * @param str The String of command.
     * @param tasks The list of Task in TaskList.
     * @return The TaskList that has been changed.
     * @throws Exception If user does not input index.
     */
    public TaskList parseDone(String str, TaskList tasks) throws Exception {
        String[] strArr = str.split(" ");

        if (strArr.length == 1) { //no description
            throw new DukeException("OOPS!!! Please state the task number you want to mark as done.");
        }

        int index = Integer.parseInt(strArr[1]) - 1;

        if (index >= tasks.size()) {
            throw new DukeException("OOPS!!! The number is too large.");
        }
        if (index < 0) {
            throw new DukeException("OOPS!!! The number is too small.");
        }

        Task t = tasks.get(index);
        t.markAsDone();

        tasks.setOutput(ui.taskDoneMessage(t));

        return tasks;
    }

    /**
     * Making sense of Todo command.
     * @param str The String of command.
     * @param tasks The list of Task in TaskList.
     * @return The TaskList that has been changed.
     * @throws Exception If user does not input description of Todo.
     */
    public TaskList parseTodo(String str, TaskList tasks) throws Exception {
        String[] strArr = str.split(" ");
        if (strArr.length == 1) { //no description
            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
        }

        Task t = new Todo(str.substring(5));
        tasks.add(t);

        tasks.setOutput(ui.addTaskMessage(t, tasks));

        return tasks;
    }

    /**
     * Making sense of Deadline command.
     * @param str The String of command.
     * @param tasks The list of Task in TaskList.
     * @return The TaskList that has been changed.
     * @throws Exception If user does not input description, date or time.
     */
    public TaskList parseDeadline(String str, TaskList tasks) throws Exception {
        String[] strArr = str.split(" ");
        if (strArr.length == 1) {
            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
        }
        if (!str.contains(" /by ")) {
            throw new DukeException("OOPS!!! The date/time of deadline cannot be empty.");
        }

        int indexOfSlash = str.indexOf(47);
        String ss1 = str.substring(0, indexOfSlash); //description
        String[] ss1Arr = ss1.split(" ");
        String ss2 = str.substring(indexOfSlash); //date and time
        String[] ss2Arr = ss2.split(" ");

        if (ss1Arr.length == 1) { //no description
            throw new DukeException("OOPS!!! The description of deadline cannot be empty.");
        }
        if (ss2Arr.length < 3) { // no date or no time
            throw new DukeException("OOPS!!! The date/time of deadline cannot be empty.");
        }

        DateTimeHandler dateTimeHandler = new DateTimeHandler(ss2Arr);
        dateTimeHandler.parseDateTimeFromUserInput();
        Date date = dateTimeHandler.getDate();
        LocalTime time = dateTimeHandler.getTime();

        Task t = new Deadline(str.substring(9, indexOfSlash - 1), date, time);
        tasks.add(t);

        tasks.setOutput(ui.addTaskMessage(t, tasks));

        return tasks;
    }

    /**
     * Making sense of Event command.
     * @param str The String of command.
     * @param tasks The list of Task in TaskList.
     * @return The TaskList that has been changed.
     * @throws Exception If user does not input description, date or time.
     */
    public TaskList parseEvent(String str, TaskList tasks) throws Exception {
        String[] strArr = str.split(" ");
        if (strArr.length == 1) {
            throw new DukeException("OOPS!!! The description of a event cannot be empty.");
        }
        if (!str.contains(" /at ")) {
            throw new DukeException("OOPS!!! The date/time of event cannot be empty.");
        }

        int indexOfSlash = str.indexOf(47);
        String ss1 = str.substring(0, indexOfSlash);
        String[] ss1Arr = ss1.split(" "); //description
        String ss2 = str.substring(indexOfSlash);
        String[] ss2Arr = ss2.split(" "); //date and time

        if (ss1Arr.length == 1) { //no description
            throw new DukeException("OOPS!!! The description of event cannot be empty.");
        }
        if (ss2Arr.length < 3) { //no date or no time
            throw new DukeException("OOPS!!! The date/time of event cannot be empty.");
        }

        DateTimeHandler dateTimeHandler = new DateTimeHandler(ss2Arr);
        dateTimeHandler.parseDateTimeFromUserInput();
        Date date = dateTimeHandler.getDate();
        LocalTime time = dateTimeHandler.getTime();

        Task t = new Event(str.substring(6, indexOfSlash - 1), date, time);
        tasks.add(t);

        tasks.setOutput(ui.addTaskMessage(t, tasks));

        return tasks;
    }

    /**
     * Making sense of Delete command.
     * @param str The String of command.
     * @param tasks The list of Task in TaskList.
     * @return The TaskList that has been changed.
     * @throws Exception If user does not input description, date or time.
     */
    public TaskList parseDelete(String str, TaskList tasks) throws Exception {
        String[] strArr = str.split(" ");

        if (strArr.length == 1) {
            throw new DukeException("OOPS!!! Please state the task number you want to delete.");
        }

        int index = Integer.parseInt(strArr[1]) - 1;

        if (index >= tasks.size()) {
            throw new DukeException("OOPS!!! The number is too large.");
        }
        if (index < 0) {
            throw new DukeException("OOPS!!! The number is too small.");
        }

        Task t = tasks.delete(index);

        tasks.setOutput(ui.deleteTaskMessage(t, tasks));

        return tasks;

    }

    /**
     * Making sense of Find command.
     * @param str The String of command.
     * @param tasks THe lis of Task in TaskList.
     * @return The TaskList that contains the keyword.
     * @throws Exception If user does not input keyword.
     */
    public TaskList parseFind(String str, TaskList tasks) throws Exception {
        String[] strArr = str.split(" ");

        if (strArr.length == 1) {
            throw new DukeException("OOPS!!! Please state the keyword you want to find.");
        }

        ArrayList<Task> listOfTask = tasks.getList();
        ArrayList<Task> listOfTaskContainsKeyword = new ArrayList<>();

        for (Task t : listOfTask) {
            if (t.toString().contains(strArr[1])) {
                listOfTaskContainsKeyword.add(t);
            }
        }

        return new TaskList(listOfTaskContainsKeyword);
    }
}
