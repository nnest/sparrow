/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.shouldmatch;

/**
 * numeric minimum should match, contains a value of integer
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class NumericMinimumShouldMatch implements ValuedMinimumShouldMatch {
	private Integer value = null;

	public NumericMinimumShouldMatch(Integer value) {
		this.setValue(value);
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	protected void setValue(Integer value) {
		assert value != null && value != 0 : "Value cannot be null or zero.";
		this.value = value;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.shouldmatch.MinimumShouldMatch#asString()
	 */
	@Override
	public String asString() {
		return value.toString();
	}
}
