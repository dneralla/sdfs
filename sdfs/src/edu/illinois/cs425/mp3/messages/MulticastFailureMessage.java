package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.ServiceThread;
import edu.illinois.cs425.mp3.UDPMessageHandler;

public class MulticastFailureMessage extends MulticastMessage {
	private static final long serialVersionUID = 1L;

	@Override
	public void processMessage() {
		new ServiceThread(this) {
			@Override
			public void run() {
				try {
					if (mergeIntoMemberList()) {
						Message message = getNewRelayMessage(UDPMessageHandler
								.getProcess().getNode(), getMessage()
								.getSourceNode(), getMessage().getAlteredNode());
						UDPMessageHandler.sendMessage(message,
								UDPMessageHandler.getProcess().getNeighborNode());
						if (getMessage().getAlteredNode().compareTo(
								UDPMessageHandler.getProcess().getNeighborNode())) {
							UDPMessageHandler.getProcess().setNeighborNode(
									getMessage().getSourceNode());
						}
					}

				} catch (Exception e) {
					UDPMessageHandler
							.getProcess()
							.getLogger()
							.info("Multicast failure receieve  Processing failed of node"
									+ getMessage().getAlteredNode()
											.getHostAddress());
				}
			}
		}.start();

	}

	public MulticastFailureMessage(MemberNode sourceNode,
			MemberNode centralNode, MemberNode alteredNode) {
		super(sourceNode, centralNode, alteredNode);
	}

	@Override
	public RelayFailureMessage getNewRelayMessage(MemberNode sourceNode,
			MemberNode centralNode, MemberNode alteredNode) {

		return new RelayFailureMessage(sourceNode, centralNode, alteredNode);
	}

}
