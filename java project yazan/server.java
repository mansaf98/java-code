import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {

    public static void main(String[] args) throws Exception {
        try (ServerSocket listener = new ServerSocket(1099)) {
            System.out.println("server is up and running ");
            ExecutorService pool = Executors.newFixedThreadPool(200);
            // start a game session
            while (true) {
                Game session = new Game();
                pool.execute(session.new Player(listener.accept(), 'X'));
                pool.execute(session.new Player(listener.accept(), 'O'));
            }
        }
    }

}

class Game {
    // innitialize the game variables,shuffle the questions and create a handle for
    // the new player
    boolean valid = false;
    Player currentPlayer;
    List<Question> ds;
    int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
            27 };

    public Game() {
        // shuffle the questions
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }
        // System.out.println(Arrays.toString(array));
        // load the questions
        try {
            FileInputStream fis = new FileInputStream("questions.out");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ds = (List<Question>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get the question to be displayed to the user
    public String getquestion(int num) throws IOException {

        return ds.get(num).toString();
    }

    // get the winner of the game session
    public void getwinner() {
        if (this.currentPlayer.opponent.points > this.currentPlayer.points) {
            this.currentPlayer.output
                    .println("LOST" + " the winner is " + this.currentPlayer.opponent.name + " YOUR POINTS ARE :"
                            + this.currentPlayer.points + " OPPONENT POINTS : " + this.currentPlayer.opponent.points);
            this.currentPlayer.opponent.output
                    .println("WINNER" + " the winner is " + this.currentPlayer.opponent.name + " YOUR POINTS ARE : "
                            + this.currentPlayer.opponent.points + " OPPONENT POINTS : " + this.currentPlayer.points);
        } else {
            this.currentPlayer.output
                    .println("WINNER" + " the winner is " + this.currentPlayer.name + "YOUR POINTS ARE : "
                            + this.currentPlayer.points + " OPPONENT POINTS : " + this.currentPlayer.opponent.points);
            this.currentPlayer.opponent.output
                    .println("LOST" + " the winner is " + this.currentPlayer.name + " YOUR POINTS ARE : "
                            + this.currentPlayer.points + " OPPONENT POINTS : " + this.currentPlayer.opponent.points);
        }
    }

    // represents every player on the server
    class Player implements Runnable {

        char mark;
        String name;
        boolean done = false;
        int points = 0;
        Player opponent;
        Scanner input;
        PrintWriter output;
        Socket socket;

        public Player(Socket socket, char mark) {
            this.socket = socket;
            this.mark = mark;

        }

        @Override
        public void run() {
            try {
                setup();
                while (opponent == null) {
                    System.out.print("");
                }
                for (int i = 0; i < 5; i++) {
                    valid = true;
                    while (valid != false) {
                        output.println(
                                "QUERRY " + "Prepare for question number " + "  " + i + "  " + getquestion(array[i]));
                        if (valid == false) {
                            break;
                        }
                        processCommands(array[i]);
                    }
                }
                this.done = true;
                output.println("DONE waiting for other player to finish");
                while (this.opponent.done == false) {

                }
                getwinner();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (opponent != null && opponent.output != null) {
                    opponent.output.println("OTHER_PLAYER_LEFT");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

        // set up the the player class, everytime a player connects they will be
        // querried to enter their name
        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + mark);
            output.println("Enter only Choice Number for Answer EX:- 1 and press enter");
            if (mark == 'X') {
                System.out.println("arrived X");
                currentPlayer = this;
                output.println("NAME");
                this.name = input.nextLine();
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                System.out.println("arrived O");
                opponent = currentPlayer;
                output.println("NAME");
                this.name = input.nextLine();
                opponent.opponent = this;
                opponent.output.println("the game is now starting ");
                output.println("the game is now starting ");
            }
        }

        // process every message from the client side
        private void processCommands(int num) {

            if (input.hasNextLine()) {
                String command = input.nextLine();
                System.out.print("  " + mark + " Answered:" + command);
                if (!command.matches("\\d{1}")) {
                    output.println("Invalid");
                    return;
                } else {
                    String temp = Integer.toString(ds.get(num).getCorrectAnswer());
                    System.out.print("  Question Number: " + (num + 1) + " Correct Answer is " + temp);
                    if (command.equalsIgnoreCase(temp) && valid == true) {
                        output.println("CORRECT");
                        points = points + ds.get(num).getPoints();
                        // ds.get(num).invalid();
                        valid = false;
                        System.out.print("  " + mark + " Points:" + points);
                    } else {
                        output.println("WRONG" + " the correct answer is : " + ds.get(num).getCorrectAnswer());
                        valid = false;
                        System.out.print("  " + mark + " Total Points:" + points);
                    }
                }
            }
            System.out.println(" ");
        }

    }

}
