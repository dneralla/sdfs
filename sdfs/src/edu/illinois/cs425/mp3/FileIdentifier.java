package edu.illinois.cs425.mp3;

import java.net.InetAddress;

public class FileIdentifier {

	private int chunkId;
	private String sdfsFileName;
	private InetAddress chunkAddress;

	public FileIdentifier(int chunkId, String sdfsFileName, String localFileName) {
		this.chunkId = chunkId;
		this.sdfsFileName = sdfsFileName;
	}

	public int getChunkId() {
		return chunkId;
	}

	public void setChunkId(int chunkId) {
		this.chunkId = chunkId;
	}

	public String getSdfsFileName() {
		return sdfsFileName;
	}

	public void setSdfsFileName(String sdfsFileName) {
		this.sdfsFileName = sdfsFileName;
	}

	public InetAddress getChunkAddress() {
		return chunkAddress;
	}

	public void setChunkAddress(InetAddress chunkAddress) {
		this.chunkAddress = chunkAddress;
	}

	public boolean compare(FileIdentifier fIdentifier) {
		if (this.getChunkAddress() == fIdentifier.getChunkAddress()
				&& this.getChunkId() == fIdentifier.getChunkId()
				&& this.getSdfsFileName() == fIdentifier.getSdfsFileName())
			return true;

		return false;

	}

}
