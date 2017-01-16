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
public class PainlessElasticScript extends DefaultElasticScript<PainlessElasticScript> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.script.DefaultElasticScript#getLanguage()
	 */
	@Override
	public String getLanguage() {
		return "painless";
	}

	/**
	 * do nothing, simply return this
	 * 
	 * @see com.github.nnest.sparrow.command.script.DefaultElasticScript#withLanguage(java.lang.String)
	 */
	@Override
	public PainlessElasticScript withLanguage(String language) {
		return this;
	}
}
