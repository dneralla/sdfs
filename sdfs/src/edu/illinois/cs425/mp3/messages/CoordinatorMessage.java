package edu.illinois.cs425.mp3.messages;

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
	}
}
