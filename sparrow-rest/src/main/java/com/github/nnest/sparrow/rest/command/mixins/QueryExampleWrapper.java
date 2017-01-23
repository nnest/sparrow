package com.github.nnest.sparrow.rest.command.mixins;

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
public interface QueryExampleWrapper<T extends Example> {
	/**
	 * accept example or not
	 * 
	 * @param example
	 *            example
	 * @return example
	 */
	boolean accept(T example);

	/**
	 * wrap example
	 * 
	 * @param example
	 *            original example
	 * @return wrapped example
	 */
	Example wrap(T example);
}