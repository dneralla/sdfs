package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.ServiceThread;
import edu.illinois.cs425.mp3.UDPMessageHandler;

public class JoinMessage extends Message {
	private static final long serialVersionUID = 1L;

	public JoinMessage(MemberNode sourceNode, MemberNode centralNode,
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
							.info("Servicing Join request of node"
									+ getMessage().getSourceNode()
											.getHostAddress());

					((JoinMessage) getMessage()).mergeIntoMemberList();

					MemberNode oldNeighbourNode = UDPMessageHandler.getProcess()
							.getNeighborNode();
					UDPMessageHandler.getProcess().setNeighborNode(
							getMessage().getSourceNode());

					Message ackMessage = new JoinAckMessage(UDPMessageHandler
							.getProcess().getNode(), null, null);

					((JoinAckMessage) ackMessage)
							.setNeighbourNode(oldNeighbourNode);
					((JoinAckMessage) ackMessage).setGlobalList(UDPMessageHandler
							.getProcess().getGlobalList());

					UDPMessageHandler.sendMessage(ackMessage,
							getMessage().getSourceNode());

				} catch (Exception e) {

					UDPMessageHandler
							.getProcess()
							.getLogger()
							.info("Processing join failed of node"
									+ getMessage().getSourceNode()
											.getHostAddress());
					System.out.println("Processing join failed");
					e.printStackTrace();
				}
			}
		}.start();

	}
}
