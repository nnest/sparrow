/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nnest.sparrow.command.document.QueryResultData;

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
		return this.getHits().getTotal();
	}
}
