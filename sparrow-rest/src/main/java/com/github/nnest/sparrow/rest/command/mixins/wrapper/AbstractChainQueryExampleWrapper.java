/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins.wrapper;

import java.util.Optional;

import com.github.nnest.sparrow.command.document.query.Example;

/**
 * abstract chained query example wrapper.<br>
 * if the object is not accepted by this, use {@linkplain #getNext()} to try
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractChainQueryExampleWrapper<T extends Example> implements ChainQueryExampleWrapper<T> {
	private ChainQueryExampleWrapper next = null;

	public AbstractChainQueryExampleWrapper() {
	}

	public AbstractChainQueryExampleWrapper(ChainQueryExampleWrapper nextWrapper) {
		assert nextWrapper != null : "Next wrapper cannot be null.";

		this.setNext(nextWrapper);
	}

	/**
	 * call {@linkplain #doWrap(Example)} for try to wrap example by itself. is
	 * the returned {@linkplain Optional} is present, return wrapped example
	 * directly. otherwise call next to wrap example if next exists. if cannot
	 * wrap, return example itself.
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.QueryExampleWrapper#wrap(com.github.nnest.sparrow.command.document.query.Example)
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public Example wrap(T example) {
		Optional<Example> wrapped = this.doWrap(example);
		if (wrapped.isPresent()) {
			return wrapped.get();
		} else {
			QueryExampleWrapper next = this.getNext();
			if (next != null) {
				return next.wrap(example);
			} else {
				return example;
			}
		}
	}

	/**
	 * wrap example. returns {@linkplain Optional#empty()} if not wrapped.
	 * 
	 * @param example
	 *            example
	 * @return wrapped example or empty
	 */
	protected abstract Optional<Example> doWrap(T example);

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
