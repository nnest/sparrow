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
public abstract class AbstractMatch<T extends AbstractMatch<T>> implements Example {
	private String fieldName = Example.ALL_FIELDS;
	private String exampleText = null;
	private BigDecimal boost = null;

	public AbstractMatch(String exampleText) {
		this.setExampleText(exampleText);
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 * @return this;
	 */
	@SuppressWarnings("unchecked")
	public T withFieldName(String fieldName) {
		assert Strings.nullToEmpty(fieldName).trim().length() != 0 : "Field name cannot be null or blank.";

		this.fieldName = fieldName;
		return (T) this;
	}

	/**
	 * @return the exampleText
	 */
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
	 * @return the boost
	 */
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
}
