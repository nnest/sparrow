/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.compound.ConstantScore;

/**
 * Constant score wrapper, see {@linkplain ConstantScore}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ConstantScoreWrapper extends AbstractChainQueryExampleWrapper {
	public ConstantScoreWrapper() {
		super();
	}

	public ConstantScoreWrapper(ChainQueryExampleWrapper nextWrapper) {
		super(nextWrapper);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.QueryExampleWrapper#accept(com.github.nnest.sparrow.command.document.query.Example)
	 */
	@Override
	public boolean accept(Example example) {
		return example instanceof ConstantScore;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.AbstractQueryExampleWrapper#doWrap(com.github.nnest.sparrow.command.document.query.Example)
	 */
	@Override
	protected Example doWrap(Example example) {
		return new WrappedConstantScore((ConstantScore) example);
	}

	/**
	 * wrapped constant score, only for {@linkplain ConstantScore}
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class WrappedConstantScore implements Example {
		private ConstantScore query = null;

		private WrappedConstantScore(ConstantScore terms) {
			this.query = terms;
		}

		/**
		 * @return the terms
		 */
		@JsonUnwrapped
		public ConstantScore getCompoundQuery() {
			return query;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
		 */
		@Override
		@JsonIgnore
		public ExampleType getExampleType() {
			return this.getCompoundQuery().getExampleType();
		}
	}
}
