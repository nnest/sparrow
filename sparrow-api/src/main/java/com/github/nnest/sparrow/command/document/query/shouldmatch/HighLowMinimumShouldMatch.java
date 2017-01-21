/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.shouldmatch;

/**
 * high low minimum should match
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class HighLowMinimumShouldMatch implements MinimumShouldMatch {
	private MinimumShouldMatch high = null;
	private MinimumShouldMatch low = null;

	/**
	 * @return the high
	 */
	public MinimumShouldMatch getHigh() {
		return high;
	}

	/**
	 * @param high
	 *            the high to set
	 * @return this
	 */
	public HighLowMinimumShouldMatch withHigh(MinimumShouldMatch high) {
		assert high != null : "High value cannot be null.";

		this.high = high;
		return this;
	}

	/**
	 * @return the low
	 */
	public MinimumShouldMatch getLow() {
		return low;
	}

	/**
	 * @param low
	 *            the low to set
	 * @return this
	 */
	public HighLowMinimumShouldMatch withLow(MinimumShouldMatch low) {
		assert low != null : "Low value cannot be null.";

		this.low = low;
		return this;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.shouldmatch.MinimumShouldMatch#asString()
	 */
	@Override
	public String asString() {
		// TODO Auto-generated method stub
		return null;
	}

}
