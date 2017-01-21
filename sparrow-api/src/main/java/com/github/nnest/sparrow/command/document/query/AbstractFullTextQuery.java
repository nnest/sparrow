/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

import java.math.BigDecimal;

import com.google.common.base.Strings;

/**
 * abstract full text query
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractFullTextQuery<T extends AbstractFullTextQuery<T>> implements FullTextQuery<T> {
	private String exampleText = null;
	private BigDecimal boost = null;
	private String analyzerName = null;

	public AbstractFullTextQuery(String exampleText) {
		this.withExampleText(exampleText);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.FullTextQuery#getExampleText()
	 */
	@Override
	public String getExampleText() {
		return exampleText;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.FullTextQuery#withExampleText(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T withExampleText(String exampleText) {
		assert Strings.nullToEmpty(exampleText).length() != 0 : "Example text cannot be null or empty.";

		this.exampleText = exampleText;
		return (T) this;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.FullTextQuery#getBoost()
	 */
	@Override
	public BigDecimal getBoost() {
		return boost;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.FullTextQuery#withBoost(java.math.BigDecimal)
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

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.FullTextQuery#getAnalyzerName()
	 */
	@Override
	public String getAnalyzerName() {
		return analyzerName;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.FullTextQuery#withAnalyzerName(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T withAnalyzerName(String analyzerName) {
		assert Strings.nullToEmpty(analyzerName).trim().length() != 0 : "Analyzer name cannot be null or blank.";

		this.analyzerName = analyzerName;
		return (T) this;
	}
}
