/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

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
import com.google.common.base.Joiner;
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
		String versionField = descriptor.getVersionField();
		String versionValue = null;
		if (Strings.nullToEmpty(versionField).length() > 0) {
			versionValue = this.getVersionValue(document, versionField);
		}

		if (Strings.nullToEmpty(idValue).trim().length() == 0) {
			// no id identified
			request.setEndpoint(this.buildEndpoint(descriptor, null, versionValue));
			// use POST for auto id creation
			request.setMethod(ElasticRestMethod.POST.name());
		} else {
			request.setEndpoint(buildEndpoint(descriptor, idValue, versionValue));
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
	 * build endpoint
	 * 
	 * @param descriptor
	 *            document descriptor
	 * @param idValue
	 *            id value
	 * @param versionValue
	 *            version value
	 * @return endpoint uri
	 */
	protected String buildEndpoint(ElasticDocumentDescriptor descriptor, String idValue, String versionValue) {
		// return String.format("%1$s/%2$s/%3$s", descriptor.getIndex(),
		// descriptor.getType(), idValue);
		List<String> parts = new LinkedList<String>();
		parts.add(descriptor.getIndex());
		parts.add(descriptor.getType());
		if (idValue != null) {
			parts.add(idValue);
		}
		this.moreParts(parts);
		String loc = Joiner.on('/').join(parts);
		if (versionValue != null) {
			return loc + "?version=" + versionValue;
		} else {
			return loc;
		}
	}

	/**
	 * add into parts if there are more, default do nothing
	 * 
	 * @param parts
	 */
	protected void moreParts(List<String> parts) {
	}
}
