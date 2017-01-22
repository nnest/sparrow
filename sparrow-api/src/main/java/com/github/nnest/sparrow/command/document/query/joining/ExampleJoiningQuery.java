/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.joining;

import com.github.nnest.sparrow.command.document.query.Example;

/**
 * joining query with example
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ExampleJoiningQuery<T extends ExampleJoiningQuery<T>> extends JoiningQuery<T> {
	/**
	 * get example
	 * 
	 * @return example
	 */
	Example getExample();

	/**
	 * with example
	 * 
	 * @param example
	 *            example
	 * @return this
	 */
	T with(Example example);
}
