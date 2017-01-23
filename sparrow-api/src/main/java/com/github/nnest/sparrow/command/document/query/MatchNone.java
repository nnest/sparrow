/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

/**
 * match none example
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MatchNone implements Example {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.MATCH_NONE;
	}
}
