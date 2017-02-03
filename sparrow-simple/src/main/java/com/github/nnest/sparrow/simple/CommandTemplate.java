/**
 * 
 */
package com.github.nnest.sparrow.simple;

import java.util.Map;

import com.github.nnest.sparrow.simple.template.HttpMethod;
import com.github.nnest.sparrow.simple.token.BodyKey;
import com.github.nnest.sparrow.simple.token.Endpoint;
import com.github.nnest.sparrow.simple.token.HeaderKey;
import com.github.nnest.sparrow.simple.token.HeaderValue;
import com.github.nnest.sparrow.simple.token.ParamKey;
import com.github.nnest.sparrow.simple.token.ParamValue;

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

	/**
	 * get transformed endpoint
	 * 
	 * @return endpoint
	 */
	Endpoint getTransformedEndpoint();

	/**
	 * get parameters
	 * 
	 * @return parameters
	 */
	Map<String, String> getParams();

	/**
	 * get transformed parameters
	 * 
	 * @return parameters
	 */
	Map<ParamKey, ParamValue> getTransformedParams();

	/**
	 * get body
	 * 
	 * @return body
	 */
	Map<String, Object> getBody();

	/**
	 * get transformed body
	 * 
	 * @return body
	 */
	Map<BodyKey, Object> getTransformedBody();

	/**
	 * get headers
	 * 
	 * @return headers
	 */
	Map<String, String> getHeaders();

	/**
	 * get transformed headers
	 * 
	 * @return headers
	 */
	Map<HeaderKey, HeaderValue> getTransformedHeaders();
}
