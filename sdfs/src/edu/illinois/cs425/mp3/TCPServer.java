package edu.illinois.cs425.mp3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

import edu.illinois.cs425.mp3.messages.GenericMessage;

public class TCPServer implements Server {
	ServerSocket serverSocket;
	boolean keepListening;

	@Override
	public void start(int serverPort){
		try {
			keepListening = true;
	        serverSocket = new ServerSocket(serverPort);
	        while (keepListening) {
				new TcpServerThread(serverSocket.accept()).start();
			}
	    } catch (IOException e) {
	        System.err.println("Could not listen on port: " + serverPort);
	        System.exit(1);
	    }
	}

	@Override
	public void stop() throws IOException {
		serverSocket.close();
	}

	@Override
	public void sendMessage(GenericMessage message, InetAddress host, int port) {
		// TODO Auto-generated method stub
	}
}
