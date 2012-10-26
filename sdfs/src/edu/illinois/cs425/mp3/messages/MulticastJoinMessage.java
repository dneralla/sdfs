package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.ServiceThread;
import edu.illinois.cs425.mp3.UDPMessageHandler;

public class MulticastJoinMessage extends MulticastMessage {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MulticastJoinMessage(MemberNode sourceNode, MemberNode centralNode,
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
							.info("Multicast join message Processing of node"
									+ getMessage().getAlteredNode()
											.getHostAddress());
					if (mergeIntoMemberList()) {
						Message message = getNewRelayMessage(UDPMessageHandler
								.getProcess().getNode(), getMessage()
								.getSourceNode(), getMessage().getAlteredNode());
						UDPMessageHandler.sendMessage(message,
								UDPMessageHandler.getProcess().getNeighborNode());
					}
				} catch (Exception e) {
					e.printStackTrace();
					UDPMessageHandler
							.getProcess()
							.getLogger()
							.info("Multicast Join Message processing failed  of node"
									+ getMessage().getAlteredNode()
											.getHostAddress());
				}
			}
		}.start();
	}

	@Override
	public RelayMessage getNewRelayMessage(MemberNode sourceNode,
			MemberNode centralNode, MemberNode alteredNode) {
		RelayJoinMessage message = new RelayJoinMessage(sourceNode,
				centralNode, alteredNode);
		return message;
	}

}
