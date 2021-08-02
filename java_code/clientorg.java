import java.io.*;
import java.net.*;
import java.util.*;

public class clientorg {
	public static void main(String args[]) {
		Process theProcess = null;
		DatagramSocket socket = null;
		String s;
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
		File newFile = new File("shared.txt");
		try {
			socket = new DatagramSocket();
			InetAddress host = InetAddress.getByName("localhost");
			boolean finished = false;
			echo("Enter operation");
			Scanner sc = new Scanner(System.in);
			String str = sc.nextLine();
			if (str.contentEquals("add a line")) {
				if (newFile.exists() == false) {
					DatagramPacket packeterror = new DatagramPacket(new byte[150000], 150000);
					socket.receive(packeterror);
					byte[] data8 = packeterror.getData();
					s = new String(data8, 0, packeterror.getLength());
					echo(s);

				}

				// FileOutputStream fout=new FileOutputStream("shared.txt");

				while (!finished) {
					// take input and send the packet
					echo("Enter message to send : ");
					s = (String) cin.readLine();
					byte[] b = s.getBytes();
					DatagramPacket dp = new DatagramPacket(b, b.length, host, 1502);
					socket.send(dp);

				}
			} else {
				while (!finished) {
					String st = "Read the file";
					byte[] b = st.getBytes();
					DatagramPacket dp = new DatagramPacket(b, b.length, host, 1500);
					socket.send(dp);
					DatagramPacket packet = new DatagramPacket(new byte[150000], 150000);
					socket.receive(packet);
					byte[] data = packet.getData();
					s = new String(data, 0, packet.getLength());
					echo(packet.getAddress().getHostAddress() + " : " + packet.getPort() + " - " + s);

				}

			}
			{

			}

			/*
			 * //now receive reply //buffer to receive incoming data byte[] buffer = new
			 * byte[65536]; DatagramPacket reply = new DatagramPacket(buffer,
			 * buffer.length); sock.receive(reply);
			 * 
			 * byte[] data = reply.getData(); s = new String(data, 0, reply.getLength());
			 * 
			 * //echo the details of incoming data - client ip : client port - client
			 * message echo(reply.getAddress().getHostAddress() + " : " + reply.getPort() +
			 * " - " + s);
			 */

			socket.close();
		}

		catch (IOException e) {
			System.err.println("IOException " + e);
		}

	}

	// simple function to echo data to terminal
	public static void echo(String msg) {
		System.out.println(msg);
	}
}