/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.joining;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.attrs.ScoreMode;
import com.google.common.base.Strings;

/**
 * nested query
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Nested extends AbstractExampleJoiningQuery<Nested> {
	private String path = null;
	private ScoreMode scoreMode = null;

	public Nested(String path, Example example) {
		super(example);
		this.withPath(path);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.NESTED;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 * @return this
	 */
	public Nested withPath(String path) {
		assert Strings.nullToEmpty(path).trim().length() != 0 : "Path cannot be null or blank.";

		this.path = path;
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
	public Nested with(ScoreMode scoreMode) {
		assert scoreMode != null && scoreMode != ScoreMode.MULTIPLY : "Score mode cannot be null or ScoreMode.MULTIPLY";

		this.scoreMode = scoreMode;
		return this;
	}
}
