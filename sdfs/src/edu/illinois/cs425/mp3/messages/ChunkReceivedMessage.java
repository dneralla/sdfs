package edu.illinois.cs425.mp3.messages;

import java.net.InetAddress;

import edu.illinois.cs425.mp3.FileIdentifier;
import edu.illinois.cs425.mp3.Process;

public class ChunkReceivedMessage extends GenericMessage {
	private int chunkId;
	private InetAddress source;
	private String fileName;

	@Override
	public void processMessage(Process process) throws Exception {
		process.getFileIndexer().merge((new FileIdentifier(fileName, chunkId, source)));
	}
}
