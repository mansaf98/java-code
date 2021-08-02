
import java.io.*;
import java.util.*;
import java.net.*;
import java.io.ObjectOutputStream;

// Server class 
public class TCPserver {
    
    // Vector to store active clients
    public static Vector<ClientHandler> ar = new Vector<>();

    // counter for clients
    static int i = 0;

    public static void main(String[] args) throws IOException ,FileNotFoundException {
        // server is listening on port 1234
        ServerSocket ss = new ServerSocket(1234);
        FileInputStream fis = new FileInputStream("questions.out");
        ObjectInputStream ois = new ObjectInputStream(fis);
       
        Socket s;
        System.out.println("server up and running! now waiting for clients");
        // running infinite loop for getting
        // client request
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
            ClientHandler mtch = new ClientHandler(s, "client", dis, dos, points);

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
            i++;

        }
    }
}

// ClientHandler class
class ClientHandler implements Runnable  {
     FileInputStream fis ;
     ObjectInputStream ois ;
    Scanner scn = new Scanner(System.in);
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;
    int points;

    // constructor
    public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos, int points) throws FileNotFoundException {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin = true;
        this.points = 0;
        System.out.println(name);
        try {
            name = dis.readUTF();
        } catch (IOException e) {

            e.printStackTrace();
        }
        
    }

    @Override
    public void run() {
        
        //List<Question> ds = (List<Question>)ois.readObject();
        String received;
        while (true) {
            
            try {
                   fis = new FileInputStream("questions.out");
                    ois = new ObjectInputStream(fis);
                    List<Question> ds = (List<Question>)ois.readObject();
                if (TCPserver.ar.size() % 2 == 0) {
                    Vector<ClientHandler> players = new Vector<>();
                    players.add(TCPserver.ar.get(0));
                    players.add(TCPserver.ar.get(1));
                    TCPserver.ar.remove(0);
                    TCPserver.ar.remove(0);
                    // TCPserver.ar.remove(1);
                    System.out.println("starting match");
                    
                    for (ClientHandler mc : players) {
                        
                       // List<Question> ds = (List<Question>)ois.readObject();
                        // if the recipient is found, write on its
                        // output stream
                       mc.dos.writeUTF("starting the game ");
                        mc.dos.writeUTF("Questions are Coming ");
                      
                        mc.dos.writeUTF(ds.get(3).toString());
                        mc.dos.writeUTF("Enter Your Answer ");
                        // break;

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
                received=dis.readUTF();
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
            }
            catch(ClassNotFoundException e){System.out.println(e);}

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

/*class Question implements java.io.Serializable {
    private String question;
    private String[] choices;
    private int correctAnswer;
    private int points;

    public Question() {
    }

    public Question(String question, String[] choices, int correctAnswer, int points) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        String s = question + " (" + points + " points)\n";
        for (int i = 0; i < choices.length; i++)
            s += choices[i] + "\n";
        return s;
    }
}*/
