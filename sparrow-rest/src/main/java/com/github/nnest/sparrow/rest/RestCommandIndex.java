/**
 * 
 */
package com.github.nnest.sparrow.rest;

import org.elasticsearch.client.Response;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandResult;

/**
 * rest command {@code index}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandIndex extends AbstractRestCommand {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#convertToCommandResult(org.elasticsearch.client.Response,
	 *      com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected ElasticCommandResult convertToCommandResult(Response response, ElasticCommand command) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#convertToRestRequest(com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected RestRequest convertToRestRequest(ElasticCommand command) {
		// TODO Auto-generated method stub
		return null;
	}
}
