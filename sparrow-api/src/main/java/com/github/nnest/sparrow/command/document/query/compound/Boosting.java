/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.compound;

import java.math.BigDecimal;
import java.util.List;

import com.github.nnest.sparrow.command.document.query.Example;

/**
 * boosting query
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Boosting extends AbstractCompoundQuery<Boosting> {
	private List<Example> positive = null;
	private List<Example> negative = null;
	private BigDecimal negativeBoost = null;

	/**
	 * @return the positive
	 */
	public List<Example> getPositive() {
		return positive;
	}

	/**
	 * @param positive
	 *            the positive to set
	 * @return this
	 */
	public Boosting withPositive(List<Example> positive) {
		assert positive != null && positive.size() > 0 : "Positive examples cannot be null or empty.";

		this.positive = positive;
		return this;
	}

	/**
	 * @return the negative
	 */
	public List<Example> getNegative() {
		return negative;
	}

	/**
	 * @param negative
	 *            the negative to set
	 * @return this
	 */
	public Boosting withNegative(List<Example> negative) {
		assert negative != null && negative.size() > 0 : "Negative examples cannot be null or empty.";

		this.negative = negative;
		return this;
	}

	/**
	 * @return the negativeBoost
	 */
	public BigDecimal getNegativeBoost() {
		return negativeBoost;
	}

	/**
	 * @param negativeBoost
	 *            the negativeBoost to set
	 * @return this
	 */
	public Boosting withNegativeBoost(BigDecimal negativeBoost) {
		assert negativeBoost != null
				&& negativeBoost.doubleValue() > 0 : "Negative boost cannot be null, and must be positive.";

		this.negativeBoost = negativeBoost;
		return this;
	}
}
