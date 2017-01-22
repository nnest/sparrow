/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.term;

import com.github.nnest.sparrow.command.document.query.attrs.rewrite.Rewrite;
import com.google.common.base.Strings;

/**
 * Wildcard term
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Wildcard extends AbstractTermLevelQuery<Wildcard> {
	private String exampleText = null;
	private Rewrite rewrite = null;

	public Wildcard(String fieldName) {
		super(fieldName);
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
	public Wildcard withExampleText(String exampleText) {
		assert Strings.nullToEmpty(exampleText).length() != 0 : "Example text cannot be null or empty.";

		this.exampleText = exampleText;
		return this;
	}

	/**
	 * @return the rewrite
	 */
	public Rewrite getRewrite() {
		return rewrite;
	}

	/**
	 * @param rewrite
	 *            the rewrite to set
	 * @return this
	 */
	public Wildcard with(Rewrite rewrite) {
		assert rewrite != null : "Rewrite cannot be null.";

		this.rewrite = rewrite;
		return this;
	}
}
