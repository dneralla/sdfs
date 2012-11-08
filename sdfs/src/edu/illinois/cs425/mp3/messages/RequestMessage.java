package edu.illinois.cs425.mp3.messages;

import java.io.ObjectOutputStream;
public abstract class RequestMessage extends GenericMessage {
	/**
	 * Default Serial version Uid
	 */
	private static final long serialVersionUID = 1L;
	ObjectOutputStream outputStream;

	
	
	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(ObjectOutputStream outputStream) {
		this.outputStream = outputStream;
	}

}
