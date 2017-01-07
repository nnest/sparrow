/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.io.IOException;

import org.elasticsearch.client.RestClient;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandException;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticCommandResultHandler;

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
	 * @param command
	 * @return
	 * @throws IOException
	 */
	ElasticCommandResult performRequest(RestClient restClient, ElasticCommand command) throws ElasticCommandException;

	/**
	 * perform request asynchronized
	 * 
	 * @param restClient
	 * @param command
	 * @param commandResultHandler
	 */
	void performRequestAsync(RestClient restClient, ElasticCommand command,
			ElasticCommandResultHandler commandResultHandler);
}
