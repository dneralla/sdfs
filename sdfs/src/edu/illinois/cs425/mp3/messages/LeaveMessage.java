package edu.illinois.cs425.mp3.messages;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.Process;

public class LeaveMessage extends Message {

	public LeaveMessage(MemberNode sourceNode, MemberNode centralNode,
			MemberNode alteredNode) {
		super(sourceNode, centralNode, alteredNode);
	}

	@Override
	public void processMessage(Process process) {
		try {
			process.getLogger().info(
					"Processing Leave message"
							+ getAlteredNode().getHostAddress());
			mergeIntoMemberList(process);

			MemberNode self = process.getNode();
			MulticastLeaveMessage message = new MulticastLeaveMessage(self,
					self, getAlteredNode());
			process.setRecentLeftNode(getAlteredNode());
			process.getMulticastServer().multicastUpdate(message);

		} catch (Exception e) {
			process.getLogger().info(
					"Leave Message processing failed or multicast update failed of node"
							+ getAlteredNode().getHostAddress());
			e.printStackTrace();
		}
	}

}
