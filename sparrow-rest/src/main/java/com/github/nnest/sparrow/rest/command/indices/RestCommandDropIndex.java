/**
 * 
 */
package com.github.nnest.sparrow.rest.command.indices;

import java.io.InputStream;

import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticCommandResultData;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.command.indices.DropIndex;
import com.github.nnest.sparrow.rest.AbstractRestCommand;
import com.github.nnest.sparrow.rest.ElasticRestMethod;

/**
 * rest command {@linkplain ElasticCommandKind#DROP_INDEX}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandDropIndex extends AbstractRestCommand<DropIndex> {
	/**
	 * return null
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#readResponse(com.github.nnest.sparrow.ElasticCommand,
	 *      java.io.InputStream)
	 */
	@Override
	protected ElasticCommandResultData readResponse(DropIndex command, InputStream stream)
			throws ElasticExecutorException {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#convertToRestRequest(com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected RestRequest convertToRestRequest(DropIndex command) throws ElasticExecutorException {
		RestRequest request = new RestRequest();

		request.setEndpoint("/" + command.getIndexName());
		// use PUT for id given
		request.setMethod(ElasticRestMethod.DELETE.name());
		return request;
	}
}
