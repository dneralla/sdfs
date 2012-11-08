package edu.illinois.cs425.mp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

import edu.illinois.cs425.mp3.messages.LoadBalanceMasterMessage;
import edu.illinois.cs425.mp3.messages.LoadBalanceMessage;

public class FileSystemManager {
	
	private Process process;
	LoadBalanceMessage message;
	
	public FileSystemManager(Process process)
	{  
       this.setProcess(process); 		
	}
	public Process getProcess() {
		return process;
	}
	public void setProcess(Process process) {
		this.process = process;
	}

	
   public void putChunk(String localFile,String sdfsFile,int chunkId)
   {
//	   LoadBalanceMasterMessage lbm = new LoadBalanceMasterMessage(process.getNode(),null,null,localFile,i,outputFile,this.process.getChunkSize());
//  	   process.getTcpServer().sendMessage(lbm,process.getMaster().getHostAddress(),Process.TCP_SERVER_PORT);
//	   
  }	
   
  
}
 



