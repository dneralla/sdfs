package edu.illinois.cs425.mp3.messages;
import edu.illinois.cs425.mp3.Process;

public abstract class GenericMessage {
	public abstract void processMessage(Process process) throws Exception;
}
