/**
 * 
 */
package com.github.nnest.sparrow.simple;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Strings;

/**
 * default command template
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DefaultCommandTemplate implements CommandTemplate {
	// plain settings
	private String name = null;
	private HttpMethod method = null;
	private String endpoint = null;
	private Map<String, String> params = null;
	private Map<String, Object> body = null;
	private Map<String, String> headers = null;

	// transformed objects
	private Endpoint transformedEndpoint = null;
	private Map<ParamKey, ParamValue> transformedParams = null;
	private Map<BodyKey, Object> transformedBody = null;
	private Map<HeaderKey, HeaderValue> transformedHeaders = null;

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
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplate#getTransformedEndpoint()
	 */
	@Override
	public Endpoint getTransformedEndpoint() {
		return this.transformedEndpoint;
	}

	/**
	 * @param endpoint
	 *            the endpoint to set
	 */
	public void setEndpoint(String endpoint) {
		assert Strings.nullToEmpty(endpoint).trim().length() != 0 : "Endpoint cannot be null or blank.";

		this.endpoint = endpoint;
		this.transformedEndpoint = new Endpoint(this.endpoint);
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
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplate#getTransformedParams()
	 */
	@Override
	public Map<ParamKey, ParamValue> getTransformedParams() {
		return this.transformedParams;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;

		if (params != null) {
			this.transformedParams = new HashMap<ParamKey, ParamValue>(params.size());
			for (Map.Entry<String, String> entry : params.entrySet()) {
				this.transformedParams.put(new ParamKey(entry.getKey()), new ParamValue(entry.getValue()));
			}
		}
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
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplate#getTransformedBody()
	 */
	@Override
	public Map<BodyKey, Object> getTransformedBody() {
		return this.transformedBody;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(Map<String, Object> body) {
		this.body = body;

		if (body != null) {
			this.transformedBody = new HashMap<>(this.body.size());
			this.transformBodyMap(this.body, this.transformedBody);
		}
	}

	/**
	 * transform body map
	 * 
	 * @param source
	 *            source map
	 * @param target
	 *            target map
	 */
	@SuppressWarnings("unchecked")
	protected void transformBodyMap(Map<String, Object> source, Map<BodyKey, Object> target) {
		for (Map.Entry<String, Object> entry : source.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			BodyKey bodyKey = new BodyKey(key);
			Object bodyValue = null;
			if (value == null) {
				// keep original
				bodyValue = null;
			} else if (value instanceof String) {
				bodyValue = new BodyValue((String) value);
			} else if (value instanceof Map) {
				bodyValue = new HashMap<BodyKey, Object>(((Map<String, Object>) value).size());
				this.transformBodyMap((Map<String, Object>) value, (Map<BodyKey, Object>) bodyValue);
			} else {
				// keep original
				bodyValue = value;
			}
			target.put(bodyKey, bodyValue);
		}
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
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplate#getTransformedHeaders()
	 */
	@Override
	public Map<HeaderKey, HeaderValue> getTransformedHeaders() {
		return this.transformedHeaders;
	}

	/**
	 * @param headers
	 *            the headers to set
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;

		if (headers != null) {
			this.transformedHeaders = new HashMap<HeaderKey, HeaderValue>(headers.size());
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				this.transformedHeaders.put(new HeaderKey(entry.getKey()), new HeaderValue(entry.getValue()));
			}
		}
	}
}
