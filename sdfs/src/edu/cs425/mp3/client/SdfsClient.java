package edu.cs425.mp3.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class SdfsClient {

	public static void main(String args) throws Exception {

		SdfsClient client = new SdfsClient();
		String inputLine;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("[Please Enter Command]$ ");
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.startsWith("put")) {
				String localFileName = inputLine.substring(
						inputLine.indexOf(" ") + 1, inputLine.lastIndexOf(" "));
				String sdfsFileName = inputLine.substring(inputLine
						.lastIndexOf(" ") + 1);
				client.chunkAndSend(localFileName, sdfsFileName);

			} else if (inputLine.startsWith("get")) {
				String sdfsFileName = inputLine.substring(
						inputLine.indexOf(" ") + 1, inputLine.lastIndexOf(" "));
				client.getFile(sdfsFileName);
			} else if (inputLine.startsWith("delete")) {
				String sdfsFileName = inputLine.substring(
						inputLine.indexOf(" ") + 1, inputLine.lastIndexOf(" "));
				client.deleteFile(sdfsFileName);
			} else {

			}

		}

	}

	public void chunkAndSend(String localFileName, String sdfsFileName) {

	}

	public void chunkAndSend(String localFileName, String sdfsFileName,
			InetAddress bootstrapNode) {
	}

	public void getFile(String sdfsFile) {
	}

	public void getFile(String sdfsFile, InetAddress bootStrapNode) {
	}

	public void deleteFile(String sdfsFile, InetAddress bootStrapNode) {
	}

	public void deleteFile(String sdfsFile) {
	}

}