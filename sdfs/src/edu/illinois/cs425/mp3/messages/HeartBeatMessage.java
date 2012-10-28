package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.FailureDetectorThread;
import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.Process;

public class HeartBeatMessage extends Message {

	private static final long serialVersionUID = 1L;

	public HeartBeatMessage(MemberNode sourceNode, MemberNode centralNode,
			MemberNode alteredNode) {
		super(sourceNode, centralNode, alteredNode);
	}

	@Override
	public void processMessage(Process process) {
		process.getLogger()
				.info("Hear tBeatReceieved from host"
						+ this.getSourceNode().getHostAddress().toString());
		FailureDetectorThread.setLastReceivedHeartBeatTime(
				System.currentTimeMillis());
	}

}
