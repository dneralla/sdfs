package edu.illinois.cs425.mp3.messages;

import java.io.Serializable;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.Process;

public class GetFileMessage extends Message implements Serializable{
	
	String sdfsFileName;
	
	public GetFileMessage(MemberNode sourceNode, MemberNode centralNode,
			MemberNode alteredNode,char[] chunkContent,int chunkId,String sdfsFileName)
	{
		super();
		this.sdfsFileName=sdfsFileName;
	   
	}
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void processMessage(Process process) 
	{

		
	}

}
