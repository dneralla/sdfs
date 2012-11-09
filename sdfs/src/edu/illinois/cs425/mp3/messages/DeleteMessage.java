package edu.illinois.cs425.mp3.messages;

import java.io.Serializable;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.Process;

public class DeleteMessage extends Message implements Serializable{
	
	String sdfsFileName;
	
	public DeleteMessage(MemberNode sourceNode, MemberNode centralNode,
			MemberNode alteredNode,String sdfsFileName)
	{
		super(sourceNode, centralNode,alteredNode);
		this.sdfsFileName=sdfsFileName;
	   
	}
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void processMessage(Process process) 
	{

		
	}

}