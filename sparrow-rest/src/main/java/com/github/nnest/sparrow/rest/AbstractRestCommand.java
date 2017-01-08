/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandException;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticCommandResultHandler;
import com.github.nnest.sparrow.ElasticExecutorException;

/**
 * abstract rest command
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractRestCommand implements RestCommand {
	private static Map<Class<?>, DocumentIdVisitor> idVisitors = new ConcurrentHashMap<Class<?>, DocumentIdVisitor>();

	/**
	 * (non-Javadoc)
	 * 
	 * @throws ElasticExecutorException
	 * 
	 * @see com.github.nnest.sparrow.rest.RestCommand#performRequest(org.elasticsearch.client.RestClient,
	 *      com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	public ElasticCommandResult performRequest(RestClient restClient, ElasticCommand command)
			throws ElasticCommandException, ElasticExecutorException {
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
	 * @throws ElasticExecutorException
	 */
	protected abstract RestRequest convertToRestRequest(ElasticCommand command) throws ElasticExecutorException;

	/**
	 * (non-Javadoc)
	 * 
	 * @throws ElasticExecutorException
	 * 
	 * @see com.github.nnest.sparrow.rest.RestCommand#performRequestAsync(org.elasticsearch.client.RestClient,
	 *      com.github.nnest.sparrow.ElasticCommand,
	 *      com.github.nnest.sparrow.ElasticCommandResultHandler)
	 */
	@Override
	public void performRequestAsync(RestClient restClient, ElasticCommand command,
			ElasticCommandResultHandler commandResultHandler) {
		try {
			RestRequest request = this.convertToRestRequest(command);
			ResponseListener responseListener = this.getResponseListener(command, commandResultHandler);
			restClient.performRequestAsync(request.getMethod(), request.getEndpoint(), request.getParams(),
					request.getEntity(), responseListener, request.getHeaders());
		} catch (ElasticExecutorException e) {
			commandResultHandler.handleFail(e);
		}
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

	/**
	 * get id value of given document and id field
	 * 
	 * @param document
	 * @param idField
	 * @return
	 * @throws ElasticExecutorException
	 */
	protected String getIdValue(Object document, String idField) throws ElasticExecutorException {
		Class<?> documentType = document.getClass();
		DocumentIdVisitor visitor = idVisitors.get(documentType);
		if (visitor == null) {
			visitor = new DocumentIdVisitor(documentType, idField);
			idVisitors.put(documentType, visitor);
		}
		Object value = visitor.getIdValue(document);
		return value == null ? null : value.toString();
	}

	/**
	 * document id visitor
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	private static class DocumentIdVisitor {
		private static final Class<?>[] NO_PARAM_TYPES = new Class<?>[0];
		private static final Object[] NO_PARAM_OBJECTS = new Object[0];

		private Field field = null;
		private Method method = null;

		public DocumentIdVisitor(Class<?> documentType, String idFieldName) throws ElasticExecutorException {
			String methodName = idFieldName.substring(0, 1).toUpperCase() + idFieldName.substring(1);
			try {
				this.method = documentType.getDeclaredMethod("get" + methodName, NO_PARAM_TYPES);
				this.method.setAccessible(true);
			} catch (Exception e) {
				try {
					this.field = documentType.getDeclaredField(idFieldName);
					this.field.setAccessible(true);
				} catch (Exception ex) {
					throw new ElasticExecutorException(String.format(
							"Fail to find visitor of id field[%1s] on document[%2s]", idFieldName, documentType));
				}
			}
		}

		/**
		 * get id value of given document
		 * 
		 * @param document
		 * @return
		 * @throws ElasticExecutorException
		 */
		public Object getIdValue(Object document) throws ElasticExecutorException {
			try {
				if (this.method != null) {
					return this.method.invoke(document, NO_PARAM_OBJECTS);
				} else if (this.field != null) {
					return this.field.get(document);
				} else {
					throw new ElasticExecutorException(
							String.format("Fail to find visitor on document[%2s]", document.getClass()));
				}
			} catch (ElasticExecutorException e) {
				throw e;
			} catch (Exception e) {
				throw new ElasticExecutorException(
						String.format("Failed to visit id value on document[%2s]", document.getClass()), e);
			}
		}
	}

	/**
	 * Rest request object
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class RestRequest {
		private static final Header[] NO_HEADER = new Header[0];

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
			return params == null ? Collections.emptyMap() : params;
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
			return headers == null ? NO_HEADER : headers;
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
