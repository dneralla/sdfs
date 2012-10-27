package edu.illinois.cs425.mp3;

import edu.illinois.cs425.mp3.messages.HeartBeatMessage;
import edu.illinois.cs425.mp3.messages.Message;

public class HeartBeatServiceThread extends Thread {
	@Override
	public void run() {
		try {
			Message m = new HeartBeatMessage(MessageHandler.getProcess()
					.getNode(), null, null);
			while (true) {

				MessageHandler
						.getProcess()
						.getLogger()
						.info("HeartBeat Sending to"
								+ MessageHandler.getProcess().getNeighborNode()
										.getHostAddress().toString());
				MessageHandler.getProcess().sendMessage(m,
						MessageHandler.getProcess().getNeighborNode());
				Thread.sleep(100);
			}
		} catch (Exception e) {

			System.out.println("Error in sending hearbeat message");
			MessageHandler
					.getProcess()
					.getLogger()
					.info("Error in sending heart beat message to"
							+ MessageHandler.getProcess().getNeighborNode()
									.getHostAddress());
		}
	}
}
