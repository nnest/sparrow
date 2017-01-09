/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.io.StringWriter;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.elasticsearch.client.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.rest.response.RestResponseObject;
import com.google.common.base.Strings;

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
	protected ElasticCommandResult convertToCommandResult(Response response, ElasticCommand command)
			throws ElasticExecutorException {
		HttpEntity entity = response.getEntity();
		try {
			RestResponseObject responseObject = new ObjectMapper().readValue(entity.getContent(),
					RestResponseObject.class);
			return new RestElasticCommandResult(command.getOriginalDocument(), responseObject);
		} catch (Exception e) {
			throw new ElasticExecutorException("Fail to read data from response", e);
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
			System.out.println(documentJSONString);
		} catch (Exception e) {
			throw new ElasticExecutorException(String.format("Fail to parse document[%1$s] to JSON.", documentType), e);
		}
		request.setEntity(new StringEntity(documentJSONString.toString(), "UTF-8"));
		return request;
	}
}
