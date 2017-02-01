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
import com.github.nnest.sparrow.command.document.UpdateByScript;
import com.github.nnest.sparrow.command.script.Script;
import com.github.nnest.sparrow.rest.ElasticRestMethod;
import com.github.nnest.sparrow.rest.command.AbstractRestCommand;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder.VersionQueryParam;
import com.google.common.base.Strings;

/**
 * update by script, {@linkplain ElasticCommandKind#UPDATE_BY_SCRIPT}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandUpdateByScript extends AbstractRestCommand<UpdateByScript, UpdateResponse> {
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
	protected RestRequest convertToRestRequest(UpdateByScript command) throws ElasticExecutorException {
		ElasticDocumentDescriptor descriptor = command.getDocumentDescriptor();

		RestRequest request = new RestRequest();

		Class<?> documentType = command.getDocumentType();
		String idValue = command.getId();
		String versionValue = command.getVersion();
		Object document = command.getDocument();
		// if document assigned, means upsert
		if (document != null) {
			// get id value if not present
			if (Strings.nullToEmpty(idValue).length() == 0) {
				idValue = this.getIdValue(document, descriptor.getIdField());
			}

			// get version value if not present
			if (Strings.nullToEmpty(versionValue).length() == 0) {
				String versionField = descriptor.getVersionField();
				if (Strings.nullToEmpty(versionField).length() > 0) {
					versionValue = this.getVersionValue(document, versionField);
					request.setParams(RestCommandEndpointBuilder
							.transformQueryParameters(VersionQueryParam.withVersion(versionValue)));
				}
			}
		}

		request.setEndpoint(RestCommandEndpointBuilder.buildEndpoint(descriptor, idValue, "_update"));
		request.setMethod(ElasticRestMethod.POST.name());

		StringWriter documentJSONString = new StringWriter();
		try {
			this.createRequestObjectMapper(descriptor).writeValue(documentJSONString,
					new UpdateByScriptRequestObject(command));
		} catch (Exception e) {
			throw new ElasticExecutorException(String.format("Fail to parse document[%1$s] to JSON.", documentType), e);
		}
		request.setEntity(new StringEntity(documentJSONString.toString(), "UTF-8"));
		return request;
	}

	/**
	 * update by script request object
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class UpdateByScriptRequestObject {
		@JsonProperty
		private Script script = null;
		@JsonProperty("upsert")
		@JsonInclude(Include.NON_NULL)
		private Object document = null;
		@JsonProperty(value = "scripted_upsert", defaultValue = "false")
		@JsonInclude(Include.NON_DEFAULT)
		private boolean asUpsert = false;

		public UpdateByScriptRequestObject(UpdateByScript command) {
			this.script = command.getScript();
			this.document = command.getDocument();
			this.asUpsert = command.isAsUpsert();
		}

		/**
		 * @return the script
		 */
		public Script getScript() {
			return script;
		}

		/**
		 * @return the document
		 */
		public Object getDocument() {
			return document;
		}

		/**
		 * @return the asUpsert
		 */
		public boolean isAsUpsert() {
			return asUpsert;
		}
	}
}
