import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.*;

//import sun.net.util.SocketExceptions;
public class serverorg {

    public static void main(String args[]) {

        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(1502);

            // check if the exists
            /*
             * File newFile = new File("shared.txt"); if( newFile.exists()==false) { String
             * se="File is Empty"; DatagramPacket ok = new
             * DatagramPacket(se.getBytes(),se.getBytes().length); socket.send(ok);
             * System.exit(0);
             * 
             * }
             */

            DatagramPacket incoming = new DatagramPacket(new byte[65536], 65536);
            FileInputStream fileInputStream = new FileInputStream("shared.txt");
            BufferedReader reader = new BufferedReader(new FileReader("shared.txt"));
            BufferedWriter bufwriter = new BufferedWriter(new FileWriter("shared.txt", true));

            System.out.println("Server socket created. Waiting for incoming data...");

            boolean finished = false;
            while (!finished) {
                socket.receive(incoming);

                byte[] data = incoming.getData();
                String s = new String(data, 0, incoming.getLength());

                if (s.contentEquals("Read the file")) {
                    // ((reader.readLine()) != null)

                    while (reader.read() != -1) {
                        // ByteArrayInputStream bin = new ByteArrayInputStream(incoming.getData());
                        // DataInputStream din = new DataInputStream (bin);
                        String string = reader.readLine();

                        DatagramPacket dp1 = new DatagramPacket(string.getBytes(), string.getBytes().length,
                                incoming.getAddress(), incoming.getPort());
                        socket.send(dp1);

                    }

                }

                else {

                    // echo the details of incoming data - client ip : client port - client message
                    System.out.println(incoming.getAddress() + " : " + incoming.getPort() + " - " + s);
                    bufwriter.append(" ");
                    bufwriter.append(s);
                    bufwriter.newLine();
                    DatagramPacket dp2 = new DatagramPacket(s.getBytes(), s.getBytes().length, incoming.getAddress(),
                            incoming.getPort());
                    socket.send(dp2);
                    bufwriter.flush();
                    // process the packet
                    // }
                }
            }
        }

        catch (FileNotFoundException ex) {
            // String fnf="File is Empty";
            // DatagramPacket ok = new DatagramPacket(fnf.getBytes() ,
            // fnf.getBytes().length);
            System.out.print("File not found");
        }

        // catch(Exception x){System.out.println("66");}}

        catch (SocketException so) {
            System.out.println(so);
        } catch (Exception ex) {
            System.out.println("77");
        }

    }
}
