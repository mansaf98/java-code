import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.*;

public class server1 {

    // static ServerSocket variable
    // private static ServerSocket server;
    // socket server port on which it will listen
    private static int port = 9876;

    public static void main(String args[]) throws IOException, ClassNotFoundException {
        // server = new ServerSocket(port);

        FileInputStream fis = new FileInputStream("questions.out");
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            int Points = 0;
            List<Question> ds = (List<Question>) ois.readObject();

            ds.size();
            System.out.println(ds.get(22).toString());
            Scanner myObj = new Scanner(System.in);

            System.out.println("Please Enter your Answer: ");
            int userName = myObj.nextInt();
            if (userName == (ds.get(4).getCorrectAnswer())) {
                Points = Points + ds.get(4).getPoints();
                System.out.println("Your Answer is correct !! and Your Points is " + Points);
            } else {
                System.out.println("Your Answer is incorrect !! and Your Points is " + Points);
            }

            System.out.println(ds.get(4).getCorrectAnswer());
            ois.close();

        }

        catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}