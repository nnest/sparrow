/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import com.github.nnest.sparrow.AbstractElasticCommandExecutor;
import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandException;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticCommandResultHandler;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.ElasticHost;
import com.github.nnest.sparrow.ElasticSettings;
import com.github.nnest.sparrow.rest.command.RestCommandUtil;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;

/**
 * abstract rest elastic command executor
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestElasticCommandExecutor extends AbstractElasticCommandExecutor {
	private RestClient restClient = null;

	public RestElasticCommandExecutor(ElasticSettings settings) {
		super(settings);

		this.restClient = this.createRestClient();
	}

	/**
	 * create rest client
	 * 
	 * @return rest client
	 */
	protected RestClient createRestClient() {
		ElasticSettings settings = this.getSettings();

		RestClientBuilder builder = RestClient.builder(
				Iterables.toArray(Iterables.transform(settings.getHosts(), new Function<ElasticHost, HttpHost>() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.google.common.base.Function#apply(java.lang.Object)
					 */
					@Override
					public HttpHost apply(ElasticHost host) {
						return new HttpHost(host.getHostName(), host.getPort(), host.getScheme());
					}
				}), HttpHost.class));
		// TODO other settings
		return builder.build();
	}

	/**
	 * @return the restClient
	 */
	public RestClient getRestClient() {
		return restClient;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandExecutor#execute(com.github.nnest.sparrow.ElasticCommand)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ElasticCommandResult execute(ElasticCommand command)
			throws ElasticCommandException, ElasticExecutorException {
		return RestCommandUtil.get(command.getCommandKind()).performRequest(this.getRestClient(), command);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandExecutor#executeAsync(com.github.nnest.sparrow.ElasticCommand,
	 *      com.github.nnest.sparrow.ElasticCommandResultHandler)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void executeAsync(ElasticCommand command, ElasticCommandResultHandler commandResultHandler) {
		RestCommandUtil.get(command.getCommandKind()).performRequestAsync(this.getRestClient(), command,
				commandResultHandler);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.AbstractElasticCommandExecutor#close()
	 */
	@Override
	public void close() throws ElasticExecutorException {
		try {
			this.getRestClient().close();
		} catch (IOException e) {
			throw new ElasticExecutorException("Fail to close rest client.", e);
		}
	}
}
