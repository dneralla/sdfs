package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.FileSystemManager;
import edu.illinois.cs425.mp3.Process;

public class ChunkMessage extends RequestMessage {
	String chunk;
	int chunkId;
	String fileName;

	public ChunkMessage(String chunk, int chunkId, String fileName) {
		this.chunk = chunk;
		this.chunkId = chunkId;
		this.fileName = fileName;
	}

	@Override
	public void processMessage(Process process) throws Exception {
		FileSystemManager.putChunk(chunk, fileName, chunkId);
		GenericMessage m = new ChunkReceivedMessage(fileName, chunkId, process.getNode().getHostAddress());
		Object response = process.getTCPServer().sendRequestMessage(m, process.getMaster().getHostAddress(), Process.TCP_SERVER_PORT);
		outputStream.writeObject(response);
	}

}
