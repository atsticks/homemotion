package org.homemotion.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class Base64Serializer {
	private Base64Serializer() {
		// TODO Auto-generated constructor stub
	}

	public static String serialize(Object obj)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(baos);
		oout.writeObject(obj);
		oout.close();
		byte[] buf = baos.toByteArray();
		return new sun.misc.BASE64Encoder().encode(buf);
	}

	public static Object deserialize(String input) throws 
			IOException, ClassNotFoundException {
		byte[] buf = new sun.misc.BASE64Decoder().decodeBuffer(input);
		if (buf != null) {
			ObjectInputStream objectIn = new ObjectInputStream(
					new ByteArrayInputStream(buf));
			return objectIn.readObject(); // Contains the object
		}
		return null;
	}
}
