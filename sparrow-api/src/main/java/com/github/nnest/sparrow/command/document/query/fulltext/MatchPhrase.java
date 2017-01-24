/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fulltext;

import java.math.BigDecimal;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.ExampleType;

/**
 * match phrase
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MatchPhrase<T extends MatchPhrase<T>> extends AbstractSingleMatch<T> {
	public MatchPhrase(String exampleText) {
		super(exampleText);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.MATCH_PHRASE;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.fulltext.AbstractSingleMatch#withFieldName(java.lang.String)
	 */
	@Override
	public T withFieldName(String fieldName) {
		return super.withFieldName(fieldName);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.fulltext.AbstractMatch#withSlop(java.lang.Integer)
	 */
	@Override
	public T withSlop(Integer slop) {
		return super.withSlop(slop);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.fulltext.AbstractFullTextQuery#withExampleText(java.lang.String)
	 */
	@Override
	public T withExampleText(String exampleText) {
		return super.withExampleText(exampleText);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.fulltext.AbstractFullTextQuery#withBoost(java.math.BigDecimal)
	 */
	@Override
	public T withBoost(BigDecimal boost) {
		return super.withBoost(boost);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.fulltext.AbstractFullTextQuery#withAnalyzerName(java.lang.String)
	 */
	@Override
	public T withAnalyzerName(String analyzerName) {
		return super.withAnalyzerName(analyzerName);
	}
}
