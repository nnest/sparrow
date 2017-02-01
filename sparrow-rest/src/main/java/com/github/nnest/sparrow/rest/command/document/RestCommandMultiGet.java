/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.command.document.Get;
import com.github.nnest.sparrow.command.document.MultiGet;
import com.github.nnest.sparrow.command.document.MultiGetResultData.InnerGetResultData;
import com.github.nnest.sparrow.rest.ElasticRestMethod;
import com.github.nnest.sparrow.rest.command.AbstractRestCommand;
import com.github.nnest.sparrow.rest.command.RestCommandUtil;
import com.github.nnest.sparrow.rest.command.document.MultiGetResponse.InnerGetResponseReceiver;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * multiple get, {@linkplain ElasticCommandKind#MULTI_GET}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandMultiGet extends AbstractRestCommand<MultiGet, MultiGetResponse> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#readResponse(com.github.nnest.sparrow.ElasticCommand,
	 *      java.io.InputStream)
	 */
	@Override
	protected MultiGetResponse readResponse(MultiGet command, InputStream stream) throws ElasticExecutorException {
		try {
			ObjectMapper mapper = this.createResponseObjectMapper(this.getResponseClass());
			MultiGetResponse response = mapper.readValue(stream, MultiGetResponse.class);
			List<InnerGetResultData> innerResponses = response.getInnerResponses();
			if (innerResponses != null) {
				for (int index = 0, count = innerResponses.size(); index < count; index++) {
					InnerGetResponseReceiver innerResponse = (InnerGetResponseReceiver) innerResponses.get(index);
					Get innerCommand = command.getCommands().get(index);
					innerResponse.setCommand(innerCommand);
					if (innerResponse.isFound()) {
						innerResponse.transformDocument(mapper, innerCommand.getDocumentType());
						// set id value when it is null, use value of "_id"
						this.setIdValueIfNull(innerResponse.getDocument(),
								innerResponse.getCommand().getDocumentDescriptor(), new IdDetective() {
									/**
									 * (non-Javadoc)
									 * 
									 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand.IdDetective#findIdValue()
									 */
									@Override
									public String findIdValue() {
										return innerResponse.getId();
									}
								});
					}
				}
				// unwrap and discard json node objects in receiver
				response.setInnerResponses(
						Lists.transform(innerResponses, new Function<InnerGetResultData, InnerGetResultData>() {
							/**
							 * (non-Javadoc)
							 * 
							 * @see com.google.common.base.Function#apply(java.lang.Object)
							 */
							@Override
							public InnerGetResultData apply(InnerGetResultData input) {
								return ((InnerGetResponseReceiver) input).unwrap();
							}
						}));
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
	protected Class<MultiGetResponse> getResponseClass() {
		return MultiGetResponse.class;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.AbstractRestCommand#convertToRestRequest(com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	protected RestRequest convertToRestRequest(MultiGet command) throws ElasticExecutorException {
		RestRequest request = new RestRequest();

		CommandContent content = computeCommandContent(command);
		request.setEndpoint(this.buildEndpoint(content));
		// use GET for id given
		request.setMethod(ElasticRestMethod.GET.name());
		StringWriter documentJSONString = new StringWriter();
		try {
			this.createRequestObjectMapper(content).writeValue(documentJSONString, new MultiGetRequestObject(content));
		} catch (Exception e) {
			throw new ElasticExecutorException("Fail to parse request of multiple get to JSON.", e);
		}
		request.setEntity(new StringEntity(documentJSONString.toString(), "UTF-8"));
		return request;
	}

	/**
	 * create request object mapper
	 * 
	 * @param content
	 *            content
	 * @return object mapper
	 */
	protected ObjectMapper createRequestObjectMapper(CommandContent content) {
		return RestCommandUtil.getObjectMapper();
	}

	/**
	 * compute command content
	 * 
	 * @param command
	 *            command
	 * @return command content
	 */
	protected CommandContent computeCommandContent(MultiGet command) {
		final CommandContent content = new CommandContent();
		Iterables.all(command.getCommands(), new Predicate<Get>() {
			/**
			 * (non-Javadoc)
			 * 
			 * @see com.google.common.base.Predicate#apply(java.lang.Object)
			 */
			@Override
			public boolean apply(Get singleGetCommand) {
				content.with(singleGetCommand);
				return true;
			}
		});
		return content;
	}

	/**
	 * build endpoint
	 * 
	 * @param content
	 *            command content
	 * @return endpoint
	 */
	protected String buildEndpoint(CommandContent content) {
		if (content.isMultipleIndices()) {
			// multiple indices
			return "/_mget";
		} else if (content.isMultipleTypes()) {
			// single index, multiple types
			return Joiner.on("/").join(content.getFirstIndex(), "_mget");
		} else {
			// single index, single types
			return Joiner.on("/").join(content.getFirstIndex(), content.getFirstType(), "_mget");
		}
	}

	/**
	 * multiple get type. check the command with multiple indices or types
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class CommandContent {
		private Set<String> indexNames = new HashSet<>();
		private Set<String> typeNames = new HashSet<>();
		private List<Get> innerCommands = new LinkedList<>();

		protected void with(String indexName, String typeName) {
			indexNames.add(indexName);
			typeNames.add(indexName + "/" + typeName);
		}

		public void with(Get innerCommand) {
			ElasticDocumentDescriptor documentDescriptor = innerCommand.getDocumentDescriptor();
			this.with(documentDescriptor.getIndex(), documentDescriptor.getType());
			this.innerCommands.add(innerCommand);
		}

		/**
		 * is multiple index names or not
		 * 
		 * @return true if more than one index name
		 */
		public boolean isMultipleIndices() {
			return indexNames.size() > 1;
		}

		/**
		 * is multiple type names or not.
		 * 
		 * @return true if {@linkplain #isMultipleIndices()} returns true or
		 *         more than one type name
		 */
		public boolean isMultipleTypes() {
			return this.isMultipleIndices() || typeNames.size() > 1;
		}

		/**
		 * @return the innerCommands
		 */
		public List<Get> getInnerCommands() {
			return innerCommands;
		}

		/**
		 * get index, always returns first
		 * 
		 * @return index name
		 */
		public String getFirstIndex() {
			return this.innerCommands.get(0).getDocumentDescriptor().getIndex();
		}

		/**
		 * get type, always returns first
		 * 
		 * @return type name
		 */
		public Object getFirstType() {
			return this.innerCommands.get(0).getDocumentDescriptor().getType();
		}
	}

	/**
	 * multiple get request object
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class MultiGetRequestObject {
		private List<GetRequestObject> docs = null;

		public MultiGetRequestObject(final CommandContent content) {
			this.docs = Lists.transform(content.getInnerCommands(), new Function<Get, GetRequestObject>() {
				/**
				 * (non-Javadoc)
				 * 
				 * @see com.google.common.base.Function#apply(java.lang.Object)
				 */
				@Override
				public GetRequestObject apply(Get command) {
					ElasticDocumentDescriptor documentDescriptor = command.getDocumentDescriptor();
					return new GetRequestObject() //
							.withIndex(documentDescriptor.getIndex(), content.isMultipleIndices()) //
							.withType(documentDescriptor.getType(), content.isMultipleTypes()) //
							.withId(command.getId()) //
							.withIncludes(command.getIncludes()) //
							.withExcludes(command.getExcludes());
				}
			});
		}

		/**
		 * @return the docs
		 */
		public List<GetRequestObject> getDocs() {
			return docs;
		}
	}

	/**
	 * single get request object
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class GetRequestObject {
		@JsonProperty("_index")
		@JsonInclude(Include.NON_NULL)
		private String index = null;
		@JsonProperty("_type")
		@JsonInclude(Include.NON_NULL)
		private String type = null;
		@JsonProperty("_id")
		private String id = null;
		@JsonProperty("_source")
		@JsonInclude(Include.NON_NULL)
		private GetSourceFilter sourceFilter = null;

		/**
		 * @return the index
		 */
		public String getIndex() {
			return index;
		}

		/**
		 * set index, only when multiple indices is true. otherwise do nothing
		 * 
		 * @param index
		 *            the index to set
		 * @param multipleIndices
		 *            is multiple indcies or not
		 * @return this
		 */
		public GetRequestObject withIndex(String index, boolean multipleIndices) {
			if (multipleIndices) {
				this.index = index;
			}
			return this;
		}

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * set type, only when multiple types is true. otherwise do nothing
		 * 
		 * @param type
		 *            the type to set
		 * @param multipleTypes
		 *            is multiple types or not
		 * @return this
		 */
		public GetRequestObject withType(String type, boolean multipleTypes) {
			if (multipleTypes) {
				this.type = type;
			}
			return this;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id
		 *            the id to set
		 * @return this
		 */
		public GetRequestObject withId(String id) {
			this.id = id;
			return this;
		}

		/**
		 * @return the sourceFilter
		 */
		public GetSourceFilter getSourceFilter() {
			return sourceFilter;
		}

		/**
		 * with includes
		 * 
		 * @param includes
		 *            includes
		 * @return this
		 */
		public GetRequestObject withIncludes(Set<String> includes) {
			if (includes == null || includes.size() == 0) {
				return this;
			}

			if (sourceFilter == null) {
				sourceFilter = new GetSourceFilter();
			}
			sourceFilter.setIncludes(includes);
			return this;
		}

		/**
		 * with excludes
		 * 
		 * @param excludes
		 *            excludes
		 * @return this
		 */
		public GetRequestObject withExcludes(Set<String> excludes) {
			if (excludes == null || excludes.size() == 0) {
				return this;
			}

			if (sourceFilter == null) {
				sourceFilter = new GetSourceFilter();
			}
			sourceFilter.setExcludes(excludes);
			return this;
		}
	}

	/**
	 * get source filter
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class GetSourceFilter {
		@JsonProperty("include")
		@JsonInclude(Include.NON_EMPTY)
		private Set<String> includes = null;
		@JsonProperty("exclude")
		@JsonInclude(Include.NON_EMPTY)
		private Set<String> excludes = null;

		/**
		 * @return the includes
		 */
		public Set<String> getIncludes() {
			return includes;
		}

		/**
		 * @param includes
		 *            the includes to set
		 */
		public void setIncludes(Set<String> includes) {
			this.includes = includes;
		}

		/**
		 * @return the excludes
		 */
		public Set<String> getExcludes() {
			return excludes;
		}

		/**
		 * @param excludes
		 *            the excludes to set
		 */
		public void setExcludes(Set<String> excludes) {
			this.excludes = excludes;
		}
	}
}
