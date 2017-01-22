/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.compound;

import com.github.nnest.sparrow.command.document.query.Example;

/**
 * Constant score query
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ConstantScore extends AbstractCompoundQuery<ConstantScore> {
	private Example example = null;

	public ConstantScore(Example example) {
		this.with(example);
	}

	/**
	 * @return the example
	 */
	public Example getExample() {
		return example;
	}

	/**
	 * @param example
	 *            the example to set
	 * @return this
	 */
	protected ConstantScore with(Example example) {
		assert example != null : "Example cannot be null.";

		this.example = example;
		return this;
	}
}
