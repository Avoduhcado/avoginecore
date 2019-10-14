package com.avogine.core.resource.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

/**
 * Static utility for reading resource files.
 * @author Dominus
 *
 */
public class ResourceFileReader {

	/**
	 * Read a resource file as plain text and return a {@link StringBuilder} of the file contents.
	 * @param filePath the file name starting from <tt>src/main/resources/</tt>
	 * @return A <tt>StringBuilder</tt> containing the contents of the file.
	 */
	public static StringBuilder readTextFile(String filePath) {
		StringBuilder fileContents = new StringBuilder();
		try (InputStream in = ResourceFileReader.class.getClassLoader().getResourceAsStream(filePath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
			String line;
			while ((line = reader.readLine()) != null) {
				fileContents.append(line).append("\n");
			}
		} catch (IOException e) {
			System.err.println("Could not read file! " + filePath);
			e.printStackTrace();
			System.exit(1);
		}

		return fileContents;
	}
	
	/**
	 * Read a resource file to a {@link ByteBuffer}.
	 * @param filePath the file name starting from <tt>src/main/resources/</tt>
	 * @return A direct <tt>ByteBuffer</tt> containing the contents of the file.
	 */
	public static ByteBuffer readResourceToByteBuffer(String filePath) {
		ByteBuffer buffer = null;
		try (InputStream source = ResourceFileReader.class.getClassLoader().getResourceAsStream(filePath)) {
			// Read all the bytes from the InputStream and create a new DIRECT ByteBuffer
			byte[] inBytes = source.readAllBytes();
			buffer = ByteBuffer.allocateDirect(inBytes.length);

			// Store all the bytes in the buffer and prepare it for loading
			buffer.put(inBytes);
			buffer.flip();
		} catch (IOException e) {
			// TODO Logger
			System.err.println("Could not read file! " + filePath);
			e.printStackTrace();
			System.exit(1);
		}
		return buffer;
	}

}