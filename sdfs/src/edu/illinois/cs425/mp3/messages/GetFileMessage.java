package edu.illinois.cs425.mp3.messages;

import java.io.Serializable;


import edu.illinois.cs425.mp3.Process;

public class GetFileMessage extends RequestMessage implements Serializable{
	
	String sdfsFileName;
	
	public GetFileMessage(String sdfsFileName)
	{
		this.sdfsFileName=sdfsFileName;
	}
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void processMessage(Process process) 
	{

		
	}

}