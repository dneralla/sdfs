package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.ServiceThread;
import edu.illinois.cs425.mp3.UDPMessageHandler;

public class MulticastLeaveMessage extends MulticastMessage {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void processMessage() {
		new ServiceThread(this) {
			@Override
			public void run() {
				try {

					UDPMessageHandler
							.getProcess()
							.getLogger()
							.info("Multicast leave message Processing  of node"
									+ getMessage().getAlteredNode()
											.getHostAddress());

					if (mergeIntoMemberList()) {
						Message message = getNewRelayMessage(UDPMessageHandler
								.getProcess().getNode(), getMessage()
								.getSourceNode(), getMessage().getAlteredNode());
						UDPMessageHandler.sendMessage(
								message,
								UDPMessageHandler.getProcess()
										.getNeighborNode());
						if (getMessage().getAlteredNode().compareTo(
								UDPMessageHandler.getProcess()
										.getNeighborNode())) {
							UDPMessageHandler.getProcess().setNeighborNode(
									getMessage().getSourceNode());
						}

					}

				} catch (Exception e) {
					UDPMessageHandler
							.getProcess()
							.getLogger()
							.info("Multicast leave message  Processing failed of node"
									+ getMessage().getAlteredNode()
											.getHostAddress());
					e.printStackTrace();
				}
			}
		}.start();

	}

	@Override
	public RelayLeaveMessage getNewRelayMessage(MemberNode sourceNode,
			MemberNode centralNode, MemberNode alteredNode) {
		// TODO Auto-generated method stub
		return new RelayLeaveMessage(sourceNode, centralNode, alteredNode);
	}

	public MulticastLeaveMessage(MemberNode sourceNode, MemberNode centralNode,
			MemberNode alteredNode) {
		super(sourceNode, centralNode, alteredNode);
	}

}
