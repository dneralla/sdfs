package edu.cs425.mp3.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import edu.illinois.cs425.mp3.messages.DeleteMessage;
import edu.illinois.cs425.mp3.messages.GenericMessage;
import edu.illinois.cs425.mp3.messages.GetFileMessage;
import edu.illinois.cs425.mp3.messages.PutChunkMessage;

public class SdfsClient {

	private InetAddress masterNode;
	public static final int PORT = 3333;
	public static final String RCV_DIR = "";
	public static final int CHUNK_SIZE = 1024;

	public static void main(String args) throws Exception {

		SdfsClient client = new SdfsClient();
		String inputLine;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("[Please Enter Command]$ ");
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.startsWith("put")) {
				String localFileName = inputLine.substring(
						inputLine.indexOf(" ") + 1, inputLine.lastIndexOf(" "));
				String sdfsFileName = inputLine.substring(inputLine
						.lastIndexOf(" ") + 1);
				client.chunkAndSend(localFileName, sdfsFileName);

			} else if (inputLine.startsWith("get")) {
				String sdfsFileName = inputLine.substring(
						inputLine.indexOf(" ") + 1, inputLine.lastIndexOf(" "));
				client.getFile(sdfsFileName);
			} else if (inputLine.startsWith("delete")) {
				String sdfsFileName = inputLine.substring(
						inputLine.indexOf(" ") + 1, inputLine.lastIndexOf(" "));
				client.deleteFile(sdfsFileName);
			} else {

			}

		}

	}

	public void chunkAndSend(String localFileName, String sdfsFileName) {
		chunkAndSend(localFileName, sdfsFileName, this.masterNode);
	}

	public void getFile(String sdfsFile) {
		getFile(sdfsFile, this.masterNode);
	}

	public void deleteFile(String sdfsFile) {
		deleteFile(sdfsFile, this.masterNode);
	}

	public void chunkAndSend(String localFileName, String sdfsFileName,
			InetAddress bootStrapNode) {

		try {
			FileReader fr = new FileReader(new File(localFileName));
			char chunk[] = new char[SdfsClient.CHUNK_SIZE];
			ObjectOutputStream out = null;
			ObjectInputStream in = null;
			OutputStream os = null;
			Socket requestSocket;
			PutChunkMessage pMsg;
			int chunkId = 0;

			while (fr.read(chunk) != -1) {
				requestSocket = new Socket(bootStrapNode, SdfsClient.PORT);
				out = new ObjectOutputStream(requestSocket.getOutputStream());
				out.flush();
				in = new ObjectInputStream(requestSocket.getInputStream());
				pMsg = new PutChunkMessage(new String(chunk), chunkId,
						sdfsFileName);
				chunkId++;
				sendMessage(pMsg, out);
				out.close();
				in.close();

			}

		} catch (Exception ex) {

		}

	}

	public void getFile(String sdfsFile, InetAddress bootStrapNode) {
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		OutputStream os = null;
		Socket requestSocket;
		PutChunkMessage pcMsg;
		PrintWriter p = null;
		File outputFile;
		try {
			requestSocket = new Socket(bootStrapNode, SdfsClient.PORT);
			out = new ObjectOutputStream(requestSocket.getOutputStream());

			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			GetFileMessage gMsg = new GetFileMessage(sdfsFile);
			sendMessage(gMsg, out);
			int noChunks = in.readInt();
			for (int i = 0; i < noChunks; i++) {
				pcMsg = (PutChunkMessage) in.readObject();
				outputFile = new File(pcMsg.getFileName());
				if (!outputFile.exists())
					p = new PrintWriter(new BufferedWriter(new FileWriter(
							pcMsg.getFileName(), true)));

				p.println(pcMsg.getChunk());

			}

			out.close();
			in.close();
			p.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteFile(String sdfsFile, InetAddress bootStrapNode) {
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		Socket requestSocket;

		try {
			requestSocket = new Socket(bootStrapNode, SdfsClient.PORT);
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			DeleteMessage dMsg = new DeleteMessage(sdfsFile);
			sendMessage(dMsg, out);

			out.close();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void sendMessage(GenericMessage msg, ObjectOutputStream out) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

}