/**
 * 
 */
package com.github.nnest.sparrow.simple;

import java.util.Map;

/**
 * command template
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface CommandTemplate {
	/**
	 * get name of template
	 * 
	 * @return name
	 */
	String getName();

	/**
	 * get http method
	 * 
	 * @return http method
	 */
	HttpMethod getMethod();

	/**
	 * get endpoint
	 * 
	 * @return endpoint
	 */
	String getEndpoint();
	
	Endpoint get();

	/**
	 * get parameters
	 * 
	 * @return parameters
	 */
	Map<String, String> getParams();

	/**
	 * get body
	 * 
	 * @return body
	 */
	Map<String, Object> getBody();

	/**
	 * get headers
	 * 
	 * @return headers
	 */
	Map<String, String> getHeaders();
}
