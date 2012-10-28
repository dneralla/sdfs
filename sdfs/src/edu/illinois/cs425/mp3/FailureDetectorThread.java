package edu.illinois.cs425.mp3;

import java.util.Date;

import edu.illinois.cs425.mp3.messages.MulticastFailureMessage;

/*
 * Class spawns thread, which will be used for
 * detecting node failures.
 */
public class FailureDetectorThread extends Thread {

	private final Process process;
	private final long detectionTime;
	private boolean pause;
	public static volatile long lastReceivedHeartBeatTime;

	public static void setLastReceivedHeartBeatTime(long lastReceivedTime) {
		FailureDetectorThread.lastReceivedHeartBeatTime = lastReceivedHeartBeatTime;
	}

	public FailureDetectorThread(Process process, long detectionTime) {
		this.process = process;
		this.detectionTime = detectionTime;
		this.pause = false;
	}

	@Override
	public void run() {
		while (true) {
			if (System.currentTimeMillis() - lastReceivedHeartBeatTime > detectionTime) {
				if (pause)
					continue;
				pause = true;
				System.out.println("Failure Detected: "
						+ process.getHeartbeatSendingNode().getHostAddress());
				process.getLogger().info(
						"Failure Detected: "
								+ process.getHeartbeatSendingNode()
										.getHostAddress());
				processFailure(process.getHeartbeatSendingNode());
			}
			else {
				if(pause)
					pause = false;
			}
		}
	}

	public void processFailure(MemberNode node) {
		try {
			MemberNode self = process.getNode();
			node.setTimeStamp(new Date());
			MulticastFailureMessage message = new MulticastFailureMessage(self,
					self, node);
			process.getMulticastServer().multicastUpdate(message);
			message.mergeIntoMemberList(process);
			process.getMulticastServer().multicastUpdate(message);
			if(node.equals(process.getMaster())) {
				process.startMasterElection();
			}
		} catch (Exception e) {
			System.out.println("processing failure failed of node"
					+ node.getHostAddress());
			process.getLogger()
					.info("processing failure failed of node"
							+ node.getHostAddress());
		}
	}
}
