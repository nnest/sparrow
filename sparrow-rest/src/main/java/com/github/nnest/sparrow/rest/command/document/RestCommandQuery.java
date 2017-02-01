/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.command.document.Query;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.rest.ElasticRestMethod;
import com.github.nnest.sparrow.rest.command.AbstractRestCommand;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder;
import com.github.nnest.sparrow.rest.command.RestCommandUtil;
import com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHitItem;
import com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHitItemReceiver;
import com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHits;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ExampleTypeAsIdResolver;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * rest command query, {@linkplain ElasticCommandKind#QUERY}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandQuery extends AbstractRestCommand<Query, QueryResponse> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#readResponse(com.github.nnest.sparrow.ElasticCommand,
	 *      java.io.InputStream)
	 */
	@Override
	protected QueryResponse readResponse(Query command, InputStream stream) throws ElasticExecutorException {
		try {
			ObjectMapper mapper = this.createResponseObjectMapper(this.getResponseClass());
			QueryResponse response = mapper.readValue(stream, this.getResponseClass());
			QueryHits hits = response.getHits();
			if (hits != null) {
				List<QueryHitItem> items = hits.getItems();
				if (items != null) {
					for (QueryHitItem item : items) {
						QueryHitItemReceiver itemReceiver = (QueryHitItemReceiver) item;
						ElasticDocumentDescriptor documentDescriptor = command.getHitDocumentDescriptor(item.getIndex(),
								item.getType());
						itemReceiver.transformDocument(mapper, documentDescriptor.getDocumentClass());
						// set id value when it is null, use value of "_id"
						this.setIdValueIfNull(item.getDocument(), documentDescriptor, new IdDetective() {
							/**
							 * (non-Javadoc)
							 * 
							 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand.IdDetective#findIdValue()
							 */
							@Override
							public String findIdValue() {
								return item.getId();
							}
						});
					}
					// unwrap and discard json node objects in receiver
					hits.setItems(Lists.transform(items, new Function<QueryHitItem, QueryHitItem>() {
						/**
						 * (non-Javadoc)
						 * 
						 * @see com.google.common.base.Function#apply(java.lang.Object)
						 */
						@Override
						public QueryHitItem apply(QueryHitItem input) {
							return ((QueryHitItemReceiver) input).unwrap();
						}
					}));
				}
			}
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
	protected Class<QueryResponse> getResponseClass() {
		return QueryResponse.class;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#convertToRestRequest(com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected RestRequest convertToRestRequest(Query command) throws ElasticExecutorException {
		RestRequest request = new RestRequest();

		Set<String> indices = new LinkedHashSet<>();
		Set<String> types = new LinkedHashSet<>();
		Set<Class<?>> scopeClasses = command.getScopeClasses();
		if (scopeClasses != null) {
			for (Class<?> scopeClass : scopeClasses) {
				ElasticDocumentDescriptor descriptor = command.getScopeDocumentDescriptor(scopeClass);
				indices.add(descriptor.getIndex());
				if (!command.isTypeIgnored(scopeClass)) {
					types.add(descriptor.getType());
				}
			}
		}
		request.setEndpoint(RestCommandEndpointBuilder.buildEndpoint(indices, types, null, "_search"));

		StringWriter documentJSONString = new StringWriter();
		try {
			this.createRequestObjectMapper(command).writeValue(documentJSONString, new QueryRequestObject(command));
		} catch (Exception e) {
			throw new ElasticExecutorException(
					String.format("Fail to parse command[%1$s] content to JSON.", command.getCommandKind()), e);
		}
		if (this.getLogger().isDebugEnabled()) {
			this.getLogger().debug(documentJSONString.toString());
		}
		request.setEntity(new StringEntity(documentJSONString.toString(), "UTF-8"));
		request.setMethod(ElasticRestMethod.POST.name());
		return request;
	}

	/**
	 * create request object mapper by given command
	 * 
	 * @param command
	 *            command
	 * @return object mapper
	 */
	protected ObjectMapper createRequestObjectMapper(Query command) {
		return RestCommandUtil.getObjectMapper();
	}

	/**
	 * query request object.<br>
	 * each command should be serailized as below,<br>
	 * 
	 * <pre>
	 * <code>
	 * {
	 *    "query": {
	 *      "example-type": {
	 *        something from example itself 
	 *      }
	 *    }
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonNaming(SnakeCaseStrategy.class)
	public static class QueryRequestObject {
		/**
		 * first an object named "query"<br>
		 * wrap object name by {@linkplain ExampleTypeAsIdResolver}, by example
		 * type.
		 */
		@JsonProperty("query")
		@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.CUSTOM)
		@JsonTypeIdResolver(ExampleTypeAsIdResolver.class)
		private Example example = null;

		private Integer from = null;
		private Integer size = null;

		public QueryRequestObject(Query command) {
			this.example = RestCommandUtil.wrapExample(command.getExample());
			this.from = command.getFrom();
			this.size = command.getSize();
		}

		/**
		 * @return the example
		 */
		public Example getExample() {
			return this.example;
		}

		public Integer getFrom() {
			return this.from;
		}

		public Integer getSize() {
			return this.size;
		}
	}
}
