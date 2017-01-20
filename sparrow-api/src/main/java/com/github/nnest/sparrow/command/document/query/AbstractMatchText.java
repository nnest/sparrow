/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

import java.math.BigDecimal;

import com.google.common.base.Strings;

/**
 * abstract match
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractMatchText<T extends AbstractMatchText<T>> implements MatchText {
	private String exampleText = null;
	private BigDecimal boost = null;
	private String analyzerName = null;
	private Integer slop = null;

	public AbstractMatchText(String exampleText) {
		this.setExampleText(exampleText);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.MatchText#getExampleText()
	 */
	@Override
	public String getExampleText() {
		return exampleText;
	}

	/**
	 * @param exampleText
	 *            the exampleText to set
	 */
	protected void setExampleText(String exampleText) {
		assert Strings.nullToEmpty(exampleText).length() != 0 : "Example text cannot be null or empty.";

		this.exampleText = exampleText;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.MatchText#getBoost()
	 */
	@Override
	public BigDecimal getBoost() {
		return boost;
	}

	/**
	 * @param boost
	 *            the boost to set
	 */
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
	 * @see com.github.nnest.sparrow.command.document.query.MatchText#getAnalyzerName()
	 */
	@Override
	public String getAnalyzerName() {
		return analyzerName;
	}

	/**
	 * @param analyzerName
	 *            the analyzerName to set
	 * @return this;
	 */
	@SuppressWarnings("unchecked")
	public T withAnalyzerName(String analyzerName) {
		assert Strings.nullToEmpty(analyzerName).trim().length() != 0 : "Analyzer name cannot be null or blank.";

		this.analyzerName = analyzerName;
		return (T) this;
	}

	/**
	 * @return the slop
	 */
	public Integer getSlop() {
		return slop;
	}

	/**
	 * @param slop
	 *            the slop to set
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T withSlop(Integer slop) {
		assert slop != null && slop > 0 : "Slop cannot be null, and must be positive.";
		
		this.slop = slop;
		return (T) this;
	}
}
