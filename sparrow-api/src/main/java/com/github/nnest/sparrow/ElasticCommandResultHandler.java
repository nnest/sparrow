/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * elastic command result handler
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticCommandResultHandler {
	/**
	 * handle elastic command result on fail
	 * 
	 * @param result
	 *            command execution result
	 */
	void handleSuccess(ElasticCommandResult result);

	/**
	 * handle elastic command result on success
	 * 
	 * @param exception
	 *            exception raised by command execution
	 */
	void handleFail(Exception exception);
}
