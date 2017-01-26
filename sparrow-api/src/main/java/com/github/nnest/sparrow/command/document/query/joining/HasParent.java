/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.joining;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.google.common.base.Strings;

/**
 * has parent query.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class HasParent extends AbstractExampleJoiningQuery<HasParent> {
	private String parentType = null;
	private Boolean score = null;

	public HasParent(String parentType, Example example) {
		super(example);
		this.withParentType(parentType);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.HAS_PARENT;
	}

	/**
	 * @return the type
	 */
	public String getParentType() {
		return parentType;
	}

	/**
	 * @param parentType
	 *            the type to set
	 * @return this
	 */
	public HasParent withParentType(String parentType) {
		assert Strings.nullToEmpty(parentType).trim().length() != 0 : "Parent type cannot be null or empty.";

		this.parentType = parentType;
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
