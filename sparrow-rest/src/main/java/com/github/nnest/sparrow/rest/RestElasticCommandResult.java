/**
 * 
 */
package com.github.nnest.sparrow.rest;

import com.github.nnest.sparrow.DefaultElasticCommandResult;
import com.github.nnest.sparrow.rest.response.RestResponseObject;

/**
 * rest elastic command result
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestElasticCommandResult extends DefaultElasticCommandResult {
	private RestResponseObject responseObject = null;

	public RestElasticCommandResult(Object originalDocument, RestResponseObject responseObject) {
		super(originalDocument);
		this.setResponseObject(responseObject);
	}

	/**
	 * @return the responseObject
	 */
	public RestResponseObject getResponseObject() {
		return responseObject;
	}

	/**
	 * @param responseObject
	 *            the responseObject to set
	 */
	private void setResponseObject(RestResponseObject responseObject) {
		this.responseObject = responseObject;
	}

}
