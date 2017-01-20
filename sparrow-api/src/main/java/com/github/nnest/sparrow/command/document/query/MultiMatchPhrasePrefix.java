/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

/**
 * multiple match phrase prefix
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MultiMatchPhrasePrefix extends AbstractMultiMatchText<MultiMatchPhrasePrefix> {
	public MultiMatchPhrasePrefix(String exampleText) {
		super(exampleText);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.MatchText#getType()
	 */
	@Override
	public MatchType getType() {
		return MatchType.MULTI_PHRASE_PREFIX;
	}
}
