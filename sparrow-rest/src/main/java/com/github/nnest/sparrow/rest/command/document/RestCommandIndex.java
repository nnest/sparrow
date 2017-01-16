/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.io.InputStream;
import java.io.StringWriter;

import org.apache.http.entity.StringEntity;
import org.elasticsearch.client.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticCommandResultData;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.command.document.Index;
import com.github.nnest.sparrow.rest.AbstractRestCommand;
import com.github.nnest.sparrow.rest.ElasticRestMethod;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder;
import com.google.common.base.Strings;

/**
 * rest command {@linkplain ElasticCommandKind#INDEX}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandIndex<C extends Index> extends AbstractRestCommand<C> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#convertToCommandResult(org.elasticsearch.client.Response,
	 *      com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected ElasticCommandResult convertToCommandResult(Response response, C command)
			throws ElasticExecutorException {
		ElasticCommandResult result = super.convertToCommandResult(response, command);
		ElasticDocumentDescriptor descriptor = command.getDocumentDescriptor();
		Object document = command.getDocument();
		String idField = descriptor.getIdField();
		String idValue = this.getIdValue(document, idField);

		if (Strings.nullToEmpty(idValue).trim().length() == 0) {
			IndexResponse indexResponse = (IndexResponse) result.getResultData();
			String resultIdValue = indexResponse.getId();
			this.setIdValue(document, idField, resultIdValue);
		}

		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#readResponse(com.github.nnest.sparrow.ElasticCommand,
	 *      java.io.InputStream)
	 */
	@Override
	protected ElasticCommandResultData readResponse(C command, InputStream stream) throws ElasticExecutorException {
		try {
			IndexResponse response = new ObjectMapper().readValue(stream, IndexResponse.class);
			response.setDocument(command.getDocument());
			return response;
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
	protected RestRequest convertToRestRequest(C command) throws ElasticExecutorException {
		ElasticDocumentDescriptor descriptor = command.getDocumentDescriptor();

		RestRequest request = new RestRequest();

		Object document = command.getDocument();
		Class<?> documentType = document.getClass();
		String idField = descriptor.getIdField();
		String idValue = this.getIdValue(document, idField);
		String versionField = descriptor.getVersionField();
		String versionValue = null;
		if (Strings.nullToEmpty(versionField).length() > 0) {
			versionValue = this.getVersionValue(document, versionField);
		}

		if (Strings.nullToEmpty(idValue).trim().length() == 0) {
			// no id identified
			request.setEndpoint(RestCommandEndpointBuilder.buildEndpoint(descriptor, null, versionValue,
					this.getEndpointCommandKind()));
			// use POST for auto id creation
			request.setMethod(ElasticRestMethod.POST.name());
		} else {
			request.setEndpoint(RestCommandEndpointBuilder.buildEndpoint(descriptor, idValue, versionValue,
					this.getEndpointCommandKind()));
			// use PUT for id given
			request.setMethod(ElasticRestMethod.PUT.name());
		}

		StringWriter documentJSONString = new StringWriter();
		try {
			this.createObjectMapper(descriptor).writeValue(documentJSONString, document);
		} catch (Exception e) {
			throw new ElasticExecutorException(String.format("Fail to parse document[%1$s] to JSON.", documentType), e);
		}
		request.setEntity(new StringEntity(documentJSONString.toString(), "UTF-8"));
		return request;
	}

	/**
	 * get endpoint command kind
	 * 
	 * @return
	 */
	protected String getEndpointCommandKind() {
		return null;
	}
}
