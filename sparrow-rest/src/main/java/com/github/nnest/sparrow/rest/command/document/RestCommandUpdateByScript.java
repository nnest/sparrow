/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.io.InputStream;
import java.io.StringWriter;

import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticCommandResultData;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.command.document.UpdateByScript;
import com.github.nnest.sparrow.command.script.ElasticScript;
import com.github.nnest.sparrow.rest.AbstractRestCommand;
import com.github.nnest.sparrow.rest.ElasticRestMethod;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder;
import com.github.nnest.sparrow.rest.command.RestCommandSet;
import com.google.common.base.Strings;

/**
 * update by script, {@linkplain ElasticCommandKind#UPDATE_BY_SCRIPT}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandUpdateByScript extends AbstractRestCommand<UpdateByScript> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.AbstractRestCommand#readResponse(com.github.nnest.sparrow.ElasticCommand,
	 *      java.io.InputStream)
	 */
	@Override
	protected ElasticCommandResultData readResponse(UpdateByScript command, InputStream stream)
			throws ElasticExecutorException {
		try {
			return new ObjectMapper().readValue(stream, UpdateResponse.class);
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
				}
			}
		}

		request.setEndpoint(RestCommandEndpointBuilder.buildEndpoint(descriptor, idValue, versionValue, "_update"));
		request.setMethod(ElasticRestMethod.POST.name());

		StringWriter documentJSONString = new StringWriter();
		try {
			this.createObjectMapper(descriptor, command.getScript()).writeValue(documentJSONString,
					new UpdateByScriptRequestObject(command));
		} catch (Exception e) {
			throw new ElasticExecutorException(String.format("Fail to parse document[%1$s] to JSON.", documentType), e);
		}
		request.setEntity(new StringEntity(documentJSONString.toString(), "UTF-8"));
		return request;
	}

	/**
	 * create object mapper by given script
	 * 
	 * @param documentDescriptor
	 *            document descriptor
	 * @param script
	 *            script
	 * 
	 * @return object mapper
	 */
	protected ObjectMapper createObjectMapper(ElasticDocumentDescriptor documentDescriptor, ElasticScript script) {
		ObjectMapper mapper = super.createObjectMapper(documentDescriptor);
		Class<?> mixinClass = this.getElasticScriptMixinClass(script.getClass());
		if (mixinClass != null) {
			mapper.addMixIn(ElasticScript.class, mixinClass);
		}
		Object params = script.getParamsObject();
		if (params != null) {
			Class<?> paramsMixinClass = this.getElasticScriptMixinClass(params.getClass());
			if (paramsMixinClass != null) {
				mapper.addMixIn(params.getClass(), paramsMixinClass);
			}
		}

		return mapper;
	}

	/**
	 * get elastic script or params mixin class
	 * 
	 * @param classToSerialize
	 *            script class or params class
	 * @return mixin class
	 */
	protected Class<?> getElasticScriptMixinClass(Class<?> classToSerialize) {
		return RestCommandSet.getScriptMixinClass(classToSerialize);
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
		private ElasticScript script = null;
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
		public ElasticScript getScript() {
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
