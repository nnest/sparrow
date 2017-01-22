/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.io.StringWriter;

import org.apache.http.entity.StringEntity;
import org.elasticsearch.client.Response;

import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.command.document.Index;
import com.github.nnest.sparrow.rest.ElasticRestMethod;
import com.github.nnest.sparrow.rest.command.AbstractRestCommand;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder;
import com.google.common.base.Strings;

/**
 * rest command {@linkplain ElasticCommandKind#INDEX}.<br>
 * Note if create id value by elastic search itself, command set the generated
 * id value to document automatically, <strong>BUT, in elastic search server,
 * the id field is still null, which means, be careful about this.</strong>
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandIndex<C extends Index> extends AbstractRestCommand<C, IndexResponse> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#convertToCommandResult(org.elasticsearch.client.Response,
	 *      com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected ElasticCommandResult convertToCommandResult(Response response, C command)
			throws ElasticExecutorException {
		ElasticCommandResult result = super.convertToCommandResult(response, command);
		ElasticDocumentDescriptor documentDescriptor = command.getDocumentDescriptor();
		this.setIdValueIfNull(command.getDocument(), documentDescriptor, new IdDetective() {
			/**
			 * (non-Javadoc)
			 * 
			 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand.IdDetective#findIdValue()
			 */
			@Override
			public String findIdValue() {
				return ((IndexResponse) result.getResultData()).getId();
			}
		});

		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#completeResponse(com.github.nnest.sparrow.ElasticCommandResultData,
	 *      com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected IndexResponse completeResponse(IndexResponse response, C command) {
		response.setDocument(command.getDocument());
		return response;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#getResponseClass()
	 */
	@Override
	protected Class<IndexResponse> getResponseClass() {
		return IndexResponse.class;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#convertToRestRequest(com.github.nnest.sparrow.ElasticCommand)
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
			if (!this.isIdAutoCreateionAllowed()) {
				throw new ElasticExecutorException(
						String.format("Id is required for [%1$s]", command.getCommandKind()));
			}
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
			this.createRequestObjectMapper(descriptor).writeValue(documentJSONString, document);
		} catch (Exception e) {
			throw new ElasticExecutorException(String.format("Fail to parse document[%1$s] to JSON.", documentType), e);
		}
		request.setEntity(new StringEntity(documentJSONString.toString(), "UTF-8"));
		return request;
	}

	/**
	 * get endpoint command kind
	 * 
	 * @return endpoint command kind
	 */
	protected String getEndpointCommandKind() {
		return null;
	}

	/**
	 * is id auto creation allowed, default returns true
	 * 
	 * @return allow
	 */
	protected boolean isIdAutoCreateionAllowed() {
		return true;
	}
}
