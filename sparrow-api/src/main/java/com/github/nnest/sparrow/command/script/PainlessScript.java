/**
 * 
 */
package com.github.nnest.sparrow.command.script;

/**
 * painless elastic script
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class PainlessScript extends DefaultScript<PainlessScript> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.script.DefaultScript#getLanguage()
	 */
	@Override
	public String getLanguage() {
		return "painless";
	}

	/**
	 * do nothing, simply return this
	 * 
	 * @see com.github.nnest.sparrow.command.script.DefaultScript#withLanguage(java.lang.String)
	 */
	@Override
	public PainlessScript withLanguage(String language) {
		return this;
	}

	/**
	 * value of
	 * 
	 * @param script
	 *            script
	 * @return script
	 */
	public static PainlessScript valueOf(String script) {
		return new PainlessScript().withScript(script);
	}
}
