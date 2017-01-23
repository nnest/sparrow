/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

import java.math.BigDecimal;

/**
 * match all example
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MatchAll implements Example {
	private BigDecimal boost = null;

	/**
	 * @return the boost
	 */
	public BigDecimal getBoost() {
		return boost;
	}

	/**
	 * @param boost
	 *            the boost to set
	 */
	public void setBoost(BigDecimal boost) {
		this.boost = boost;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.MATCH_ALL;
	}
}
