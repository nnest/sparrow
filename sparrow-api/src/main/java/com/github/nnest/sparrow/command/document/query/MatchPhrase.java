/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

/**
 * match phrase
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MatchPhrase<T extends MatchPhrase<T>> extends AbstractSingleMatchText<T> {
	public MatchPhrase(String exampleText) {
		super(exampleText);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.AbstractMatchText#getType()
	 */
	@Override
	public MatchType getType() {
		return MatchType.SINGLE_MATCH_PHRASE;
	}
}
