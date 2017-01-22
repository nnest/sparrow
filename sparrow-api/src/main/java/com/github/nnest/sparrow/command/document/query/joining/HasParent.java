/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.joining;

import com.github.nnest.sparrow.command.document.query.Example;
import com.google.common.base.Strings;

/**
 * has parent query.<br>
 * {@linkplain type} is parent type.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class HasParent extends AbstractExampleJoiningQuery<HasParent> {
	private String type = null;
	private Boolean score = null;

	public HasParent(String type, Example example) {
		super(example);
		this.withType(type);
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 * @return this
	 */
	public HasParent withType(String type) {
		assert Strings.nullToEmpty(type).trim().length() != 0 : "Type cannot be null or empty.";

		this.type = type;
		return this;
	}

	/**
	 * @return the score
	 */
	public Boolean getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 * @return this
	 */
	public HasParent withScore(Boolean score) {
		assert score != null : "Score cannot be null.";

		this.score = score;
		return this;
	}
}
