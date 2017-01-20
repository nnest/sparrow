/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

/**
 * match phrase prefix
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MatchPhrasePrefix extends MatchPhrase<MatchPhrasePrefix> {
	private int maxExpansions = 50;

	public MatchPhrasePrefix(String exampleText) {
		super(exampleText);
	}

	/**
	 * @return the maxExpansions
	 */
	public int getMaxExpansions() {
		return maxExpansions;
	}

	/**
	 * @param maxExpansions
	 *            the maxExpansions to set
	 * @return this
	 */
	public MatchPhrasePrefix withMaxExpansions(int maxExpansions) {
		assert maxExpansions >= 0 : "Max expansions must be zero or positive.";

		this.maxExpansions = maxExpansions;
		return this;
	}
}
