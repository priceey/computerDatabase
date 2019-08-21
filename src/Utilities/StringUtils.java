package Utilities;

import java.util.Random;

public class StringUtils {
	
	public static final String SOURCES =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

	/**
	 * Generates a random string of supplied length
	 * @param length
	 * @return
	 */
	public static String GenerateRandomString(int length) {

		if (length < 1)
			throw new IllegalArgumentException();
		
		Random random = new Random();

		char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
        return new String(text);
	}

}
