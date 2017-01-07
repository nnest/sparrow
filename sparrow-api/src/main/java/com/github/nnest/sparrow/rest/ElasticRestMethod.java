/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.util.HashMap;
import java.util.Map;

/**
 * elastic rest method. same as http method
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public enum ElasitcRestMethod {
	GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

	private static final Map<String, ElasitcRestMethod> mappings = new HashMap<String, ElasitcRestMethod>(8);

	static {
		for (ElasitcRestMethod httpMethod : values()) {
			mappings.put(httpMethod.name(), httpMethod);
		}
	}

	/**
	 * Resolve the given method value to an {@code ElasitcRestMethod}.
	 * 
	 * @param method
	 *            the method value as a String
	 * @return the corresponding {@code ElasitcRestMethod}, or {@code null} if
	 *         not found
	 * @since 4.2.4
	 */
	public static ElasitcRestMethod resolve(String method) {
		return (method != null ? mappings.get(method) : null);
	}

	/**
	 * Determine whether this {@code ElasitcRestMethod} matches the given method
	 * value.
	 * 
	 * @param method
	 *            the method value as a String
	 * @return {@code true} if it matches, {@code false} otherwise
	 * @since 4.2.4
	 */
	public boolean matches(String method) {
		return (this == resolve(method));
	}
}
