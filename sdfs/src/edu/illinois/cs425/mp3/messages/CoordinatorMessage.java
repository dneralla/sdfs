package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.FileIndexer;
import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.Process;

public class CoordinatorMessage extends GenericMessage {
	public MemberNode masterNode;
	public CoordinatorMessage(MemberNode masterNode) {
		this.masterNode = masterNode;
	}
	@Override
	public void processMessage(Process process) throws Exception {
		process.setMaster(masterNode);
		//process.getTcpServer().sendMessage(new FileIndexerMessage(process.getFileIndexer()), masterNode.getHostAddress(), process.TCP_SERVER_PORT);
		if(process.getNode().equals(masterNode)) {
			for(MemberNode node: process.getGlobalList()) {
				FileIndexer fileIndexer = (FileIndexer) process.getTcpServer().sendRequestMessage(new FileIndexerRequestMessage(), masterNode.getHostAddress(), process.TCP_SERVER_PORT);
				process.getFileIndexer().merge(fileIndexer);
			}
			process.ensureReplicaCount();
		}
	}
}
