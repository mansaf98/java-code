import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class assignmet2 {
    public static void main(String[] args) throws Exception, EOFException {

        try {
            byte[] array = new byte[1024];
            FileInputStream sourceFile = new FileInputStream("Names.txt");
            // FileOutputStream destFile = new FileOutputStream("summary.txt", true);
            DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("Grades.txt")));
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            // reads all data from input.txt
            sourceFile.read(array);
            // try {
            double grade;
            String stringofgrades;
            String grades[];
            String s;
            try {
                // read data from source, add to array of strings
                /*
                 * while(true){ grade = in.readDouble(); stringofgrades = String.valueOf(grade);
                 * System.out.println(stringofgrades); grades = stringofgrades.split("\\r?\\n");
                 * }
                 */
                s = new String(array);
                String lines[] = s.split("\\r?\\n");
                // print date
                FileWriter fw = new FileWriter("summary.txt", true);
                fw.write(dateFormat.format(date).toString() + "\n");
                // writes all data to newFile
                for (int i = 0; i < lines.length; i++) {
                    fw.write(lines[i] + "\n");
                }
                fw.close();
                System.out.println("Finished writing!");
                // closes the stream
                sourceFile.close();
            } catch (EOFException e1) {
                e1.getStackTrace();
            }

        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
