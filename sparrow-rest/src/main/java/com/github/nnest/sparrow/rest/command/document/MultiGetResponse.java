/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.nnest.sparrow.command.document.Get;
import com.github.nnest.sparrow.command.document.MultiGetResultData;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * multiple get response
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MultiGetResponse implements MultiGetResultData {
	@JsonProperty("docs")
	private List<InnerGetResponse> innerResponses = null;

	private int count = 0;
	private int successCount = 0;
	private int failCount = 0;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandResultData#isSuccessful()
	 */
	@Override
	public boolean isSuccessful() {
		return this.count != 0 && this.count == this.successCount;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.MultiGetResultData#isPartialSuccessful()
	 */
	@Override
	public boolean isPartialSuccessful() {
		return this.count != 0 && this.successCount != 0;
	}

	/**
	 * @return the count
	 */
	public int getInnerCommandCount() {
		return count;
	}

	/**
	 * @return the successCount
	 */
	public int getSuccessCount() {
		return successCount;
	}

	/**
	 * @return the failCount
	 */
	public int getFailCount() {
		return failCount;
	}

	/**
	 * @return the innerResponses
	 */
	public List<InnerGetResponse> getInnerResponses() {
		return this.innerResponses;
	}

	/**
	 * @param innerResponses
	 *            the innerResponses to set
	 */
	@JsonDeserialize(contentAs = InnerGetResponseReceiver.class)
	public void setInnerResponses(List<InnerGetResponse> innerResponses) {
		// reset counting
		count = 0;
		successCount = 0;
		failCount = 0;
		
		this.innerResponses = innerResponses;
		if (this.innerResponses != null && this.innerResponses.size() > 0) {
			Iterables.all(innerResponses, new Predicate<GetResponse>() {
				/**
				 * (non-Javadoc)
				 * 
				 * @see com.google.common.base.Predicate#apply(java.lang.Object)
				 */
				@Override
				public boolean apply(GetResponse response) {
					count++;
					if (response.isSuccessful()) {
						successCount++;
					} else {
						failCount++;
					}
					return true;
				}
			});
		}
	}

	/**
	 * inner get response.<br>
	 * {@linkplain #jsonNode} should be clear after convert to document. see
	 * {@linkplain RestCommandMultiGet#readResponse(com.github.nnest.sparrow.command.document.MultiGet, java.io.InputStream)}
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class InnerGetResponse extends GetResponse {
		private Get command = null;

		/**
		 * set command.
		 * 
		 * @param command
		 *            command
		 */
		public void setCommand(Get command) {
			this.command = command;
		}

		/**
		 * @return the command
		 */
		public Get getCommand() {
			return command;
		}
	}

	/**
	 * inner get response receiver
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class InnerGetResponseReceiver extends InnerGetResponse {
		private InnerGetResponse get = new InnerGetResponse();
		private JsonNode jsonNode = null;

		/**
		 * unwrap and get real {@linkplain GetResponse}
		 * 
		 * @return real response
		 */
		public InnerGetResponse unwrap() {
			return this.get;
		}

		/**
		 * @return the jsonNode
		 */
		public JsonNode getJsonNode() {
			return jsonNode;
		}

		/**
		 * @param jsonNode
		 *            the jsonNode to set
		 */
		@JsonProperty("_source")
		public void setJsonNode(JsonNode jsonNode) {
			this.jsonNode = jsonNode;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#getIndex()
		 */
		@Override
		public String getIndex() {
			return get.getIndex();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#setIndex(java.lang.String)
		 */
		@Override
		@JsonProperty("_index")
		public void setIndex(String index) {
			get.setIndex(index);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#getType()
		 */
		@Override
		public String getType() {
			return get.getType();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#setType(java.lang.String)
		 */
		@Override
		@JsonProperty("_type")
		public void setType(String type) {
			get.setType(type);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#getId()
		 */
		@Override
		public String getId() {
			return get.getId();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#setId(java.lang.String)
		 */
		@Override
		@JsonProperty("_id")
		public void setId(String id) {
			get.setId(id);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#getVersion()
		 */
		@Override
		public String getVersion() {
			return get.getVersion();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#setVersion(java.lang.String)
		 */
		@Override
		@JsonProperty("_version")
		public void setVersion(String version) {
			get.setVersion(version);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#isFound()
		 */
		@Override
		public boolean isFound() {
			return get.isFound();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#setFound(boolean)
		 */
		@Override
		public void setFound(boolean found) {
			get.setFound(found);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#isSuccessful()
		 */
		@Override
		public boolean isSuccessful() {
			return get.isSuccessful();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#getDocument()
		 */
		@Override
		public <T> T getDocument() {
			return get.getDocument();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#setDocument(java.lang.Object)
		 */
		@Override
		public void setDocument(Object document) {
			get.setDocument(document);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.MultiGetResponse.InnerGetResponse#setCommand(com.github.nnest.sparrow.command.document.Get)
		 */
		@Override
		public void setCommand(Get command) {
			get.setCommand(command);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.MultiGetResponse.InnerGetResponse#getCommand()
		 */
		@Override
		public Get getCommand() {
			return get.getCommand();
		}

		/**
		 * transform document
		 * 
		 * @param mapper
		 *            mapper
		 * @param documentType
		 *            document type
		 * @throws JsonProcessingException
		 */
		public void transformDocument(ObjectMapper mapper, Class<?> documentType) throws JsonProcessingException {
			this.setDocument(mapper.treeToValue(this.getJsonNode(), documentType));
		}
	}
}
