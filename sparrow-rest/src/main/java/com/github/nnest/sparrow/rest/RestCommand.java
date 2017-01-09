/**
 * 
 */
package com.github.nnest.sparrow.rest;

import org.elasticsearch.client.RestClient;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandException;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticCommandResultHandler;
import com.github.nnest.sparrow.ElasticExecutorException;

/**
 * rest command<br>
 * any command implements this must be stateless and thread safe.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface RestCommand {
	/**
	 * perform request
	 * 
	 * @param restClient
	 *            rest client
	 * @param command
	 *            command to execute
	 * @return command execution result
	 * @throws ElasticExecutorException
	 *             executor exception
	 * @throws ElasticCommandException
	 *             command exception
	 */
	ElasticCommandResult performRequest(RestClient restClient, ElasticCommand command)
			throws ElasticCommandException, ElasticExecutorException;

	/**
	 * perform request asynchronized
	 * 
	 * @param restClient
	 *            rest client
	 * @param command
	 *            ommand to execute
	 * @param commandResultHandler
	 *            command execution result
	 */
	void performRequestAsync(RestClient restClient, ElasticCommand command,
			ElasticCommandResultHandler commandResultHandler);
}
