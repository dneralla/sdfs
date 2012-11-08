package edu.illinois.cs425.mp3.messages;

import java.io.Serializable;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.Process;
public class SdfsClientMessage extends Message implements Serializable{

private int chunkId;
private String localFilename;
private String sdfsFileName;


	
public SdfsClientMessage(MemberNode sourceNode, MemberNode centralNode,
	   MemberNode alteredNode,int chunkId,String localFileName,String sdfsFileName) {
		super(sourceNode, centralNode, alteredNode);
		this.chunkId=chunkId;
		this.localFilename=localFileName;
		this.sdfsFileName=sdfsFileName;
	}




/**
	 * default serial Version uid
	 */
	private static final long serialVersionUID = 1L;



@Override
public void processMessage(Process process) throws Exception {
	
	
}

}
