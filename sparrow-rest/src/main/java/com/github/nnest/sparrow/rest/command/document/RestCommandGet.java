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
			ElasticDocumentDescriptor documentDescriptor = command.getDocumentDescriptor();
			ObjectMapper mapper = this.createResponseObjectMapper(this.getResponseClass()).copy();
			SimpleModule module = new SimpleModule();
			module.addDeserializer(GetResponse.class, new GetResponseDeserializer(mapper, documentDescriptor));
			mapper.registerModule(module);
			GetResponse response = mapper.readValue(stream, GetResponse.class);

			// set id value when it is null, use value of "_id"
			this.setIdValueIfNull(response.getDocument(), documentDescriptor, new IdDetective() {
				/**
				 * (non-Javadoc)
				 * 
				 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand.IdDetective#findIdValue()
				 */
				@Override
				public String findIdValue() {
					return response.getId();
				}
			});
			return response;
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
		String endpoint = RestCommandEndpointBuilder.buildEndpoint(descriptor, command.getId());
		request.setEndpoint(endpoint);
		request.setParams(RestCommandEndpointBuilder.transformQueryParameters(
				new SetQueryParam("_source_include", command.getIncludes()),
				new SetQueryParam("_source_exclude", command.getExcludes())));
		// use GET for id given
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
		private ElasticDocumentDescriptor documentDescriptor = null;

		public GetResponseDeserializer(ObjectMapper mapper, ElasticDocumentDescriptor documentDescriptor) {
			this(null);

			this.mapper = mapper;
			this.documentDescriptor = documentDescriptor;
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
		 * @return the documentDescriptor
		 */
		public ElasticDocumentDescriptor getDocumentDescriptor() {
			return documentDescriptor;
		}

		/**
		 * @return the documentType
		 */
		public Class<?> getDocumentType() {
			return this.getDocumentDescriptor().getDocumentClass();
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
			resp.setDocument(mapper.treeToValue(node.get("_source"), this.getDocumentType()));

			return resp;
		}
	}
}
