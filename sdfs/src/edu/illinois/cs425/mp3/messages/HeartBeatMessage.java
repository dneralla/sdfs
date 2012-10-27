package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.FailureDetectorThread;
import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.MessageHandler;
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

		if (!process.toStartHeartBeating) {
			process.setTimer(new FailureDetectorThread());

			process.getFailureDetector().start();
			process.toStartHeartBeating = true;
		}
		updateTimer();
	}

	public void updateTimer() {
		if (!(process.getHeartbeatSendingNode()
				.compareTo(this.getSourceNode()))) {

			process.setHeartbeatSendingNode(
					this.getSourceNode());
			MessageHandler.toStartHeartBeating = false;
			process.getFailureDetector().stop();

		}
		process.setLastReceivedHeartBeatTime(
				System.currentTimeMillis());
	}

}
