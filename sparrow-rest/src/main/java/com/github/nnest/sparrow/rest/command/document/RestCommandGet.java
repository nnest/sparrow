/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.command.document.Get;
import com.github.nnest.sparrow.rest.ElasticRestMethod;
import com.github.nnest.sparrow.rest.command.AbstractRestCommand;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder.SetQueryParam;

/**
 * rest command get, {@linkplain ElasticCommandKind#GET}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandGet extends AbstractRestCommand<Get, GetResponse> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#readResponse(com.github.nnest.sparrow.ElasticCommand,
	 *      java.io.InputStream)
	 */
	@Override
	protected GetResponse readResponse(Get command, InputStream stream) throws ElasticExecutorException {
		try {
			ElasticDocumentDescriptor documentDescriptor = command.getDocumentDescriptor();
			ObjectMapper mapper = this.createResponseObjectMapper(GetResponseReceiver.class);
			GetResponseReceiver response = (GetResponseReceiver) this
					.completeResponse(mapper.readValue(stream, GetResponseReceiver.class), command);
			response.transformDocument(mapper, documentDescriptor.getDocumentClass());
			// set id value when it is null, use value of "_id"
			this.setIdValueIfNull(response.getDocument(), documentDescriptor, new IdDetective() {
				/**
				 * (non-Javadoc)
				 * 
				 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand.IdDetective#findIdValue()
				 */
				@Override
				public String findIdValue() {
					return response.getId();
				}
			});
			return response.unwrap();
		} catch (Exception e) {
			throw new ElasticExecutorException("Fail to read data from response.", e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#getResponseClass()
	 */
	@Override
	protected Class<GetResponse> getResponseClass() {
		return GetResponse.class;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#convertToRestRequest(com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected RestRequest convertToRestRequest(Get command) throws ElasticExecutorException {
		ElasticDocumentDescriptor descriptor = command.getDocumentDescriptor();

		RestRequest request = new RestRequest();
		String endpoint = RestCommandEndpointBuilder.buildEndpoint(descriptor, command.getId());
		request.setEndpoint(endpoint);
		request.setParams(RestCommandEndpointBuilder.transformQueryParameters(
				new SetQueryParam("_source_include", command.getIncludes()),
				new SetQueryParam("_source_exclude", command.getExcludes())));
		// use GET for id given
		request.setMethod(ElasticRestMethod.GET.name());
		return request;
	}
}
