/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.rewrite;

/**
 * constant rewrite
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public enum ConstantRewrite implements Rewrite {
	CONSTANT_SCORE("constant_score"), //
	SCORING_BOOLEAN("scoring_boolean"), //
	CONSTANT_SCORE_BOOLEAN("constant_score_boolean");

	private String name = null;

	private ConstantRewrite(String name) {
		this.name = name;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.attrs.rewrite.Rewrite#asString()
	 */
	@Override
	public String asString() {
		return this.name;
	}
}
