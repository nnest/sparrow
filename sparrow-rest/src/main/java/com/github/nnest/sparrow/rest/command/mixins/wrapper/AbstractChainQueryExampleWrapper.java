/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins.wrapper;

import com.github.nnest.sparrow.command.document.query.Example;

/**
 * abstract chained query example wrapper.<br>
 * if the object is not accepted by this, use {@linkplain #getNext()} to try
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractChainQueryExampleWrapper extends AbstractQueryExampleWrapper
		implements ChainQueryExampleWrapper {
	private ChainQueryExampleWrapper next = null;

	public AbstractChainQueryExampleWrapper() {
	}

	public AbstractChainQueryExampleWrapper(ChainQueryExampleWrapper nextWrapper) {
		assert nextWrapper != null : "Next wrapper cannot be null.";

		this.setNext(nextWrapper);
	}

	/**
	 * if {@linkplain #accept(Example)} returns true, call
	 * {@linkplain #doWrap(Example)} and return.<br>
	 * if {@linkplain #accept(Example)} returns false, check there is next
	 * wrapper existing.<br>
	 * if existing, call {@linkplain #wrap(Example)} of next wrapper, otherwise
	 * return given example directly.
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.QueryExampleWrapper#wrap(com.github.nnest.sparrow.command.document.query.Example)
	 */
	@Override
	public Example wrap(Example example) {
		if (this.accept(example)) {
			return this.doWrap(example);
		} else if (this.hasNext()) {
			return this.getNext().wrap(example);
		} else {
			return example;
		}
	}

	/**
	 * wrap example. returns {@linkplain Optional#empty()} if not wrapped.
	 * 
	 * @param example
	 *            example
	 * @return wrapped example or empty
	 */
	// protected abstract Optional<Example> doWrap(T example);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.ChainQueryExampleWrapper#getNext()
	 */
	@Override
	public ChainQueryExampleWrapper getNext() {
		return this.next;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.ChainQueryExampleWrapper#setNext(com.github.nnest.sparrow.rest.command.mixins.wrapper.ChainQueryExampleWrapper)
	 */
	@Override
	public void setNext(ChainQueryExampleWrapper nextWrapper) {
		this.next = nextWrapper;
	}

	/**
	 * has next wrapper
	 * 
	 * @return has
	 */
	protected boolean hasNext() {
		return this.getNext() != null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.ChainQueryExampleWrapper#twist(com.github.nnest.sparrow.rest.command.mixins.wrapper.ChainQueryExampleWrapper)
	 */
	@Override
	public void twist(ChainQueryExampleWrapper wrapper) {
		ChainQueryExampleWrapper originalNextWrapper = this.getNext();
		// let new as new next wrapper
		this.setNext(wrapper);
		// find the end of new next wrapper
		ChainQueryExampleWrapper endOfNewWrapper = wrapper;
		ChainQueryExampleWrapper nextOfNewWrapper = wrapper.getNext();
		while (nextOfNewWrapper != null) {
			endOfNewWrapper = nextOfNewWrapper;
			nextOfNewWrapper = nextOfNewWrapper.getNext();
		}
		endOfNewWrapper.setNext(originalNextWrapper);
	}
}
