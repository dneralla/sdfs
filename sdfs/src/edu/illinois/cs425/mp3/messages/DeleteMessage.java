package edu.illinois.cs425.mp3.messages;

import java.net.InetAddress;

import edu.illinois.cs425.mp3.Process;

public class DeleteMessage extends RequestMessage {
	String sdfsFileName;

	public DeleteMessage(String sdfsFile) {
		this.sdfsFileName = sdfsFile;
	}

	@Override
	public void processMessage(Process process) throws ClassNotFoundException {
		InetAddress self = process.getNode().getHostAddress();
		for (InetAddress host : process.getFileIndexer().getReplicas(
				sdfsFileName)) {
			if (process.getTcpServer().sendRequestMessage(
					new DeleteChunkMessage(sdfsFileName), host,
					process.TCP_SERVER_PORT) != null)
				System.out.println("Chunk deleted");
		}
	}
}