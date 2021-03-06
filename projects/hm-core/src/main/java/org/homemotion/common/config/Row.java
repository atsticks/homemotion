package org.homemotion.common.config;

import java.util.HashMap;

public final class Row extends HashMap<String, String>{

	private static final long serialVersionUID = 7320946223844909841L;

	public String[] getFields(String... fieldNames) {
		String[] result = new String[fieldNames.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = get(fieldNames[i]);
		}
		return result;
	}

}
