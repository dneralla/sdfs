package edu.illinois.cs425.mp3;

import java.io.IOException;
import java.net.ServerSocket;

public class TcpServer {
	ServerSocket serverSocket;
	boolean keepListening;

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

	public void stop() throws IOException {
		serverSocket.close();
	}
}
