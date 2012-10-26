package edu.illinois.cs425.mp3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import edu.illinois.cs425.mp3.messages.GenericMessage;
import edu.illinois.cs425.mp3.messages.Message;

/*
 * Class for managing multicast server
 * and sending multicast messages.
 */
public class MulticastServer implements Server {

	private Process process;
	private MulticastSocket multicastListenerSocket;
	private MulticastSocket multicastServerSocket;
	private InetAddress multicastGroup;

	public int getMulticastServerPort() {
		return multicastServerSocket.getPort();
	}

	public MulticastSocket getMulticastListenerSocket() {
		return multicastListenerSocket;
	}

	public void setMulticastListenerSocket(
			MulticastSocket multicastListenerSocket) {
		this.multicastListenerSocket = multicastListenerSocket;
	}

	public MulticastSocket getMulticastServerSocket() {
		return multicastServerSocket;
	}

	public void setMulticastServerSocket(MulticastSocket multicastServerSocket) {
		this.multicastServerSocket = multicastServerSocket;
	}

	public Process getServer() {
		return process;
	}

	public void setServer(Process process) {
		this.process = process;
	}

	public MulticastServer(Process process) throws IOException {
		this.process = process;
		try {
			this.multicastGroup = InetAddress.getByName(Process.MULTICAST_GROUP);
			this.multicastServerSocket = new MulticastSocket();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public InetAddress getMulticastGroup() {
		return multicastGroup;
	}

	@Override
	public synchronized void stop() throws IOException {
		multicastListenerSocket.leaveGroup(multicastGroup);
		multicastListenerSocket.close();
		multicastServerSocket.close();
	}

	@Override
	public void start(int port) throws IOException {
		// TODO Auto-generated method stub
		this.multicastServerSocket = new MulticastSocket();
		this.multicastListenerSocket = new MulticastSocket(port);
		this.multicastListenerSocket.joinGroup(multicastGroup);
		new MulticastServerThread(this).start();
	}

	@Override
	public void sendMessage(GenericMessage multicastMessage, InetAddress multicastGroup, int multicastListenerPort) throws Exception {
		UDPMessageHandler
				.getProcess()
				.getLogger()
				.info("Sending multicast update of Node"
						+ ((Message)multicastMessage).getAlteredNode().getHostAddress());

		byte[] bytes = ((Message)multicastMessage).toBytes();
		DatagramPacket packet = new DatagramPacket(bytes, bytes.length,
				multicastGroup, multicastListenerPort);
		getMulticastServerSocket().send(packet);
	}
}
