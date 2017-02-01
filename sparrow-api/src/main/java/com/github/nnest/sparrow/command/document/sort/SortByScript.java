/**
 * 
 */
package com.github.nnest.sparrow.command.document.sort;

import com.github.nnest.sparrow.command.script.Script;

/**
 * sort by script
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class SortByScript implements SortBy {
	private Script script = null;
	private ScriptSortType type = null;

	public SortByScript(Script script) {
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
	public SortByScript with(Script script) {
		assert script != null : "Script cannot be null.";

		this.script = script;
		return this;
	}

	/**
	 * @return the type
	 */
	public ScriptSortType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 * @return this
	 */
	public SortByScript with(ScriptSortType type) {
		assert type != null : "Type cannot be null.";

		this.type = type;
		return this;
	}

	/**
	 * value of
	 * 
	 * @param script
	 *            script
	 * @return sort by
	 */
	public static SortByScript valueOf(Script script) {
		return new SortByScript(script);
	}
}
