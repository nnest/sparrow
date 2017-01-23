/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.compound;

import java.math.BigDecimal;

/**
 * abstract compound query
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractCompoundQuery<T extends AbstractCompoundQuery<T>> implements CompoundQuery<T> {
	private BigDecimal boost = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.compound.CompoundQuery#getBoost()
	 */
	@Override
	public BigDecimal getBoost() {
		return boost;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.compound.CompoundQuery#withBoost(java.math.BigDecimal)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T withBoost(BigDecimal boost) {
		assert boost != null && boost.doubleValue() > 0 : "Boost cannot be null, and must be positive.";

		this.boost = boost;
		return (T) this;
	}
}
