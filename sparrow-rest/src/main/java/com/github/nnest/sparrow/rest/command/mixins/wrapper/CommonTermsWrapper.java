/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.fulltext.AbstractSingleMatch;
import com.github.nnest.sparrow.command.document.query.fulltext.CommonTerms;

/**
 * Common terms wrapper, see {@linkplain CommonTerms}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class CommonTermsWrapper extends AbstractChainQueryExampleWrapper {
	public CommonTermsWrapper() {
		super();
	}

	public CommonTermsWrapper(ChainQueryExampleWrapper nextWrapper) {
		super(nextWrapper);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.QueryExampleWrapper#accept(com.github.nnest.sparrow.command.document.query.Example)
	 */
	@Override
	public boolean accept(Example example) {
		return example instanceof CommonTerms;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.mixins.wrapper.AbstractQueryExampleWrapper#doWrap(com.github.nnest.sparrow.command.document.query.Example)
	 */
	@Override
	protected Example doWrap(Example example) {
		return new WrappedCommonTerms((CommonTerms) example);
	}

	/**
	 * wrapped single match, only for {@linkplain AbstractSingleMatch}
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class WrappedCommonTerms implements Example {
		private CommonTerms terms = null;

		private WrappedCommonTerms(CommonTerms terms) {
			this.terms = terms;
		}

		/**
		 * @return the match
		 */
		public CommonTerms getTerms() {
			return terms;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
		 */
		@Override
		@JsonIgnore
		public ExampleType getExampleType() {
			return this.getTerms().getExampleType();
		}
	}
}
