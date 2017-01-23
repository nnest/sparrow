/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.compound;

import java.math.BigDecimal;
import java.util.List;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.google.common.collect.Lists;

/**
 * disjunction max query
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DisMax extends AbstractCompoundQuery<DisMax> {
	private List<Example> examples = null;
	private BigDecimal tieBreaker = null;

	public DisMax(List<Example> examples) {
		this.withExamples(examples);
	}

	public DisMax(Example... examples) {
		this.withExamples(examples);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.DIS_MAX;
	}

	/**
	 * @return the examples
	 */
	public List<Example> getExamples() {
		return examples;
	}

	/**
	 * @param examples
	 *            the examples to set
	 * @return this
	 */
	public DisMax withExamples(List<Example> examples) {
		assert examples != null && examples.size() > 0 : "Examples cannot be null or empty.";

		this.examples = examples;
		return this;
	}

	/**
	 * @param examples
	 *            the examples to set
	 * @return this
	 */
	public DisMax withExamples(Example... examples) {
		assert examples != null && examples.length > 0 : "Examples cannot be null or empty.";

		this.examples = Lists.newArrayList(examples);
		return this;
	}

	/**
	 * @return the tieBreaker
	 */
	public BigDecimal getTieBreaker() {
		return tieBreaker;
	}

	/**
	 * @param tieBreaker
	 *            the tieBreaker to set
	 * @return this
	 */
	public DisMax withTieBreaker(BigDecimal tieBreaker) {
		assert tieBreaker != null : "Tie breaker cannot be null.";
		double v = tieBreaker.doubleValue();
		assert v >= 0 && v <= 1 : "Tie breaker must in range of [0, 1]";

		this.tieBreaker = tieBreaker;
		return this;
	}
}
