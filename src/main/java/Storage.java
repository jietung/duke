import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a Storage that deals with loading tasks from the file and saving tasks in the file.
 */
public class Storage {
    private String fileName;

    /**
     * Creates a new Storage with given filename.
     * @param fileName The tasks will load and save from the given file.
     */
    public Storage(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Load tasks from the file saved in hard drive.
     * @return The list of tasks.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String str;

            while ((str = bufferedReader.readLine()) != null) {
                Task t;
                if (str.charAt(1) == 'T') {
                    t = new Todo(str.substring(7));
                    if (str.charAt(4) == '\u2713') { //mark task as done
                        t.markAsDone();
                    }
                    tasks.add(t);
                } else if (str.charAt(1) == 'D') {
                    int indexOfBracket = str.indexOf(58);
                    String ss = str.substring(indexOfBracket);
                    String[] ssArr = ss.split(" ");

                    SimpleDateFormat parser = new SimpleDateFormat("dd-MMM-yyyy");
                    Date date = parser.parse(ssArr[1]);

                    String strTime = ssArr[2].substring(0, 7);
                    LocalTime time = LocalTime.parse(strTime, DateTimeFormatter.ofPattern("hh:mma"));

                    t = new Deadline(str.substring(7, indexOfBracket - 4), date, time);
                    if (str.charAt(4) == '\u2713') { //mark task as done
                        t.markAsDone();
                    }
                    tasks.add(t);
                } else {
                    int index = str.indexOf(58);
                    String ss = str.substring(index);
                    String[] ssArr = ss.split(" ");

                    SimpleDateFormat parser = new SimpleDateFormat("dd-MMM-yyyy");
                    Date date = parser.parse(ssArr[1]);

                    String strTime = ssArr[2].substring(0, 7);
                    LocalTime time = LocalTime.parse(strTime, DateTimeFormatter.ofPattern("hh:mma"));

                    t = new Event(str.substring(7, index - 4), date, time);
                    if (str.charAt(4) == '\u2713') { //mark task as done
                        t.markAsDone();
                    }
                    tasks.add(t);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    /**
     * Save the tasks into the hard drive.
     * @param tasks The list of tasks.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            FileWriter writer = new FileWriter(fileName, false);
            BufferedWriter bufferedWriter =  new BufferedWriter(writer);

            for (Task t : tasks) {
                bufferedWriter.write(t.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
