/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fulltext;

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
}
