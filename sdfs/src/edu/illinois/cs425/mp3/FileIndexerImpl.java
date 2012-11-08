package edu.illinois.cs425.mp3;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileIndexerImpl implements FileIndexer {

	public static final int MAX = 1000;
	List<FileIdentifier> fileList;
	private final Process process;

	/*
	 * Constructor.
	 */
	public FileIndexerImpl(Process process) {
		this.process = process;
		this.fileList = new ArrayList<FileIdentifier>();
	}

	public List<FileIdentifier> getFileList() {
		return fileList;
	}

	public void setFileList(List<FileIdentifier> fileList) {
		this.fileList = fileList;
	}

	@Override
	public void merge(FileIndexer fileIndexer) {
		Iterator<FileIdentifier> it = ((FileIndexerImpl) fileIndexer)
				.getFileList().iterator();
		while (it.hasNext())
			merge(it.next());
	}

	@Override
	public void merge(FileIdentifier fileIdentifier) {
		Iterator<FileIdentifier> it = fileList.iterator();
		FileIdentifier temp;
		while (it.hasNext()) {
			temp = it.next();
			if (temp.compare(fileIdentifier))
				return;
		}
		fileList.add(fileIdentifier);
	}

	@Override
	public List<FileIdentifier> groupBy(String fileName) {

		Iterator<FileIdentifier> it = fileList.iterator();
		List<FileIdentifier> returnList = new ArrayList<FileIdentifier>();
		FileIdentifier temp;
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getSdfsFileName() == fileName)
				returnList.add(temp);

		}

		return returnList;
	}

	@Override
	public List<FileIdentifier> groupBy(InetAddress node) {
		Iterator<FileIdentifier> it = fileList.iterator();
		List<FileIdentifier> returnList = new ArrayList<FileIdentifier>();
		FileIdentifier temp;
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getChunkAddress() == node)
				returnList.add(temp);

		}

		return returnList;
	}

	@Override
	public List<InetAddress> getSourceAndDestination(FileIdentifier id) {

		Iterator<MemberNode> it = this.process.getGlobalList().iterator();
		List<InetAddress> returnList = new ArrayList<InetAddress>();
		InetAddress temp;
		InetAddress bestSource = null;
		InetAddress bestDestination = null;
		int minSourceCost = MAX;
		int minDestCost = MAX;
		if (this.isPresent(id)) {
			while (it.hasNext()) {
				temp = it.next().getHostAddress();
				if (this.isPresentAtNode(id.getChunkId(), temp)) {
					int tempSrcCost = groupBy(temp).size();
					if (tempSrcCost <= minSourceCost) {
						minSourceCost = tempSrcCost;
						bestSource = temp;
					}
				} else {
					int tempDestCost = groupBy(temp).size();
					if (tempDestCost <= minDestCost) {
						minDestCost = tempDestCost;
						bestDestination = temp;
					}

				}
			}

		}

		returnList.add(bestSource);
		returnList.add(bestDestination);
		return returnList;
	}

	@Override
	public boolean isPresent(FileIdentifier fileIdentifier) {
		Iterator<FileIdentifier> it = fileList.iterator();
		FileIdentifier temp;
		while (it.hasNext()) {
			temp = it.next();
			if (temp.compare(fileIdentifier))
				return true;
		}
		return false;
	}

	public boolean isPresentAtNode(int chunkId, InetAddress node) {
		Iterator<FileIdentifier> it = fileList.iterator();
		FileIdentifier temp;
		while (it.hasNext()) {
			temp = it.next();
			if (temp.getChunkAddress() == node && temp.getChunkId() == chunkId)
				return true;

		}

		return false;
	}

}
