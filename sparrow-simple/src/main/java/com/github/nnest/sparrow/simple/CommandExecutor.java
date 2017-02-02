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
	 * execute synchronized
	 * 
	 * @param templateName
	 * @param params
	 */
	public void execute(String templateName, Object params, CommandExecutionHandler handler);

	/**
	 * execute asynchronized
	 * 
	 * @param templateName
	 * @param params
	 */
	public void executeAsync(String templateName, Object params, CommandExecutionHandler handler);
}
