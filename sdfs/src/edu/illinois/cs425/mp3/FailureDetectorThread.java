package edu.illinois.cs425.mp3;

import java.util.Date;

import edu.illinois.cs425.mp3.messages.Message;
import edu.illinois.cs425.mp3.messages.MulticastFailureMessage;

/*
 * Class spawns thread, which will be used for
 * detecting node failures.
 */
public class FailureDetectorThread extends ServiceThread {

	public FailureDetectorThread(Message message) {
		super(message);
	}

	public FailureDetectorThread() {
		super();
	}

	@Override
	public void run() {
		while (true) {
			if (System.currentTimeMillis()
					- UDPMessageHandler.getProcess()
							.getLastReceivedHeartBeatTime() > 3 * 1000) {

				System.out.println("failure Detected of node"
						+ UDPMessageHandler.getProcess().getHeartbeatSendingNode()
								.getHostAddress());
				UDPMessageHandler
						.getProcess()
						.getLogger()
						.info("failure Detected of node"
								+ UDPMessageHandler.getProcess()
										.getHeartbeatSendingNode()
										.getHostAddress());
				processFailure(UDPMessageHandler.getProcess()
						.getHeartbeatSendingNode());
				UDPMessageHandler.toStartHeartBeating = false;

				this.stop();
			}
		}

	}

	public void processFailure(MemberNode node) {
		try {

			MemberNode self = UDPMessageHandler.getProcess().getNode();

			node.setTimeStamp(new Date());
			MulticastFailureMessage message = new MulticastFailureMessage(self,
					self, node);
			UDPMessageHandler.getMulticastServer().multicastUpdate(message);
			message.mergeIntoMemberList();
			UDPMessageHandler.getMulticastServer().multicastUpdate(message);
		} catch (Exception e) {
			System.out.println("processing failure failed of node"
					+ node.getHostAddress());
			UDPMessageHandler
					.getProcess()
					.getLogger()
					.info("processing failure failed of node"
							+ node.getHostAddress());
		}
	}
}
