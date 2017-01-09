/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * elastic command executor, send request to elastic server and parse response,
 * returns to caller.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticCommandExecutor {
	/**
	 * close command executor
	 * 
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	void close() throws ElasticExecutorException;

	/**
	 * execute command and return result
	 * 
	 * @param command
	 *            command to execute
	 * @return command execution result
	 * @throws ElasticCommandException
	 *             command exception
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	ElasticCommandResult execute(ElasticCommand command) throws ElasticCommandException, ElasticExecutorException;

	/**
	 * execute command asynchronized and handle result by result handler
	 * 
	 * @param command
	 *            command to execute
	 * @param commandResultHandler
	 *            command execution result handler
	 */
	void executeAsync(ElasticCommand command, ElasticCommandResultHandler commandResultHandler);
}
