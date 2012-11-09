package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.FileIdentifier;
import edu.illinois.cs425.mp3.FileSystemManager;
import edu.illinois.cs425.mp3.Process;

public class DeleteChunkMessage extends RequestMessage {
	String fileName;
	public DeleteChunkMessage(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public void processMessage(Process process) throws Exception {
		for(FileIdentifier fid: process.getFileIndexer().getFileList()) {
			if(fid.getSdfsFileName().equals(fileName))
				FileSystemManager.deleteChunk(fileName, fid.getChunkId());
			    process.getFileIndexer().delete(fid);
		}
	}
}