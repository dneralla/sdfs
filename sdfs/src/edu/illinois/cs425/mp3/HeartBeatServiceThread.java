package edu.illinois.cs425.mp3;

import edu.illinois.cs425.mp3.messages.HeartBeatMessage;
import edu.illinois.cs425.mp3.messages.Message;

public class HeartBeatServiceThread extends Thread {
	@Override
	public void run() {
		try {
			Message m = new HeartBeatMessage(UDPMessageHandler.getProcess()
					.getNode(), null, null);
			while (true) {

				UDPMessageHandler
						.getProcess()
						.getLogger()
						.info("HeartBeat Sending to"
								+ UDPMessageHandler.getProcess().getNeighborNode()
										.getHostAddress().toString());
				UDPMessageHandler.getProcess().sendMessage(m,
						UDPMessageHandler.getProcess().getNeighborNode());
				Thread.sleep(100);
			}
		} catch (Exception e) {

			System.out.println("Error in sending hearbeat message");
			UDPMessageHandler
					.getProcess()
					.getLogger()
					.info("Error in sending heart beat message to"
							+ UDPMessageHandler.getProcess().getNeighborNode()
									.getHostAddress());
		}
	}
}
