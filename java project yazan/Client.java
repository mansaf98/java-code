import java.util.*;
import java.io.*;
import java.net.*;

public class Client {

    private Socket socket;
    private Scanner Serverinput;
    private PrintWriter ClientOutput;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Client() throws Exception {
        socket = new Socket("localhost", 1099);
        Serverinput = new Scanner(socket.getInputStream());
        ClientOutput = new PrintWriter(socket.getOutputStream(), true);

    }

    public void play() throws Exception {
        // recieve messages from the server and process them, and also send the response
        try {
            String servermessage = Serverinput.nextLine();
            servermessage = Serverinput.nextLine();
            String clientresponse = "";
            char mark = servermessage.charAt(8);
            char opponentMark = mark == 'X' ? 'O' : 'X';
            while (Serverinput.hasNextLine()) {
                servermessage = Serverinput.nextLine();
                if (servermessage.startsWith("NAME")) {
                    System.out.println("enter your name :");
                    ClientOutput.println(br.readLine());
                } else if (servermessage.startsWith("the game is now starting")) {
                    System.out.println("STARTING......");
                } else if (servermessage.startsWith("MESSAGE Waiting for opponent to connect")) {
                    System.out.println("Waiting for opponent");
                } else if (servermessage.startsWith("Invalid")) {
                    System.out.println("WRONG Input please try again..");
                } else if (servermessage.startsWith("CORRECT")) {
                    System.out.println("correct");
                } else if (servermessage.startsWith("WRONG")) {
                    System.out.println(servermessage);
                } else if (servermessage.startsWith("QUERRY")) {
                    System.out.println(servermessage);
                    clientresponse = br.readLine();
                    ClientOutput.println(clientresponse);
                } else if (servermessage.startsWith("WINNER")) {
                    System.out.println(servermessage);
                    socket.close();
                } else if (servermessage.startsWith("LOST")) {
                    System.out.println(servermessage);
                    socket.close();
                } else if (servermessage.startsWith("DONE")) {
                    System.out.println(servermessage);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.play();

    }
}
