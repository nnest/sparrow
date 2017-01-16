/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.command.document.Get;
import com.github.nnest.sparrow.rest.ElasticRestMethod;
import com.github.nnest.sparrow.rest.command.AbstractRestCommand;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder.SetQueryParam;
import com.google.common.collect.Sets;

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
			Class<?> documentType = command.getDocumentType();
			ObjectMapper mapper = this.createResponseObjectMapper(this.getResponseClass()).copy();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(GetResponse.class, new GetResponseDeserializer(mapper, documentType));
			mapper.registerModule(module);
			return mapper.readValue(stream, GetResponse.class);
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
		String endpoint = RestCommandEndpointBuilder.buildEndpoint(descriptor, command.getId(), null,
				Sets.newHashSet(new SetQueryParam("_source_include", command.getIncludes()),
						new SetQueryParam("_source_exclude", command.getExcludes())));
		request.setEndpoint(endpoint);
		// use PUT for id given
		request.setMethod(ElasticRestMethod.GET.name());
		return request;
	}

	/**
	 * get response deserializer
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class GetResponseDeserializer extends StdDeserializer<GetResponse> {
		private static final long serialVersionUID = 2100430478383947559L;
		private ObjectMapper mapper = null;
		private Class<?> documentType = null;

		public GetResponseDeserializer(ObjectMapper mapper, Class<?> documentType) {
			this(null);

			this.mapper = mapper;
			this.documentType = documentType;
		}

		private GetResponseDeserializer(Class<?> vc) {
			super(vc);
		}

		/**
		 * @return the mapper
		 */
		public ObjectMapper getMapper() {
			return mapper;
		}

		/**
		 * @return the documentType
		 */
		public Class<?> getDocumentType() {
			return documentType;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser,
		 *      com.fasterxml.jackson.databind.DeserializationContext)
		 */
		@Override
		public GetResponse deserialize(JsonParser parser, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			GetResponse resp = new GetResponse();

			JsonNode node = parser.getCodec().readTree(parser);
			resp.setIndex(node.get("_index").asText());
			resp.setType(node.get("_type").asText());
			resp.setId(node.get("_id").asText());
			resp.setVersion(node.get("_version").asText());
			resp.setFound(node.get("found").asBoolean());
			resp.setDocument(mapper.treeToValue(node.get("_source"), documentType));

			return resp;
		}
	}
}
