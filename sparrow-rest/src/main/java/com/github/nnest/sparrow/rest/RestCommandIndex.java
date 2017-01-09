/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.io.StringWriter;

import org.apache.http.entity.StringEntity;
import org.elasticsearch.client.Response;

import com.github.nnest.sparrow.DefaultElasticCommandResult;
import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
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
	protected ElasticCommandResult convertToCommandResult(Response response, ElasticCommand command) {
		return new DefaultElasticCommandResult(command.getOriginalDocument());
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
		request.setMethod(ElasticRestMethod.PUT.name());

		Object document = command.getOriginalDocument();
		Class<?> documentType = document.getClass();
		String idField = descriptor.getIdField();
		String idValue = this.getIdValue(document, idField);

		if (Strings.nullToEmpty(idValue).trim().length() == 0) {
			// no id identified
			request.setEndpoint(String.format("%1$s/%2$s", descriptor.getIndex(), descriptor.getType()));
		} else {
			request.setEndpoint(String.format("%1$s/%2$s/%3$s", descriptor.getIndex(), descriptor.getType(), idValue));
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
