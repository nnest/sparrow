/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.io.IOException;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandException;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticCommandResultHandler;

/**
 * abstract rest command
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractRestCommand implements RestCommand {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.RestCommand#performRequest(org.elasticsearch.client.RestClient,
	 *      com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	public ElasticCommandResult performRequest(RestClient restClient, ElasticCommand command)
			throws ElasticCommandException {
		RestRequest request = this.convertToRestRequest(command);
		Response response;
		try {
			response = restClient.performRequest(request.getMethod(), request.getEndpoint(), request.getParams(),
					request.getEntity(), request.getHeaders());
		} catch (IOException e) {
			throw new ElasticCommandException(String.format("Fail to perform request with command[%1s]", command));
		}
		return this.convertToCommandResult(response, command);
	}

	/**
	 * convert rest response to result
	 * 
	 * @param response
	 * @param command
	 * @return
	 */
	protected abstract ElasticCommandResult convertToCommandResult(Response response, ElasticCommand command);

	/**
	 * convert command to rest request
	 * 
	 * @param command
	 * @return
	 */
	protected abstract RestRequest convertToRestRequest(ElasticCommand command);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.RestCommand#performRequestAsync(org.elasticsearch.client.RestClient,
	 *      com.github.nnest.sparrow.ElasticCommand,
	 *      com.github.nnest.sparrow.ElasticCommandResultHandler)
	 */
	@Override
	public void performRequestAsync(RestClient restClient, ElasticCommand command,
			ElasticCommandResultHandler commandResultHandler) {
		RestRequest request = this.convertToRestRequest(command);
		ResponseListener responseListener = this.getResponseListener(command, commandResultHandler);
		restClient.performRequestAsync(request.getMethod(), request.getEndpoint(), request.getParams(),
				request.getEntity(), responseListener, request.getHeaders());
	}

	/**
	 * get response listener from command result handler
	 * 
	 * @param command
	 * @param commandResultHandler
	 * @return
	 */
	protected ResponseListener getResponseListener(ElasticCommand command,
			ElasticCommandResultHandler commandResultHandler) {
		return new ResponseListener() {
			/**
			 * (non-Javadoc)
			 * 
			 * @see org.elasticsearch.client.ResponseListener#onSuccess(org.elasticsearch.client.Response)
			 */
			@Override
			public void onSuccess(Response response) {
				commandResultHandler.handleSuccess(convertToCommandResult(response, command));
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.elasticsearch.client.ResponseListener#onFailure(java.lang.Exception)
			 */
			@Override
			public void onFailure(Exception exception) {
				commandResultHandler.handleFail(exception);
			}
		};
	}

	public static class RestRequest {
		private String method = null;
		private String endpoint = null;
		private Map<String, String> params = null;
		private HttpEntity entity = null;
		private Header[] headers = null;

		/**
		 * @return the method
		 */
		public String getMethod() {
			return method;
		}

		/**
		 * @param method
		 *            the method to set
		 */
		public void setMethod(String method) {
			this.method = method;
		}

		/**
		 * @return the endpoint
		 */
		public String getEndpoint() {
			return endpoint;
		}

		/**
		 * @param endpoint
		 *            the endpoint to set
		 */
		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}

		/**
		 * @return the params
		 */
		public Map<String, String> getParams() {
			return params;
		}

		/**
		 * @param params
		 *            the params to set
		 */
		public void setParams(Map<String, String> params) {
			this.params = params;
		}

		/**
		 * @return the entity
		 */
		public HttpEntity getEntity() {
			return entity;
		}

		/**
		 * @param entity
		 *            the entity to set
		 */
		public void setEntity(HttpEntity entity) {
			this.entity = entity;
		}

		/**
		 * @return the headers
		 */
		public Header[] getHeaders() {
			return headers;
		}

		/**
		 * @param headers
		 *            the headers to set
		 */
		public void setHeaders(Header[] headers) {
			this.headers = headers;
		}
	}
}
