
// Import the FileWriter class
// Import the IOException class to handle errors
import java.io.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class assignment2 {
    public static void main(String args[]) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            File myfile = new File("summary.txt");
            if (myfile.createNewFile()) {
                System.out.println("File created: " + myfile.getName());
            } else {
                System.out.println("File already exists.");
            }
            PrintWriter myfile1 = new PrintWriter("summary.txt");
            myfile1.println(dtf.format(now).toString());
            // myfile1.close();
            // PrintWriter pw = new PrintWriter("file3.txt");
            BufferedReader br1 = new BufferedReader(new FileReader("Grades.txt"));
            BufferedReader br2 = new BufferedReader(new FileReader("Names.txt"));
            String line1 = br1.readLine();
            String line2 = br2.readLine();
            while (line2 != null) {
                myfile1.println(line1);
                System.out.println("done");
                // line1 = br1.readLine();
                line2 = br2.readLine();
            }
            myfile1.flush();
            br1.close();
            br2.close();
            myfile1.close();
            System.out.println("Finished writing!");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
