/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticCommandResultData;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.command.document.Delete;
import com.github.nnest.sparrow.rest.AbstractRestCommand;
import com.github.nnest.sparrow.rest.ElasticRestMethod;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder;

/**
 * rest command delet, {@linkplain ElasticCommandKind#DELETE}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandDelete extends AbstractRestCommand<Delete> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#readResponse(com.github.nnest.sparrow.ElasticCommand,
	 *      java.io.InputStream)
	 */
	@Override
	protected ElasticCommandResultData readResponse(Delete command, InputStream stream)
			throws ElasticExecutorException {
		try {
			return new ObjectMapper().readValue(stream, DeleteResponse.class);
		} catch (Exception e) {
			throw new ElasticExecutorException("Fail to read data from response.", e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#convertToRestRequest(com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected RestRequest convertToRestRequest(Delete command) throws ElasticExecutorException {
		ElasticDocumentDescriptor descriptor = command.getDocumentDescriptor();

		RestRequest request = new RestRequest();
		String endpoint = RestCommandEndpointBuilder.buildEndpoint(descriptor, command.getId(), command.getVersion());
		request.setEndpoint(endpoint);
		// use PUT for id given
		request.setMethod(ElasticRestMethod.DELETE.name());
		return request;
	}
}
