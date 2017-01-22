/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.joining;

import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.attrs.ScoreMode;
import com.google.common.base.Strings;

/**
 * has child query.<br>
 * {@linkplain type} is child type.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class HasChild extends AbstractExampleJoiningQuery<HasChild> {
	private String type = null;
	private ScoreMode scoreMode = null;
	private Integer minChildren = null;
	private Integer maxChildren = null;

	public HasChild(String type, Example example) {
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
	public HasChild withType(String type) {
		assert Strings.nullToEmpty(type).trim().length() != 0 : "Type cannot be null or empty.";

		this.type = type;
		return this;
	}

	/**
	 * @return the scoreMode
	 */
	public ScoreMode getScoreMode() {
		return scoreMode;
	}

	/**
	 * @param scoreMode
	 *            the scoreMode to set
	 * @return this
	 */
	public HasChild with(ScoreMode scoreMode) {
		assert scoreMode != null && scoreMode != ScoreMode.MULTIPLY : "Score mode cannot be null or ScoreMode.MULTIPLY";

		this.scoreMode = scoreMode;
		return this;
	}

	/**
	 * @return the minChildren
	 */
	public Integer getMinChildren() {
		return minChildren;
	}

	/**
	 * @param minChildren
	 *            the minChildren to set
	 * @return this
	 */
	public HasChild setMinChildren(Integer minChildren) {
		assert minChildren != null && minChildren >= 0 : "Min children cannot be null, and must be zero or positive.";

		this.minChildren = minChildren;
		return this;
	}

	/**
	 * @return the maxChildren
	 */
	public Integer getMaxChildren() {
		return maxChildren;
	}

	/**
	 * @param maxChildren
	 *            the maxChildren to set
	 * @return this
	 */
	public HasChild setMaxChildren(Integer maxChildren) {
		assert maxChildren != null && maxChildren >= 0 : "Max children cannot be null, and must be zero or positive.";

		this.maxChildren = maxChildren;
		return this;
	}
}
