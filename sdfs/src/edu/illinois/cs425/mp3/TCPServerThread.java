package edu.illinois.cs425.mp3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.illinois.cs425.mp3.messages.GenericMessage;

public class TCPServerThread extends Thread {
	private Process process = null;
	private Socket socket = null;

	public TCPServerThread(Socket socket, Process process) {
		super("TcpServerThread");
		this.socket = socket;
		this.process = process;
	}

	@Override
	public void run() {
		try {

			// 1. get Input and Output streams
			ObjectOutputStream out = new ObjectOutputStream(socket
					.getOutputStream());
			out.flush();

			ObjectInputStream in = new ObjectInputStream(socket
					.getInputStream());

			GenericMessage message = (GenericMessage) in.readObject();

			// 2. should catch timed out exception here
			message.processMessage(null);

			out.close();
			in.close();
			socket.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
