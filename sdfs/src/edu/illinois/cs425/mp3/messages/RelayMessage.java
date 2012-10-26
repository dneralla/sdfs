package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.ServiceThread;
import edu.illinois.cs425.mp3.UDPMessageHandler;

public abstract class RelayMessage extends Message {

	public RelayMessage(MemberNode sourceNode, MemberNode centralNode,
			MemberNode alteredNode) {
		super(sourceNode, centralNode, alteredNode);
	}

	public abstract Message getNewMulticastMessage(MemberNode sourceNode,
			MemberNode centralNode, MemberNode alteredNode);

	@Override
	public void processMessage() {
		new ServiceThread(this) {
			@Override
			public void run() {
				try {
					MemberNode self = UDPMessageHandler.getProcess().getNode();
					if (mergeIntoMemberList()) {
						Message message = getNewMulticastMessage(self,
								getMessage().getCentralNode(), getMessage()
										.getAlteredNode());
						UDPMessageHandler.sendMessage(message,
								message.getSourceNode());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

	}

}
