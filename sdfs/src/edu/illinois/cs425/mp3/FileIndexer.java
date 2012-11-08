package edu.illinois.cs425.mp3;

import java.net.InetAddress;
import java.util.List;

public interface FileIndexer {

	public void merge(FileIndexer fileIndexer);

	public void merge(FileIdentifier fileIdentifier);

	public List<FileIdentifier> groupBy(String fileName);

	public List<FileIdentifier> groupBy(InetAddress node);

	public List<InetAddress> getSourceAndDestination(FileIdentifier id);

	public boolean isPresent(FileIdentifier fileIdentifier);

}
