/**
 * 
 */
package com.github.nnest.sparrow.simple;

/**
 * command executor
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface CommandExecutor {
	/**
	 * is null value ignored in body
	 * 
	 * @return boolean
	 */
	boolean isNullValueIgnoredInBody();

	/**
	 * execute synchronized
	 * 
	 * @param templateName
	 * @param params
	 */
	void execute(String templateName, Object params, CommandExecutionHandler handler);

	/**
	 * execute asynchronized
	 * 
	 * @param templateName
	 * @param params
	 */
	void executeAsync(String templateName, Object params, CommandExecutionHandler handler);
}
