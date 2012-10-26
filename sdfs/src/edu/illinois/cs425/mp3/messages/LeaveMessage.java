package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.UDPMessageHandler;
import edu.illinois.cs425.mp3.ServiceThread;

public class LeaveMessage extends Message {

	public LeaveMessage(MemberNode sourceNode, MemberNode centralNode,
			MemberNode alteredNode) {
		super(sourceNode, centralNode, alteredNode);
	}

	@Override
	public void processMessage() {
		new ServiceThread(this) {
			@Override
			public void run() {
				try {

					UDPMessageHandler
							.getProcess()
							.getLogger()
							.info("Processing Leave message"
									+ getMessage().getAlteredNode()
											.getHostAddress());
					mergeIntoMemberList();

					MemberNode self = UDPMessageHandler.getProcess().getNode();
					// TODO: in case of failure detection, altered is faulty
					// node
					MulticastLeaveMessage message = new MulticastLeaveMessage(
							self, self, getMessage().getAlteredNode());
					UDPMessageHandler.getProcess().setRecentLeftNode(
							getAlteredNode());
					UDPMessageHandler.getMulticastServer().multicastUpdate(
							message);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					UDPMessageHandler
							.getProcess()
							.getLogger()
							.info("Leave Message processing failed or multicast update failed of node"
									+ getMessage().getAlteredNode()
											.getHostAddress());
					e.printStackTrace();
				}
			}
		}.start();

	}

}
