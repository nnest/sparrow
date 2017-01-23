/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * query hints
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryHits {
	private Integer total = null;
	@JsonProperty("max_score")
	private BigDecimal maxScore = null;

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
}
