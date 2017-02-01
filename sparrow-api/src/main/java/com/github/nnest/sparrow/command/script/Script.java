/**
 * 
 */
package com.github.nnest.sparrow.command.script;

/**
 * Elastic script
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface Script {
	/**
	 * get script
	 * 
	 * @return script
	 */
	String getScript();

	/**
	 * get language
	 * 
	 * @return language
	 */
	String getLanguage();

	/**
	 * get parameters object
	 * 
	 * @return parameters
	 */
	Object getParamsObject();
}
