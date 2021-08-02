
//this is a program to recieve and send messages
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPclient {
    final static int ServerPort = 1234;

    public static void main(String args[]) throws UnknownHostException, IOException {
        int ID;
        Scanner scn = new Scanner(System.in);

        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");

        // establish the connection
        Socket s = new Socket(ip, ServerPort);

        // obtaining input and out streams
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        System.out.println("enter username ");
        String username = scn.nextLine();

        dos.writeUTF(username);
        ID = dis.readInt();
        System.out.println(ID);
        // System.out.println("we are here 1 ");
        // sendMessage thread
        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    // read the message to deliver.
                    String msg = scn.nextLine();

                    try {
                        // write on the output stream
                        dos.writeUTF(msg + ID);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // System.out.println("we are here 2 ");
                }
            }
        });

        // readMessage thread
        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        // read the message sent to this client
                        String msg = dis.readUTF();
                        System.out.println(msg);
                        if (msg.equals("moving to next question !")) {
                            dos.flush();
                        }
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                    // System.out.println("we are here 3 ");
                }
            }
        });

        sendMessage.start();
        readMessage.start();

    }
}