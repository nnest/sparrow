/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.io.StringWriter;

import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.command.document.Update;
import com.github.nnest.sparrow.rest.ElasticRestMethod;
import com.github.nnest.sparrow.rest.command.AbstractRestCommand;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder;
import com.google.common.base.Strings;

/**
 * Rest command update, {@linkplain ElasticCommandKind#UPDATE}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandUpdate extends AbstractRestCommand<Update, UpdateResponse> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#getResponseClass()
	 */
	@Override
	protected Class<UpdateResponse> getResponseClass() {
		return UpdateResponse.class;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#convertToRestRequest(com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected RestRequest convertToRestRequest(Update command) throws ElasticExecutorException {
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

		request.setEndpoint(RestCommandEndpointBuilder.buildEndpoint(descriptor, idValue, versionValue, "_update"));
		request.setMethod(ElasticRestMethod.POST.name());

		StringWriter documentJSONString = new StringWriter();
		try {
			this.createRequestObjectMapper(descriptor).writeValue(documentJSONString, new UpdateRequestObject(command));
		} catch (Exception e) {
			throw new ElasticExecutorException(String.format("Fail to parse document[%1$s] to JSON.", documentType), e);
		}
		request.setEntity(new StringEntity(documentJSONString.toString(), "UTF-8"));
		return request;
	}

	/**
	 * update request object
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class UpdateRequestObject {
		@JsonProperty
		private Object doc = null;
		@JsonProperty(value = "detect_noop")
		private boolean detectNoop = false;
		@JsonProperty(value = "doc_as_upsert", defaultValue = "false")
		@JsonInclude(Include.NON_DEFAULT)
		private boolean asUpsert = false;

		public UpdateRequestObject(Update command) {
			this.doc = command.getDocument();
			this.detectNoop = command.isDetectNoopChanged();
			this.asUpsert = command.isAsUpsert();
		}

		/**
		 * @return the doc
		 */
		public Object getDoc() {
			return doc;
		}

		/**
		 * @return the detectNoop
		 */
		public boolean isDetectNoop() {
			return detectNoop;
		}

		/**
		 * @return the asUpsert
		 */
		public boolean isAsUpsert() {
			return asUpsert;
		}
	}
}
