/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.nnest.sparrow.command.document.QueryResultData;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * query response
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class QueryResponse implements QueryResultData {
	private Integer took = null;
	@JsonProperty("timed_out")
	private boolean timeout = false;
	@JsonProperty("_shards")
	private RestResponseShards shards = null;
	private QueryHits hits = null;

	/**
	 * @return the took
	 */
	public Integer getTook() {
		return took;
	}

	/**
	 * @param took
	 *            the took to set
	 */
	public void setTook(Integer took) {
		this.took = took;
	}

	/**
	 * @return the timeout
	 */
	public boolean isTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the hits
	 */
	public QueryHits getHits() {
		return hits;
	}

	/**
	 * @param hits
	 *            the hits to set
	 */
	public void setHits(QueryHits hits) {
		this.hits = hits;
	}

	/**
	 * always returns true if response converted successfully
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandResultData#isSuccessful()
	 */
	@Override
	public boolean isSuccessful() {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.QueryResultData#getResultCount()
	 */
	@Override
	public int getResultCount() {
		if (this.getHits() == null) {
			return 0;
		} else {
			return this.getHits().getTotal();
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.QueryResultData#getResultItems()
	 */
	@Override
	public List<QueryResultItem> getResultItems() {
		if (this.getHits() == null || this.getHits().getItems() == null) {
			return Collections.emptyList();
		} else {
			return Lists.transform(this.getHits().getItems(), new Function<QueryHitItem, QueryResultItem>() {
				/**
				 * (non-Javadoc)
				 * 
				 * @see com.google.common.base.Function#apply(java.lang.Object)
				 */
				@Override
				public QueryResultItem apply(QueryHitItem input) {
					return input;
				}
			});
		}
	}

	/**
	 * query hints
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class QueryHits {
		private Integer total = null;
		@JsonProperty("max_score")
		private BigDecimal maxScore = null;
		private List<QueryHitItem> items = null;

		/**
		 * @return the total
		 */
		public Integer getTotal() {
			return total;
		}

		/**
		 * @param total
		 *            the total to set
		 */
		public void setTotal(Integer total) {
			this.total = total;
		}

		/**
		 * @return the maxScore
		 */
		public BigDecimal getMaxScore() {
			return maxScore;
		}

		/**
		 * @param maxScore
		 *            the maxScore to set
		 */
		public void setMaxScore(BigDecimal maxScore) {
			this.maxScore = maxScore;
		}

		/**
		 * @return the items
		 */
		public List<QueryHitItem> getItems() {
			return items;
		}

		/**
		 * @param items
		 *            the items to set
		 */
		@JsonProperty("hits")
		@JsonDeserialize(contentAs = QueryHitItemReceiver.class)
		public void setItems(List<QueryHitItem> items) {
			this.items = items;
		}
	}

	public static class QueryHitItem implements QueryResultItem {
		private String index = null;
		private String type = null;
		private String id = null;
		private BigDecimal score = null;
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
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.command.document.QueryResultData.QueryResultItem#getScore()
		 */
		@Override
		public BigDecimal getScore() {
			return score;
		}

		/**
		 * @param score
		 *            the score to set
		 */
		public void setScore(BigDecimal score) {
			this.score = score;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.command.document.QueryResultData.QueryResultItem#getDocument()
		 */
		@SuppressWarnings("unchecked")
		@Override
		public <T> T getDocument() {
			return (T) document;
		}

		/**
		 * @param document
		 *            the document to set
		 */
		public void setDocument(Object document) {
			this.document = document;
		}
	}

	/**
	 * query hint item receiver
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class QueryHitItemReceiver extends QueryHitItem {
		private QueryHitItem item = new QueryHitItem();
		private JsonNode jsonNode = null;

		/**
		 * unwrap
		 * 
		 * @return real item
		 */
		public QueryHitItem unwrap() {
			return this.item;
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
		 * @see com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHitItem#getIndex()
		 */
		@Override
		public String getIndex() {
			return item.getIndex();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHitItem#setIndex(java.lang.String)
		 */
		@Override
		@JsonProperty("_index")
		public void setIndex(String index) {
			item.setIndex(index);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHitItem#getType()
		 */
		@Override
		public String getType() {
			return item.getType();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHitItem#setType(java.lang.String)
		 */
		@Override
		@JsonProperty("_type")
		public void setType(String type) {
			item.setType(type);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHitItem#getId()
		 */
		@Override
		public String getId() {
			return item.getId();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHitItem#setId(java.lang.String)
		 */
		@Override
		@JsonProperty("_id")
		public void setId(String id) {
			item.setId(id);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHitItem#getScore()
		 */
		@Override
		public BigDecimal getScore() {
			return item.getScore();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHitItem#setScore(java.math.BigDecimal)
		 */
		@Override
		@JsonProperty("_score")
		public void setScore(BigDecimal score) {
			item.setScore(score);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHitItem#getDocument()
		 */
		@Override
		public <T> T getDocument() {
			return item.getDocument();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.document.QueryResponse.QueryHitItem#setDocument(java.lang.Object)
		 */
		@Override
		public void setDocument(Object document) {
			item.setDocument(document);
		}

		/**
		 * transform document by givenn mapper and document type
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
