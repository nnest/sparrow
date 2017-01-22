/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.score;

import com.github.nnest.sparrow.command.script.ElasticScript;

/**
 * script score function
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ScriptScoreFunction implements ScoreFunction {
	private ElasticScript script = null;

	public ScriptScoreFunction(ElasticScript script) {
		this.with(script);
	}

	/**
	 * @return the script
	 */
	public ElasticScript getScript() {
		return script;
	}

	/**
	 * @param script
	 *            the script to set
	 * @return this
	 */
	public ScriptScoreFunction with(ElasticScript script) {
		assert script != null : "Script cannot be null.";

		this.script = script;
		return this;
	}
}
