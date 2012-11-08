package edu.illinois.cs425.mp3.messages;

import java.io.FileWriter;
import java.io.Serializable;
import java.io.Writer;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.Process;
public class PutChunkMessage extends Message implements Serializable{

	/**
	 * Serial Version uid default.
	 */
	char[] chunkContent ;
	int chunkId;
	String sdfsFileName;
    
	public PutChunkMessage(MemberNode sourceNode, MemberNode centralNode,
			MemberNode alteredNode,char[] chunkContent,int chunkId,String sdfsFileName)
	{
		super(sourceNode, centralNode,alteredNode);
		this.chunkContent= chunkContent;
		this.chunkId=chunkId;
		this.sdfsFileName=sdfsFileName;
	   
	}
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void processMessage(Process process) 
	{
//	   try{
//		Writer out = new FileWriter(this.sdfsFileName+this.chunkId);
//		out.write(this.chunkContent);
//		
//	   }catch(Exception e)
//	   {
//		   e.printStackTrace();
//	   }
		
	}

}
