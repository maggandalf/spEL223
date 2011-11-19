package org.alexsotob.spel223;

import java.io.IOException;
import java.io.Reader;

import javax.script.ScriptException;

public class IOUtils {

	public static final String readFully(Reader reader) throws ScriptException {
		char[] arr = new char[8 * 1024]; // 8K at a time
		StringBuilder buf = new StringBuilder();
		int numChars;
		try {
			while ((numChars = reader.read(arr, 0, arr.length)) > 0) {
				buf.append(arr, 0, numChars);
			}
		} catch (IOException exp) {
			throw new ScriptException(exp);
		}
		return buf.toString();
	}

}
