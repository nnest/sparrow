/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.term;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.google.common.base.Strings;

/**
 * term
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Term extends AbstractTermLevelQuery<Term> {
	private String exampleText = null;

	public Term(String fieldName) {
		super(fieldName);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.TERM;
	}

	/**
	 * @return the exampleText
	 */
	public String getExampleText() {
		return exampleText;
	}

	/**
	 * @param exampleText
	 *            the exampleText to set
	 * @return this
	 */
	public Term withExampleText(String exampleText) {
		assert Strings.nullToEmpty(exampleText).length() != 0 : "Example text cannot be null or empty.";

		this.exampleText = exampleText;
		return this;
	}
}
