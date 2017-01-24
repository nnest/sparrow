package com.github.nnest.sparrow.rest.command.mixins.wrapper;

import com.github.nnest.sparrow.command.document.query.Example;

/**
 * query example wrapper.<br>
 * Example wrapper is wrapping the original example to be serializable by object
 * mapper.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface QueryExampleWrapper {
	/**
	 * wrap example
	 * 
	 * @param example
	 *            original example
	 * @return wrapped example
	 */
	Example wrap(Example example);

	/**
	 * check given example is accepted by this
	 * 
	 * @param example
	 *            example
	 * @return acceptable
	 */
	boolean accept(Example example);
}