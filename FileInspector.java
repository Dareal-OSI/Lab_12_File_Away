import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Lab 12 Part 1 - FileInspector
 * Lets the user pick a text file with JFileChooser, echoes every line,
 * then prints a summary: file name, line count, word count, character count.
 *
 * @author Darrell Lawson
 */
public class FileInspector
{
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        // open the chooser in the project's src folder
        File workingDirectory = new File(System.getProperty("user.dir") + File.separator + "src");
        chooser.setCurrentDirectory(workingDirectory);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            inspect(chooser.getSelectedFile().toPath());
        }
        else
        {
            System.out.println("No file was chosen. Run the program again to pick a file.");
        }
    }

    /** Reads the file line by line, echoes it, and prints the summary report. */
    public static void inspect(Path file)
    {
        int lines = 0;
        int words = 0;
        int chars = 0;

        try (BufferedReader reader = Files.newBufferedReader(file))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                lines++;
                System.out.printf("Line %4d: %s%n", lines, line);

                String trimmed = line.trim();
                if (!trimmed.isEmpty())
                {
                    words += trimmed.split("\\s+").length;
                }
                chars += line.length();
            }
        }
        catch (IOException e)
        {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        System.out.println();
        System.out.println("========== Summary Report ==========");
        System.out.println("File name:            " + file.getFileName());
        System.out.println("Number of lines:      " + lines);
        System.out.println("Number of words:      " + words);
        System.out.println("Number of characters: " + chars);
        System.out.println("====================================");
    }
}
