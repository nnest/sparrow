/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.shouldmatch;

/**
 * combination minimum should match
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class CombinationMinimumShouldMatch implements MinimumShouldMatch {
	private Integer clauseCount = null;
	private ValuedMinimumShouldMatch value = null;

	public CombinationMinimumShouldMatch(Integer clauseCount, ValuedMinimumShouldMatch value) {
		this.setClauseCount(clauseCount);
		this.setValue(value);
	}

	/**
	 * @return the clauseCount
	 */
	public Integer getClauseCount() {
		return clauseCount;
	}

	/**
	 * @param clauseCount
	 *            the clauseCount to set
	 */
	protected void setClauseCount(Integer clauseCount) {
		assert clauseCount != null && clauseCount > 0 : "Clause count cannot be null and must be positive.";
		this.clauseCount = clauseCount;
	}

	/**
	 * @return the value
	 */
	public ValuedMinimumShouldMatch getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	protected void setValue(ValuedMinimumShouldMatch value) {
		assert value != null : "Value cannot be null.";
		this.value = value;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.shouldmatch.MinimumShouldMatch#asString()
	 */
	@Override
	public String asString() {
		return this.getClauseCount() + "<" + this.getValue().asString();
	}
}
