package edu.illinois.cs425.mp3.messages;

import java.net.InetAddress;

import edu.illinois.cs425.mp3.FileIdentifier;
import edu.illinois.cs425.mp3.Process;

public class ChunkReceivedMessage extends RequestMessage {
	private final int chunkId;
	private final InetAddress source;
	private final String fileName;

	public ChunkReceivedMessage(String fileName, int chunkId,
			InetAddress source) {
		this.chunkId = chunkId;
		this.fileName = fileName;
		this.source = source;
	}

	@Override
	public void processMessage(Process process) throws Exception {
		process.getFileIndexer().merge((new FileIdentifier(chunkId, fileName, source)));
		outputStream.writeObject(new Object());
	}
}
