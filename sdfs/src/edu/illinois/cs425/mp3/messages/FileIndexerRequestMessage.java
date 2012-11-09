package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.Process;

public class FileIndexerRequestMessage extends RequestMessage{
	@Override
	public void processMessage(Process process) throws Exception {
		outputStream.writeObject(process.getFileIndexer());
	}
}
