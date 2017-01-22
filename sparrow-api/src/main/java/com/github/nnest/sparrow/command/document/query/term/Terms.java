/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.term;

import java.util.Set;

import com.google.common.collect.Sets;

/**
 * terms
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Terms extends AbstractTermLevelQuery<Terms> {
	private Set<String> exampleTexts = null;

	public Terms(String fieldName) {
		super(fieldName);
	}

	/**
	 * @return the exampleTexts
	 */
	public Set<String> getExampleTexts() {
		return exampleTexts;
	}

	/**
	 * @param exampleTexts
	 *            the exampleTexts to set
	 * @return this
	 */
	public Terms withExampleTexts(Set<String> exampleTexts) {
		assert exampleTexts != null && exampleTexts.size() > 0 : "Example texts cannot be null or empty.";

		this.exampleTexts = exampleTexts;
		return this;
	}

	/**
	 * @param exampleTexts
	 *            the exampleTexts to set
	 * @return this
	 */
	public Terms withExampleTexts(String... exampleTexts) {
		assert exampleTexts != null && exampleTexts.length > 0 : "Example texts cannot be null or empty.";

		this.exampleTexts = Sets.newHashSet(exampleTexts);
		return this;
	}
}
