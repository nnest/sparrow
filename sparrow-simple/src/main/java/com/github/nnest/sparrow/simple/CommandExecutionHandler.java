/**
 * 
 */
package com.github.nnest.sparrow.simple;

/**
 * command execution handler
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface CommandExecutionHandler {
	/**
	 * on success
	 * 
	 * @param response
	 *            response
	 */
	void onSuccess(Object response);

	/**
	 * on failure
	 * 
	 * @param exception
	 *            exception
	 */
	void onFailure(Exception exception);

	/**
	 * on client close failure
	 * 
	 * @param exception
	 *            exception
	 */
	void onClientCloseFailure(Exception exception);

	/**
	 * on client prepare exception
	 * 
	 * @param exception
	 *            exception
	 */
	void onClientPrepareException(Exception exception);

}
