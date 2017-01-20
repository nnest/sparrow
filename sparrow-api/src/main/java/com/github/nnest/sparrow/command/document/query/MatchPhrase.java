/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

import com.google.common.base.Strings;

/**
 * match phrase
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MatchPhrase<T extends MatchPhrase<T>> extends AbstractMatch<T> {
	private String analyzerName = null;

	public MatchPhrase(String exampleText) {
		super(exampleText);
	}

	/**
	 * @return the analyzerName
	 */
	public String getAnalyzerName() {
		return analyzerName;
	}

	/**
	 * @param analyzerName
	 *            the analyzerName to set
	 * @return this;
	 */
	@SuppressWarnings("unchecked")
	public T withAnalyzerName(String analyzerName) {
		assert Strings.nullToEmpty(analyzerName).trim().length() != 0 : "Analyzer name cannot be null or blank.";

		this.analyzerName = analyzerName;
		return (T) this;
	}
}
