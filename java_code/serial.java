
//yazan'assignment 0161943
import java.io.*;
import java.util.*;

public class serial {
    public static void main(String[] arg) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please enter number of students to be saved: ");
            int number = Integer.parseInt(reader.readLine());
            FileOutputStream fout = new FileOutputStream("studinfo.out");
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeInt(number);
            for (int i = 0; i < number; i++) {
                System.out.println("Student name :-");
                String name = reader.readLine();
                System.out.println("Student ID :-");
                int ID = Integer.parseInt(reader.readLine());
                System.out.println("Student GPA :-");
                double GPA = Double.parseDouble(reader.readLine());
                Student s = new Student(name, ID, GPA);
                oout.writeObject(s);
            }
            fout.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("studinfo.out"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt", true));
            int i = ois.readInt();
            // does it read ?
            // System.out.println(i);
            for (int x = 0; x < i; x++) {
                Student s = (Student) ois.readObject();
                // does it read tho ?
                // System.out.println(s);
                String sto = s.toString();
                // does it write tho ?
                // System.out.println(sto);
                writer.append(sto);

            }
            writer.flush();
            ois.close();
            writer.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        } catch (Exception e) {
            e.printStackTrace(); // handle this appropriately
        }

    }
}