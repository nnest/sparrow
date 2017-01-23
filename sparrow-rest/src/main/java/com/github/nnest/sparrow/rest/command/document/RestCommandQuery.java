/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.io.StringWriter;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.command.document.Query;
import com.github.nnest.sparrow.command.document.QueryResultData;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.rest.command.AbstractRestCommand;
import com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder;
import com.github.nnest.sparrow.rest.command.RestCommandUtil;

/**
 * rest command query, {@linkplain ElasticCommandKind#QUERY}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandQuery extends AbstractRestCommand<Query, QueryResultData> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#getResponseClass()
	 */
	@Override
	protected Class<QueryResultData> getResponseClass() {
		return QueryResultData.class;
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
		request.setEntity(new StringEntity(documentJSONString.toString(), "UTF-8"));
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
	 * query request object
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class QueryRequestObject {
		@JsonProperty("query")
		private Example example = null;

		public QueryRequestObject(Query command) {
			this.example = command.getExample();
		}

		/**
		 * @return the example
		 */
		public Example getExample() {
			return example;
		}
	}
}
