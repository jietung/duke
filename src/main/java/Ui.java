/**
 * Represents user interface that deals with interactions with user.
 */
public class Ui {
    private TaskList tasks;

    public Ui() {}

    public Ui(TaskList tasks) {
        this.tasks = tasks;
    }

    public TaskList getTaskList() {
        return tasks;
    }

    /**
     * Returns welcome message.
     * @return Welcome message.
     */
    public String welcomeMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        return "Hello! I'm Duke.\nWhat can I do for you?\n";
    }

    /**
     * Returns goodbye message.
     * @return Goodbye message.
     */
    public String goodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns the TaskList in order.
     * @return The message of list of tasks.
     */
    public String taskListMessage() {
        assert tasks == null : "List of tasks should not be null";

        if (tasks.size()  == 0) {
            return "There is no task in your list.\n";
        }

        String s = "Here are the tasks in your list:\n";

        int i = 1;
        for (Task t : tasks.getList()) {
            s += i + ". " + t + "\n";
            i++;
        }

        return s;
    }

    /**
     * Takes in Command, parse it and returns the changed list.
     * @param str The String of command.
     * @return The list of task.
     * @throws Exception If command is missing input.
     */
    public TaskList input(String str) throws Exception {
        String[] strArr = str.split(" ");
        Parser parser = new Parser();

        if (strArr[0].equals("list")) {
            tasks.setOutput(taskListMessage());
        } else if (strArr[0].equals("done")) {
            tasks = parser.parseDone(str, tasks);
        } else if (strArr[0].equals("todo")) {
            tasks = parser.parseTodo(str, tasks);
        } else if (strArr[0].equals("deadline")) {
            tasks = parser.parseDeadline(str, tasks);
        } else if (strArr[0].equals("event")) {
            tasks = parser.parseEvent(str, tasks);
        } else if (strArr[0].equals("delete")) {
            tasks = parser.parseDelete(str, tasks);
        } else if (strArr[0].equals("find")) {
            tasks.setOutput(matchingTaskMessage(parser.parseFind(str, tasks)));
        } else if (strArr[0].equals("bye")) {
            tasks.setOutput(goodbyeMessage());
        } else {
            throw new DukeException("OOPS!!! I,m sorry, but I don't know what that means :-(");
        }

        return tasks;
    }

    /**
     * Returns the message of task added.
     * @param t The Task that is added into TaskList.
     * @param tasks The TaskList of user.
     * @return The string of added task.
     */
    public String addTaskMessage(Task t, TaskList tasks) {
        assert t == null : "The task added should not be null";
        return "Got it. I've added this task:\n" + "  " + t + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    /**
     * Return the message of task mark as done.
     * @param t The Task that is marked as done.
     * @return The string of task mark as done.
     */
    public String taskDoneMessage(Task t) {
        assert t == null : "The task done should not be null";
        return "Nice! I've marked this task as done:\n\t" + t;
    }

    /**
     * Returns the message of task that is deleted.
     * @param t The Task deleted.
     * @param tasks The TaskList of user.
     * @return The string of task deleted.
     */
    public String deleteTaskMessage(Task t, TaskList tasks) {
        assert t == null : "The task deleted should not be null";
        return "Noted! I've removed this task:\n" + t + "\nNow you have " + tasks.size() + " tasks in the list.\n";
    }

    /**
     * Returns all Task that match the keyword.
     * @param tasks The TaskList of matched Task.
     * @return The string of all task that match the keyword.
     */
    public String matchingTaskMessage(TaskList tasks) {
        assert tasks == null : "The list of tasks should not be null";

        if (tasks.size() == 0) {
            return "There is no matching tasks in your list.\n";
        }

        String s = "Here are the matching tasks in your list:\n";

        int i = 1;
        for (Task t : tasks.getList()) {
            s += i + ". " + t + "\n";
            i++;
        }

        return s;
    }
}
