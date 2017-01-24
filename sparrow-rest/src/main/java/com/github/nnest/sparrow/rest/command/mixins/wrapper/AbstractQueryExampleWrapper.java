/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins.wrapper;

import com.github.nnest.sparrow.command.document.query.Example;

/**
 * abstract query example wrapper
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractQueryExampleWrapper implements QueryExampleWrapper {
	/**
	 * if {@linkplain #accept(Example)} returns true, call
	 * {@linkplain #doWrap(Example)} and return. otherwise return given example
	 * directly.
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.QueryExampleWrapper#wrap(com.github.nnest.sparrow.command.document.query.Example)
	 */
	@Override
	public Example wrap(Example example) {
		if (this.accept(example)) {
			return this.doWrap(example);
		}
		return example;
	}

	/**
	 * do wrap
	 * 
	 * @param example
	 *            example
	 * @return wrapped example
	 */
	protected abstract Example doWrap(Example example);
}
