package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.FailureDetectorThread;
import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.UDPMessageHandler;

public class HeartBeatMessage extends Message {

	private static final long serialVersionUID = 1L;

	public HeartBeatMessage(MemberNode sourceNode, MemberNode centralNode,
			MemberNode alteredNode) {
		super(sourceNode, centralNode, alteredNode);
	}

	@Override
	public void processMessage() {
		UDPMessageHandler
				.getProcess()
				.getLogger()
				.info("Hear tBeatReceieved from host"
						+ this.getSourceNode().getHostAddress().toString());

		if (!UDPMessageHandler.toStartHeartBeating) {
			UDPMessageHandler.getProcess().setTimer(new FailureDetectorThread());

			UDPMessageHandler.getProcess().getFailureDetector().start();
			UDPMessageHandler.toStartHeartBeating = true;
		}
		updateTimer();
	}

	public void updateTimer() {
		if (!(UDPMessageHandler.getProcess().getHeartbeatSendingNode()
				.compareTo(this.getSourceNode()))) {

			UDPMessageHandler.getProcess().setHeartbeatSendingNode(
					this.getSourceNode());
			UDPMessageHandler.toStartHeartBeating = false;
			UDPMessageHandler.getProcess().getFailureDetector().stop();

		}
		UDPMessageHandler.getProcess().setLastReceivedHeartBeatTime(
				System.currentTimeMillis());
	}

}
