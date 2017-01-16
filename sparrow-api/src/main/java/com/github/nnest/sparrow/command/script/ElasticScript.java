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
public interface ElasticScript {
	/**
	 * get script
	 * 
	 * @return
	 */
	String getScript();

	/**
	 * get language
	 * 
	 * @return
	 */
	String getLanguage();

	/**
	 * get parameters object
	 * 
	 * @return
	 */
	Object getParamsObject();
}
