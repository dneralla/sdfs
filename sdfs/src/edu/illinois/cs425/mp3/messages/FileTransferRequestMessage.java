package edu.illinois.cs425.mp3.messages;

import java.net.InetAddress;

import edu.illinois.cs425.mp3.Process;

public class FileTransferRequestMessage extends GenericMessage {
	public String fileName;
	public int chunkId;
	public InetAddress destinationAddress;

	public FileTransferRequestMessage(String fileName, int chunkId,
			InetAddress destinationAddress) {
		this.fileName = fileName;
		this.chunkId = chunkId;
		this.destinationAddress = destinationAddress;
	}

	@Override
	public void processMessage(Process process) throws Exception {
		//process.getTCPServer().sendMessage(message, host, port);
	}
}
