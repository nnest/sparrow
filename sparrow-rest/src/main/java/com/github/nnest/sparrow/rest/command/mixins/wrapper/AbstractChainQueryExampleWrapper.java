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
	private ChainQueryExampleWrapper previous = null;

	public AbstractChainQueryExampleWrapper() {
	}

	public AbstractChainQueryExampleWrapper(ChainQueryExampleWrapper nextWrapper) {
		assert nextWrapper != null : "Next wrapper cannot be null.";

		this.next(nextWrapper);
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
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.ChainQueryExampleWrapper#next(com.github.nnest.sparrow.rest.command.mixins.wrapper.ChainQueryExampleWrapper)
	 */
	@Override
	public ChainQueryExampleWrapper next(ChainQueryExampleWrapper nextWrapper) {
		this.next = nextWrapper;
		if (nextWrapper.getPrevious() != this) {
			nextWrapper.previous(this);
		}
		return this.next;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.ChainQueryExampleWrapper#getPrevious()
	 */
	@Override
	public ChainQueryExampleWrapper getPrevious() {
		return this.previous;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.ChainQueryExampleWrapper#previous(com.github.nnest.sparrow.rest.command.mixins.wrapper.ChainQueryExampleWrapper)
	 */
	@Override
	public ChainQueryExampleWrapper previous(ChainQueryExampleWrapper previousWrapper) {
		this.previous = previousWrapper;
		if (previousWrapper.getNext() != this) {
			previousWrapper.next(this);
		}
		return this.previous;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.ChainQueryExampleWrapper#first()
	 */
	@Override
	public ChainQueryExampleWrapper first() {
		return this.previous != null ? previous.first() : this;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.ChainQueryExampleWrapper#last()
	 */
	@Override
	public ChainQueryExampleWrapper last() {
		return this.next != null ? next.last() : this;
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
	 * has previous wrapper
	 * 
	 * @return has
	 */
	protected boolean hasPrevious() {
		return this.getPrevious() != null;
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
		this.next(wrapper);
		// find the end of new next wrapper
		wrapper.last().next(originalNextWrapper);
	}
}
