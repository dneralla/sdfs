package edu.illinois.cs425.mp3;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;

import edu.illinois.cs425.mp3.messages.GenericMessage;
import edu.illinois.cs425.mp3.messages.Message;

/*
 * Central class for processing messages.
 */
public class UDPMessageHandler extends Thread {
	private static Process process;

	public static Process getProcess() {
		return process;
	}

	public static MulticastServer getMulticastServer() {
		return process.getMulticastServer();
	}

	public static void setMulticastServer(MulticastServer multicastServer) {
		UDPMessageHandler.setMulticastServer(multicastServer);
	}

	public static boolean toStartHeartBeating = false;

	public UDPMessageHandler(Process process) {
		UDPMessageHandler.process = process;
	}

	public static void sendMessage(GenericMessage message, MemberNode node) throws Exception {
		process.getUdpServer().sendMessage(message, node.getHostAddress(), process.UDP_SERVER_PORT);
	}

	@Override
	public void run() {
		DatagramPacket packet;
		Message message;
		InetAddress senderAddress;
		int port;

		while (true) {
			byte receiveMessage[] = new byte[Message.MAX_MESSAGE_LENGTH];

			try {
				packet = new DatagramPacket(receiveMessage,
						receiveMessage.length);
				process.getUdpServer().getSocket().receive(packet);
				if(process.isInRing()) {
					continue;
				}
				senderAddress = packet.getAddress();
				port = packet.getPort();
				ByteArrayInputStream bis = new ByteArrayInputStream(
						packet.getData());
				ObjectInputStream in = null;
				in = new ObjectInputStream(bis);
				message = (Message) in.readObject();
				message.processMessage();

			} catch (Exception e) {

			}
		}
	}

}
