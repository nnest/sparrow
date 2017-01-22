/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.shouldmatch;

/**
 * percentage minimum should match
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class PercentageMinimumShouldMatch implements ValuedMinimumShouldMatch {
	private Integer value = null;

	public PercentageMinimumShouldMatch(Integer value) {
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
		assert value != null && value != 0 && value <= 100
				&& value >= -100 : "Value cannot be null or zero, and must between [100, -100]";
		this.value = value;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch#asString()
	 */
	@Override
	public String asString() {
		return value.toString() + "%";
	}
}
