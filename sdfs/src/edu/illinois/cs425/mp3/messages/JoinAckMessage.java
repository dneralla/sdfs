package edu.illinois.cs425.mp3.messages;

import java.util.List;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.ServiceThread;
import edu.illinois.cs425.mp3.UDPMessageHandler;

public class JoinAckMessage extends Message {

	private MemberNode neighbourNode;
	private List<MemberNode> globalList;

	public List<MemberNode> getGlobalList() {
		return globalList;
	}

	public void setGlobalList(List<MemberNode> globalList) {
		this.globalList = globalList;
	}

	public MemberNode getNeighbourNode() {
		return neighbourNode;
	}

	public void setNeighbourNode(MemberNode neighbourNode) {
		this.neighbourNode = neighbourNode;
	}

	public JoinAckMessage(MemberNode sourceNode, MemberNode centralNode, MemberNode alteredNode) {
		super(sourceNode, centralNode, alteredNode);
	}


	@Override
	public void processMessage() {
		new ServiceThread(this) {
			@Override
			public void run() {
				try {
					UDPMessageHandler.getProcess().getLogger().info("Join Acknowledging and updating neighbor as "+((JoinAckMessage)getMessage()).getNeighbourNode().getHostAddress());
					UDPMessageHandler.getProcess().setNeighborNode(((JoinAckMessage)getMessage()).getNeighbourNode());

                    UDPMessageHandler.getProcess().setGlobalList(getGlobalList());

					MemberNode self = UDPMessageHandler.getProcess().getNode();
					MulticastJoinMessage message = new MulticastJoinMessage(self,self,self);

					UDPMessageHandler.getMulticastServer().multicastUpdate(message);

				} catch (Exception e) {
				 UDPMessageHandler.getProcess().getLogger().info("Updating neighbor or multicasting update failed");
				 System.out.println("updating neighbor failed");
				}
			}
		}.start();

	}

}
