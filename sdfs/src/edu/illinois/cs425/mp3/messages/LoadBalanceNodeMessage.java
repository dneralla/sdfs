package edu.illinois.cs425.mp3.messages;

import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.net.InetAddress;

import edu.illinois.cs425.mp3.MemberNode;
import edu.illinois.cs425.mp3.Process;

public class LoadBalanceNodeMessage extends Message implements Serializable {

	
	private String fileName=null ;
	private InetAddress freeNode=null;
	private int chunkId=-1;
	private String sdfsFileName=null;
	int remreplicaCount;
	
	public LoadBalanceNodeMessage(MemberNode sourceNode, MemberNode centralNode,
			MemberNode alteredNode,InetAddress freeNode,String fileName,int chunkId,String sdfsFileName,int remReplicaCount)
	{
		super(sourceNode, centralNode,alteredNode);
	    this.freeNode=freeNode;
	    this.fileName=fileName;
	    this.chunkId=chunkId;
	    this.sdfsFileName=sdfsFileName;
	    this.remreplicaCount=remReplicaCount;
	}
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void processMessage(Process process) 
	{
		try{
	    char c[] = new char[process.getChunkSize()];
	    FileReader r = new FileReader(new File(this.fileName));
	    r.read(c, this.chunkId,process.getChunkSize());
	    PutChunkMessage cm = new PutChunkMessage(process.getNode(),null,null,c,this.chunkId,this.sdfsFileName);
        process.getTcpServer().sendMessage(cm, freeNode, Process.TCP_SERVER_PORT);
        r.close();
        
        this.remreplicaCount--;
        if(this.remreplicaCount>0)
        {
        	LoadBalanceMasterMessage lbm = new LoadBalanceMasterMessage(process.getNode(),null,null,this.fileName,this.chunkId,this.sdfsFileName,this.remreplicaCount);
        	process.getTcpServer().sendMessage(lbm,process.getMaster().getHostAddress(),Process.TCP_SERVER_PORT);
        }
        
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	

}
