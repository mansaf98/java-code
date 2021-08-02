
//yazan mohammad hassan hussein
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class gradestest {
    public static void main(String[] args) throws IOException {
        byte[] array = new byte[1024];
        FileInputStream sourceFile = new FileInputStream("Names.txt");
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("Grades.txt")));
        DataInputStream in2 = new DataInputStream(new BufferedInputStream(new FileInputStream("Names.txt")));
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("summary.txt")));
        double grade;
        String name;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            out.writeUTF(dateFormat.format(date).toString() + "\n");

            while (true) {
                grade = in.readDouble();
                name = in2.readLine();
                out.writeUTF(name.toString() + "\t" + String.valueOf(grade) + "\n");

            }
        } catch (IOException e) {
            out.flush();
            out.close();
            in.close();
            in2.close();
            System.out.print("Finished writing!");
        }
    }
}