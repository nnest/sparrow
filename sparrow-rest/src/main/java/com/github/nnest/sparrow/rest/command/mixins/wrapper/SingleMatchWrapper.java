package com.github.nnest.sparrow.rest.command.mixins.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.fulltext.AbstractSingleMatch;

/**
 * single match wrapper, see {@linkplain AbstractSingleMatch}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@SuppressWarnings("rawtypes")
public class SingleMatchWrapper extends AbstractChainQueryExampleWrapper {
	public SingleMatchWrapper() {
		super();
	}

	public SingleMatchWrapper(ChainQueryExampleWrapper next) {
		super(next);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.QueryExampleWrapper#accept(com.github.nnest.sparrow.command.document.query.Example)
	 */
	@Override
	public boolean accept(Example example) {
		return example instanceof AbstractSingleMatch;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.AbstractQueryExampleWrapper#doWrap(com.github.nnest.sparrow.command.document.query.Example)
	 */
	@Override
	protected Example doWrap(Example example) {
		return new WrappedSingleMatch((AbstractSingleMatch) example);
	}

	/**
	 * wrapped single match, only for {@linkplain AbstractSingleMatch}
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class WrappedSingleMatch implements Example {
		private AbstractSingleMatch match = null;

		private WrappedSingleMatch(AbstractSingleMatch match) {
			this.match = match;
		}

		/**
		 * @return the match
		 */
		public AbstractSingleMatch getMatch() {
			return match;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
		 */
		@Override
		@JsonIgnore
		public ExampleType getExampleType() {
			return this.getMatch().getExampleType();
		}
	}
}