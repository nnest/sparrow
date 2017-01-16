/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.io.InputStream;

import org.apache.http.HttpStatus;
import org.elasticsearch.client.Response;

import com.github.nnest.sparrow.DefaultElasticCommandResult;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticCommandResultData;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.command.document.Exist;
import com.github.nnest.sparrow.rest.AbstractRestCommand;
import com.github.nnest.sparrow.rest.ElasticRestMethod;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder;

/**
 * rest command {@linkplain ElasticCommandKind#EXIST}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandExist extends AbstractRestCommand<Exist> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#convertToCommandResult(org.elasticsearch.client.Response,
	 *      com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected ElasticCommandResult convertToCommandResult(Response response, Exist command)
			throws ElasticExecutorException {
		ExistResponse resultData = new ExistResponse();
		resultData.setFound(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
		return new DefaultElasticCommandResult(command, resultData);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#readResponse(com.github.nnest.sparrow.ElasticCommand,
	 *      java.io.InputStream)
	 * @deprecated
	 */
	@Override
	@Deprecated
	protected ElasticCommandResultData readResponse(Exist command, InputStream stream) throws ElasticExecutorException {
		throw new UnsupportedOperationException("#readResponse is unnecessary here.");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#convertToRestRequest(com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected RestRequest convertToRestRequest(Exist command) throws ElasticExecutorException {
		ElasticDocumentDescriptor descriptor = command.getDocumentDescriptor();

		RestRequest request = new RestRequest();
		String endpoint = RestCommandEndpointBuilder.buildEndpoint(descriptor, command.getId());
		request.setEndpoint(endpoint);
		// use PUT for id given
		request.setMethod(ElasticRestMethod.HEAD.name());
		return request;
	}
}
