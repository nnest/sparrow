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
	private HttpMethod httpMethod = null;
	private String url = null;
	private Map<String, Object> body = null;

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
	 * @see com.github.nnest.sparrow.simple.CommandTemplate#getHttpMethod()
	 */
	@Override
	public HttpMethod getHttpMethod() {
		return this.httpMethod;
	}

	/**
	 * @param httpMethod
	 *            the httpMethod to set
	 */
	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplate#getUrl()
	 */
	@Override
	public String getUrl() {
		return this.url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
}
