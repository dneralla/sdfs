package edu.illinois.cs425.mp3.messages;

import java.net.InetAddress;

import edu.illinois.cs425.mp3.FileSystemManager;
import edu.illinois.cs425.mp3.Process;

public class ChunkTransferMessage extends RequestMessage {
	String fileName;
	int chunkId;
	InetAddress destination;

	public ChunkTransferMessage(String fileName, int chunkId, InetAddress destination) {
		this.fileName = fileName;
		this.chunkId = chunkId;
		this.destination = destination;
	}

	@Override
	public void processMessage(Process process) throws Exception {
		    String chunk = FileSystemManager.getChunk(fileName, chunkId);
			outputStream.writeObject(process.getTcpServer().sendRequestMessage(new PutChunkMessage(chunk, chunkId, fileName), destination, Process.TCP_SERVER_PORT));
	}

}
