/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

/**
 * match given example text and other parameters
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface MatchText<T extends MatchText<T>> extends FullTextQuery<T> {
	/**
	 * get slop
	 * 
	 * @return slop
	 */
	Integer getSlop();

	/**
	 * with slop
	 * 
	 * @param slop
	 * @return
	 */
	T withSlop(Integer slop);

	/**
	 * get match type
	 * 
	 * @return type
	 */
	MatchType getType();
}
