package edu.illinois.cs425.mp3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileSystemManager {

	@SuppressWarnings("resource")
	public static String getChunk(String fileName, int chunkId) {
		FileReader fr = null;
		String out = "";
		char[] cbuf = new char[Process.CHUNK_SIZE];
		try {
			fr = new FileReader(new File(fileName + "." + chunkId));
			BufferedReader br = new BufferedReader(fr);
			String temp;
			while ((temp = br.readLine()) != null)
				out.concat(temp);

			fr.close();
			br.close();
			return new String(cbuf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static void putChunk(String chunk, String fileName, int chunkId) {
		try {
			FileWriter fwr = new FileWriter(new File(fileName + "." + chunkId));
			fwr.write(chunk);
			fwr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteChunk(String fileName, int chunkId) {
		File f = new File(fileName + "." + chunkId);
		f.delete();

	}
}
