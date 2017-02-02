/**
 * 
 */
package com.github.nnest.sparrow.simple;

import java.util.Map;

/**
 * default command template
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DefaultCommandTemplate implements CommandTemplate {
	private String name = null;
	private HttpMethod method = null;
	private String endpoint = null;
	private Map<String, String> params = null;
	private Map<String, Object> body = null;
	private Map<String, String> headers = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplate#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplate#getMethod()
	 */
	@Override
	public HttpMethod getMethod() {
		return this.method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplate#getEndpoint()
	 */
	@Override
	public String getEndpoint() {
		return this.endpoint;
	}

	/**
	 * @param endpoint
	 *            the endpoint to set
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplate#getParams()
	 */
	@Override
	public Map<String, String> getParams() {
		return this.params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplate#getBody()
	 */
	@Override
	public Map<String, Object> getBody() {
		return this.body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(Map<String, Object> body) {
		this.body = body;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplate#getHeaders()
	 */
	@Override
	public Map<String, String> getHeaders() {
		return this.headers;
	}

	/**
	 * @param headers
	 *            the headers to set
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
