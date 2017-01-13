/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * Elastic client.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticClient {
	/**
	 * execute elastic command
	 * 
	 * @param command
	 *            command
	 * @param <T>
	 *            command result
	 * @return command result
	 * @throws ElasticCommandException
	 *             command exception
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	<T extends ElasticCommandResult> T execute(ElasticCommand command)
			throws ElasticCommandException, ElasticExecutorException;

	/**
	 * execute elastic command asynchronzied
	 * 
	 * @param command
	 *            command
	 * @param commandResultHandler
	 *            command result handler
	 */
	void executeAysnc(ElasticCommand command, ElasticCommandResultHandler commandResultHandler);
}
