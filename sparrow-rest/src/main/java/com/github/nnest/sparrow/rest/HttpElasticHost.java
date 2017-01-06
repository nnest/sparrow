/**
 * 
 */
package com.github.nnest.sparrow.rest;

import org.apache.http.HttpHost;

import com.github.nnest.sparrow.ElasticHost;
import com.google.common.base.Strings;

/**
 * default elastic host. its immutable.<br>
 * default use port {@code 9200} and scheme {@code HttpHost.DEFAULT_SCHEME_NAME}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class HttpElasticHost implements ElasticHost {
	private String hostName = null;
	private int port = -1;
	private String scheme = null;

	public HttpElasticHost(String hostName) {
		this(hostName, 9200);
	}

	public HttpElasticHost(String hostName, int port) {
		this(hostName, port, HttpHost.DEFAULT_SCHEME_NAME);
	}

	public HttpElasticHost(String hostName, int port, String scheme) {
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
