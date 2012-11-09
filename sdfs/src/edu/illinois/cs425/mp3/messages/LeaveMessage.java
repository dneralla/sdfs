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
			process.setRecentLeftNode(getAlteredNode());
			process.getLogger().info(
					"Processing Leave message"
							+ getAlteredNode().getHostAddress());
			processNodeDisappearance(process);

		} catch (Exception e) {
			process.getLogger().info(
					"Leave Message processing failed or multicast update failed of node"
							+ getAlteredNode().getHostAddress());
			e.printStackTrace();
		}
	}

	public void processNodeDisappearance(Process process) throws Exception {
		mergeIntoMemberList(process);

		MemberNode self = process.getNode();
		MulticastLeaveMessage message = new MulticastLeaveMessage(self,
				self, getAlteredNode());

		process.getMulticastServer().multicastUpdate(message);

		// sending request to master server for re-replication
		if(self.equals(process.getMaster())) {
			 process.getTcpServer().sendMessage(new ReplicateMessage(getAlteredNode().getHostAddress()), process.getMaster().getHostAddress(), process.TCP_SERVER_PORT);
             process.replicateNode(getAlteredNode().getHostAddress());
		}

        //
		if(getAlteredNode().equals(process.getMaster())) {
			process.startMasterElection();
		}
	}

}
