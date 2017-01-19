/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
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

	public void setInnerResponses(List<InnerGetResponse> innerResponses) {
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
		private JsonNode jsonNode = null;
		private Get command = null;

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#setIndex(java.lang.String)
		 */
		@Override
		@JsonProperty("_index")
		public void setIndex(String index) {
			super.setIndex(index);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#setType(java.lang.String)
		 */
		@Override
		@JsonProperty("_type")
		public void setType(String type) {
			super.setType(type);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#setId(java.lang.String)
		 */
		@Override
		@JsonProperty("_id")
		public void setId(String id) {
			super.setId(id);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.GetResponse#setVersion(java.lang.String)
		 */
		@Override
		@JsonProperty("_version")
		public void setVersion(String version) {
			super.setVersion(version);
		}

		/**
		 * @param jsonNode
		 *            the jsonNode to set
		 */
		@JsonProperty("_source")
		protected void setJsonNode(JsonNode jsonNode) {
			this.jsonNode = jsonNode;
		}

		/**
		 * get json node
		 * 
		 * @return json node
		 */
		protected JsonNode getJsonNode() {
			return this.jsonNode;
		}

		/**
		 * set command.
		 * 
		 * @param command
		 */
		@JsonIgnore
		protected void setCommand(Get command) {
			this.command = command;
		}

		/**
		 * @return the command
		 */
		public Get getCommand() {
			return command;
		}
	}
}
