package edu.illinois.cs425.mp3.messages;

import java.io.FileWriter;
import java.io.Serializable;
import java.io.Writer;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.Process;
public class ChunkMessage extends Message implements Serializable{

	/**
	 * Serial Version uid default.
	 */
	char[] chunkContent ;
	int chunkId;
	String sdfsFileName;
    int remReplicaCount;
	public ChunkMessage(MemberNode sourceNode, MemberNode centralNode,
			MemberNode alteredNode,char[] chunkContent,int chunkId,String sdfsFileName, int remReplicaCount)
	{
		super(sourceNode, centralNode,alteredNode);
		this.chunkContent= chunkContent;
		this.chunkId=chunkId;
		this.sdfsFileName=sdfsFileName;
	    this.remReplicaCount=remReplicaCount;
	}
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void processMessage(Process process) 
	{
	   try{
		Writer out = new FileWriter(this.sdfsFileName+this.chunkId);
		out.write(this.chunkContent);
		
	   }catch(Exception e)
	   {
		   e.printStackTrace();
	   }
		
	}

}
