/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nnest.sparrow.command.document.GetResultData;

/**
 * Get response
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class GetResponse implements GetResultData {
	private String index = null;
	private String type = null;
	private String id = null;
	private String version = null;
	private boolean found = true;
	private Object document = null;

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the found
	 */
	public boolean isFound() {
		return found;
	}

	/**
	 * @param found
	 *            the found to set
	 */
	public void setFound(boolean found) {
		this.found = found;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandResultData#isSuccessful()
	 */
	@Override
	public boolean isSuccessful() {
		return this.isFound();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.GetResultData#getDocument()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getDocument() {
		return (T) this.document;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Object document) {
		this.document = document;
	}

	/**
	 * since the type of {@linkplain #setDocument(Object)} is dynamic, which
	 * means cannot receive and transform to object directly by jackson. so the
	 * receiver receive the document data as {@linkplain JsonNode}, and
	 * translate it to document.
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class GetResponseReceiver extends GetResponse {
		private GetResponse get = new GetResponse();
		private JsonNode jsonNode = null;

		/**
		 * unwrap and get real {@linkplain GetResponse}
		 * 
		 * @return real response
		 */
		public GetResponse unwrap() {
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
		 * transform document
		 * 
		 * @param mapper
		 *            mapper
		 * @param documentType
		 *            document type
		 * @throws JsonProcessingException
		 *             exception
		 */
		public void transformDocument(ObjectMapper mapper, Class<?> documentType) throws JsonProcessingException {
			if (this.getJsonNode() != null) {
				this.setDocument(mapper.treeToValue(this.getJsonNode(), documentType));
			}
		}
	}
}
