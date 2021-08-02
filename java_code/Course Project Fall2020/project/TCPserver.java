
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;

// Server class 
public class TCPserver {

    // Vector to store active clients
    public static Vector<ClientHandler> ar = new Vector<>();

    // counter for clients

    public static void main(String[] args) throws IOException {
        // server is listening on port 1234
        ServerSocket ss = new ServerSocket(1234);
        Socket s;
        System.out.println("server up and running! now waiting for clients");
        // running infinite loop for getting
        // client request
        int ID = 0;
        while (true) {

            // Accept the incoming request
            s = ss.accept();

            System.out.println("New client request received : " + s);

            // obtain input and output streams
            int points = 0;
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.println("Creating a new handler for this client...");

            // dos.writeUTF("enter user name ");

            // Create a new handler object for handling this request.
            ClientHandler mtch = new ClientHandler(s, "client", dis, dos, points, ID);

            // Create a new Thread with this object.
            Thread t = new Thread(mtch);

            System.out.println("Adding this client to active client list");

            // add this client to active clients list
            ar.add(mtch);
            // for (int i = 0; i < ar.size(); i++) {
            System.out.println(ar.size());

            // }
            // start the thread.
            t.start();

            // increment i for new client.
            // i is used for naming only, and can be replaced
            // by any naming scheme
            ID++;
        }
    }
}

// ClientHandler class
class ClientHandler implements Runnable {
    Scanner scn = new Scanner(System.in);
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;
    int points;
    int ID;

    // constructor
    public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos, int points, int ID) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin = true;
        this.points = 0;
        this.ID = ID;
        System.out.println(name);
        try {
            name = dis.readUTF();
            dos.writeInt(ID);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        // List<Question> ds = (List<Question>)ois.readObject();
        String received;
        int points1 = 0;
        int points2 = 0;
        String answer;
        char choice;
        String temp;
        int ID = 0;

        while (true) {

            try {
                FileInputStream fis = new FileInputStream("questions.out");
                ObjectInputStream ois = new ObjectInputStream(fis);
                List<Question> ds = (List<Question>) ois.readObject();
                int randomNum;
                int intArray[] = new int[5];
                if (TCPserver.ar.size() % 2 == 0) {

                    Vector<ClientHandler> players = new Vector<>();
                    players.add(TCPserver.ar.get(0));
                    players.add(TCPserver.ar.get(1));
                    TCPserver.ar.remove(0);
                    TCPserver.ar.remove(0);
                    // TCPserver.ar.remove(1);

                    System.out.println("starting match");
                    for (int i = 0; i < 5; i++) {
                        boolean correct = false;
                        randomNum = ThreadLocalRandom.current().nextInt(0, 5);
                        intArray[i] = randomNum;
                        for (ClientHandler mc : players) {
                            // List<Question> ds = (List<Question>)ois.readObject();
                            // if the recipient is found, write on its
                            // output stream
                            mc.dos.writeUTF("starting the game ");
                            mc.dos.writeUTF(ds.get(i).toString());
                            mc.dos.writeUTF("Enter Your Answer ");
                            // break;
                        }
                        while (correct != true) {
                            answer = dis.readUTF();
                            System.out.println(answer.charAt(answer.length() - 1));
                            choice = answer.charAt(0);
                            if (Character.getNumericValue(choice) == ds.get(i).getCorrectAnswer()) {
                                for (ClientHandler mc : players) {
                                    mc.dos.writeUTF("moving to next question !");
                                }
                                System.out.println("i am here 2");
                                for (ClientHandler mc : players) {
                                    System.out.println("i am here 3");
                                    if (Integer.toString(mc.ID).equals(answer.substring(answer.length() - 1))) {
                                        System.out.println("i am here 4");
                                        mc.points = mc.points + ds.get(i).getPoints();
                                        System.out.println("successful " + mc.points);
                                        correct = true;
                                    }
                                }

                            }
                        }
                        /*
                         * if (i == 4) { points1 = players.get(0).points; points2 =
                         * players.get(1).points; if (points1 > points2) { for (ClientHandler mc :
                         * players) { mc.dos.writeUTF("the winner is " + players.get(0).name); } } else
                         * { for (ClientHandler mc : players) { mc.dos.writeUTF("the winner is " +
                         * players.get(1).name); } } }
                         */
                        points1 = players.get(0).points;
                        points2 = players.get(1).points;
                        System.out.print(points1);
                    }

                } else {
                    System.out.println("waiting for another client ");
                    for (ClientHandler mc : TCPserver.ar) {
                        // if the recipient is found, write on its
                        // output stream
                        mc.dos.writeUTF("waiting for another player! ");
                        // break;

                    }
                }

                // receive the string
                received = dis.readUTF();
                received = dis.readUTF();
                System.out.println(received);

                if (received.equals("logout")) {
                    this.isloggedin = false;
                    this.s.close();
                    break;
                }

                // break the string into message and recipient part
                StringTokenizer st = new StringTokenizer(received, "#");
                String MsgToSend = st.nextToken();
                String recipient = st.nextToken();

                // search for the recipient in the connected devices list.
                // ar is the vector storing client of active users
                for (ClientHandler mc : TCPserver.ar) {
                    // if the recipient is found, write on its
                    // output stream
                    if (mc.name.equals(recipient) && mc.isloggedin == true) {
                        mc.dos.writeUTF(this.name + " : " + MsgToSend);
                        break;
                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

        }
        try {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
