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
	 * close rest client
	 * 
	 * @throws ElasticCommandException
	 * 
	 */
	void close() throws ElasticCommandException;

	/**
	 * execute command and return result
	 * 
	 * @param command
	 * @return
	 * @throws ElasticCommandException
	 */
	ElasticCommandResult execute(ElasticCommand command) throws ElasticCommandException;

	/**
	 * execute command asynchronized and handle result by result handler
	 * 
	 * @param command
	 * @param commandResultHandler
	 * @throws ElasticCommandException
	 */
	void executeAsync(ElasticCommand command, ElasticCommandResultHanlder commandResultHandler)
			throws ElasticCommandException;
}
