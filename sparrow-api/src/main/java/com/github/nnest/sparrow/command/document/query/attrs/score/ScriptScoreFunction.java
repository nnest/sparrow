/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.score;

import com.github.nnest.sparrow.command.script.Script;

/**
 * script score function
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ScriptScoreFunction implements ScoreFunction {
	private Script script = null;

	public ScriptScoreFunction(Script script) {
		this.with(script);
	}

	/**
	 * @return the script
	 */
	public Script getScript() {
		return script;
	}

	/**
	 * @param script
	 *            the script to set
	 * @return this
	 */
	public ScriptScoreFunction with(Script script) {
		assert script != null : "Script cannot be null.";

		this.script = script;
		return this;
	}
}
