/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.term.AbstractTermLevelQuery;
import com.github.nnest.sparrow.command.document.query.term.TermLevelQueryExist;
import com.github.nnest.sparrow.command.document.query.term.Term;
import com.github.nnest.sparrow.command.document.query.term.Terms;
import com.github.nnest.sparrow.command.document.query.term.TermsLookupExternal;

/**
 * term level query wrapper, see {@linkplain AbstractTermLevelQuery}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class TermLevelQueryWrapper extends AbstractChainQueryExampleWrapper {
	public TermLevelQueryWrapper() {
		super();
	}

	public TermLevelQueryWrapper(ChainQueryExampleWrapper nextWrapper) {
		super(nextWrapper);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.QueryExampleWrapper#accept(com.github.nnest.sparrow.command.document.query.Example)
	 */
	@Override
	public boolean accept(Example example) {
		return example instanceof AbstractTermLevelQuery //
				&& !Terms.class.isAssignableFrom(example.getClass()) //
				&& !TermsLookupExternal.class.isAssignableFrom(example.getClass()) //
				&& !TermLevelQueryExist.class.isAssignableFrom(example.getClass());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.AbstractQueryExampleWrapper#doWrap(com.github.nnest.sparrow.command.document.query.Example)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected Example doWrap(Example example) {
		return new WrappedTermLevelQuery((AbstractTermLevelQuery) example);
	}

	/**
	 * wrapped term, only for {@linkplain Term}
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	@SuppressWarnings("rawtypes")
	public static class WrappedTermLevelQuery implements Example {
		private AbstractTermLevelQuery term = null;

		private WrappedTermLevelQuery(AbstractTermLevelQuery term) {
			this.term = term;
		}

		/**
		 * @return the term
		 */
		public AbstractTermLevelQuery getTerm() {
			return term;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
		 */
		@Override
		@JsonIgnore
		public ExampleType getExampleType() {
			return this.getTerm().getExampleType();
		}
	}
}
