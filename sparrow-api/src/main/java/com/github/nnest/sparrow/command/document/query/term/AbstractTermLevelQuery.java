/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.term;

import java.math.BigDecimal;

import com.google.common.base.Strings;

/**
 * abstract term level query
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractTermLevelQuery<T extends AbstractTermLevelQuery<T>> implements TermLevelQuery<T> {
	private String fieldName = null;
	private BigDecimal boost = null;

	public AbstractTermLevelQuery(String fieldName) {
		this.withFieldName(fieldName);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.term.TermLevelQuery#getFieldName()
	 */
	@Override
	public String getFieldName() {
		return this.fieldName;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.term.TermLevelQuery#withFieldName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public T withFieldName(String fieldName) {
		assert Strings.nullToEmpty(fieldName).trim().length() != 0 : "Field name cannot be null or blank.";

		this.fieldName = fieldName;
		return (T) this;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.term.TermLevelQuery#getBoost()
	 */
	@Override
	public BigDecimal getBoost() {
		return boost;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.term.TermLevelQuery#withBoost(java.math.BigDecimal)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T withBoost(BigDecimal boost) {
		assert boost != null : "Boost cannot be null.";
		double v = boost.doubleValue();
		assert v > 0 : "Boost must be positive.";

		this.boost = boost;
		return (T) this;
	}
}
