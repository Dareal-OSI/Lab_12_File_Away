import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Lab 12 Part 2 - DataSaver
 * Collects person records with SafeInput, stores them as CSV strings in an ArrayList,
 * then writes them to a .csv file in the project's src folder.
 *
 * @author Darrell Lawson
 */
public class DataSaver
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        boolean more;

        do
        {
            String firstName = SafeInput.getNonZeroLenString(in, "Enter first name");
            String lastName = SafeInput.getNonZeroLenString(in, "Enter last name");
            String id = SafeInput.getRegExString(in, "Enter 6-digit ID number (e.g. 000001)", "\\d{6}");
            String email = SafeInput.getRegExString(in, "Enter email", "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
            int yob = SafeInput.getRangedInt(in, "Enter year of birth", 1000, 9999);

            String record = firstName + ", " + lastName + ", " + id + ", " + email + ", " + yob;
            records.add(record);
            System.out.println("Added: " + record);

            more = SafeInput.getYNConfirm(in, "Add another record?");
        } while (more);

        String fileName = SafeInput.getNonZeroLenString(in, "Enter a file name to save to (no extension)");
        if (!fileName.toLowerCase().endsWith(".csv"))
        {
            fileName += ".csv";
        }

        Path file = Paths.get(System.getProperty("user.dir") + File.separator + "src" + File.separator + fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(file))
        {
            for (String r : records)
            {
                writer.write(r);
                writer.newLine();
            }
            System.out.println("Saved " + records.size() + " records to " + file);
        }
        catch (IOException e)
        {
            System.out.println("Error writing the file: " + e.getMessage());
        }
    }
}
