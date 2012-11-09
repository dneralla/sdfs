package edu.illinois.cs425.mp3.messages;

import java.io.Serializable;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.Process;

public class LoadBalanceMasterMessage extends Message implements Serializable {

	
    private String fileName;
    private int chunkId;
    private String sdfsFileName;
    private int remReplicaCount;
	
	public LoadBalanceMasterMessage(MemberNode sourceNode, MemberNode centralNode,
			MemberNode alteredNode,String fileName,int chunkId, String sdfsFileName, int remReplicaCount)
	{
		super(sourceNode, centralNode,alteredNode);
		this.fileName=fileName;
		this.chunkId=chunkId;
		this.sdfsFileName=sdfsFileName;
	    this.remReplicaCount=remReplicaCount;
	}
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void processMessage(Process process) 
	{
	    
		//TODO Get the free node,dump filename and chunkid and send
	
	
	
	}
	
	
}
