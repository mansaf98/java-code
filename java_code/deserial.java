import java.io.*;

public class deserial {
    public static void main(String[] arg) throws IOException, ClassNotFoundException {
        Student si = null;
        try {
            FileInputStream fis = new FileInputStream("studinfo.out");
            ObjectInputStream ois = new ObjectInputStream(fis);
            si = (Student) ois.readObject();
            System.out.println(si.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(si.toString());
        // System.out.println(si.rid);
        // System.out.println(si.contact);
    }
}