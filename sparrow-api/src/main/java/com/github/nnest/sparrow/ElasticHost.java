/**
 * 
 */
package com.github.nnest.sparrow;

import com.google.common.base.Strings;

/**
 * Elastic host
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ElasticHost {
	private String hostName = null;
	private int port = -1;
	private String scheme = null;

	public ElasticHost(String hostName) {
		this(hostName, 9200);
	}

	public ElasticHost(String hostName, int port) {
		this(hostName, port, "http");
	}

	public ElasticHost(String hostName, int port, String scheme) {
		assert Strings.nullToEmpty(hostName).trim().length() != 0 : "Host name cannot be blank or null.";
		assert port > 0 : "Port must be positive.";
		assert Strings.nullToEmpty(scheme).trim().length() != 0 : "Scheme cannot be blank or null.";

		this.hostName = hostName;
		this.port = port;
		this.scheme = scheme;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the scheme
	 */
	public String getScheme() {
		return scheme;
	}
}
