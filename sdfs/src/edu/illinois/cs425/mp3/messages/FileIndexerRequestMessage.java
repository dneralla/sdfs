package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.Process;

public class FileIndexerRequestMessage extends RequestMessage{
	String fileName = null;
	@Override
	public void processMessage(Process process) throws Exception {
		if(fileName == null)
			outputStream.writeObject(process.getFileIndexer());
		else
			outputStream.writeObject(process.getFileIndexer().groupBy(fileName));
	}
}
