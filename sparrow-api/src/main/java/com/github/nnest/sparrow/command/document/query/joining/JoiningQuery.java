/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.joining;

import com.github.nnest.sparrow.command.document.query.Example;

/**
 * Joining query.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface JoiningQuery<T extends JoiningQuery<T>> extends Example {
	/**
	 * is ignore unmapped or not
	 * 
	 * @return boolean value
	 */
	Boolean getIgnoreUnmapped();

	/**
	 * with ignore unmapped
	 * 
	 * @param ignoreUnmapped
	 *            ignore unmapped
	 * @return this
	 */
	T withIgnoreUnmapped(Boolean ignoreUnmapped);
}
