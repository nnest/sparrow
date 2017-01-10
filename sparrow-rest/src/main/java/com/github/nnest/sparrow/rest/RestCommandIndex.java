/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.io.InputStream;
import java.io.StringWriter;

import org.apache.http.entity.StringEntity;
import org.elasticsearch.client.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.rest.response.IndexResponse;
import com.github.nnest.sparrow.rest.response.RestResponseObject;
import com.google.common.base.Strings;

/**
 * rest command {@linkplain ElasticCommandKind#INDEX}
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
	protected ElasticCommandResult convertToCommandResult(Response response, ElasticCommand command)
			throws ElasticExecutorException {
		RestElasticCommandResult result = (RestElasticCommandResult) super.convertToCommandResult(response, command);

		ElasticDocumentDescriptor descriptor = command.getDescriptor();
		Object document = command.getOriginalDocument();
		String idField = descriptor.getIdField();
		String idValue = this.getIdValue(document, idField);

		if (Strings.nullToEmpty(idValue).trim().length() == 0) {
			IndexResponse indexResponse = (IndexResponse) result.getResponseObject();
			String resultIdValue = indexResponse.getId();
			this.setIdValue(document, idField, resultIdValue);
		}

		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#readRestResponse(java.io.InputStream)
	 */
	@Override
	protected RestResponseObject readRestResponse(InputStream stream) throws ElasticExecutorException {
		try {
			return new ObjectMapper().readValue(stream, IndexResponse.class);
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
	protected RestRequest convertToRestRequest(ElasticCommand command) throws ElasticExecutorException {
		ElasticDocumentDescriptor descriptor = command.getDescriptor();

		RestRequest request = new RestRequest();

		Object document = command.getOriginalDocument();
		Class<?> documentType = document.getClass();
		String idField = descriptor.getIdField();
		String idValue = this.getIdValue(document, idField);

		if (Strings.nullToEmpty(idValue).trim().length() == 0) {
			// no id identified
			request.setEndpoint(String.format("%1$s/%2$s", descriptor.getIndex(), descriptor.getType()));
			// use POST for auto id creation
			request.setMethod(ElasticRestMethod.POST.name());
		} else {
			request.setEndpoint(String.format("%1$s/%2$s/%3$s", descriptor.getIndex(), descriptor.getType(), idValue));
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
}
