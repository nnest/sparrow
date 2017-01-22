/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.score;

import java.math.BigDecimal;

/**
 * weight function
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class WeightFunction implements ScoreFunction {
	private BigDecimal value = null;

	public WeightFunction(BigDecimal value) {
		this.withValue(value);
	}

	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 * @return this
	 */
	public WeightFunction withValue(BigDecimal value) {
		assert value != null : "Value cannot be null.";

		this.value = value;
		return this;
	}
}
