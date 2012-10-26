package edu.illinois.cs425.mp3;

import edu.illinois.cs425.mp3.messages.Message;

public class ServiceThread extends Thread {
	private final Message message;

	public Message getMessage() {
		return message;
	}

	public ServiceThread() {
		this.message = null;
	}

	public ServiceThread(Message message) {
		this.message = message;
	}

}
