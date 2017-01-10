/**
 * 
 */
package com.github.nnest.sparrow.rest;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticExecutorException;

/**
 * rest command index create only,
 * {@linkplain ElasticCommandKind#INDEX_CREATE_ONLY}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandIndexCreateOnly extends RestCommandIndex {
	/**
	 * add {@code /_create} as suffix of endpoint of request
	 * 
	 * @see com.github.nnest.sparrow.rest.RestCommandIndex#convertToRestRequest(com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected RestRequest convertToRestRequest(ElasticCommand command) throws ElasticExecutorException {
		RestRequest request = super.convertToRestRequest(command);
		String endpoint = request.getEndpoint();
		request.setEndpoint(endpoint + "/_create");
		return request;
	}
}
