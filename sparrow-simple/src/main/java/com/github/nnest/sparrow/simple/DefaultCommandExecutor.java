/**
 * 
 */
package com.github.nnest.sparrow.simple;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * default command executor
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DefaultCommandExecutor implements CommandExecutor {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private CommandTemplateContext templateContext = null;
	private RestClientBuilder restClientBuilder = null;

	public DefaultCommandExecutor() {
	}

	public DefaultCommandExecutor(CommandTemplateContext templateContext) {
		this(templateContext, null);
	}

	public DefaultCommandExecutor(RestClientBuilder restClientBuilder) {
		this(null, restClientBuilder);
	}

	public DefaultCommandExecutor(CommandTemplateContext templateContext, RestClientBuilder restClientBuilder) {
		this.setTemplateContext(templateContext);
		this.setRestClientBuilder(restClientBuilder);
	}

	/**
	 * @return the templateContext
	 */
	public CommandTemplateContext getTemplateContext() {
		return templateContext;
	}

	/**
	 * @param templateContext
	 *            the templateContext to set
	 */
	public void setTemplateContext(CommandTemplateContext templateContext) {
		this.templateContext = templateContext;
	}

	/**
	 * @return the restClientBuilder
	 */
	public RestClientBuilder getRestClientBuilder() {
		return restClientBuilder;
	}

	/**
	 * @param restClientBuilder
	 *            the restClientBuilder to set
	 */
	public void setRestClientBuilder(RestClientBuilder restClientBuilder) {
		this.restClientBuilder = restClientBuilder;
	}

	/**
	 * get rest client
	 * 
	 * @return rest client
	 */
	protected RestClient getRestClient() {
		return this.getRestClientBuilder().build();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutor#execute(java.lang.String,
	 *      java.lang.Object,
	 *      com.github.nnest.sparrow.simple.CommandExecutionHandler)
	 */
	@Override
	public void execute(String templateName, Object params, CommandExecutionHandler handler) {
		CommandTemplate template = this.getTemplateContext().find(templateName).get();

		RestClient client = this.getRestClient();
		try {
			Response response = client.performRequest( //
					template.getMethod().name(), //
					this.transformEndpoint(template.get(), params), //
					this.transformParams(template.getParams()), //
					this.transformBody(template.getBody()), //
					this.transformHeaders(template.getHeaders()));
			handler.onSuccess(response);
		} catch (UnsupportedCharsetException | JsonProcessingException e) {
			handler.onClientPrepareException(e);
		} catch (IOException e) {
			handler.onFailure(e);
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				handler.onClientCloseFailure(e);
			}
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutor#executeAsync(java.lang.String,
	 *      java.lang.Object,
	 *      com.github.nnest.sparrow.simple.CommandExecutionHandler)
	 */
	@Override
	public void executeAsync(String templateName, Object params, CommandExecutionHandler handler) {
		CommandTemplate template = this.getTemplateContext().find(templateName).get();

		RestClient client = this.getRestClient();
		try {
			client.performRequestAsync( //
					template.getMethod().name(), //
					this.transformEndpoint(template.get(), params), //
					this.transformParams(template.getParams()), //
					this.transformBody(template.getBody()), //
					this.createResponseListener(handler, client), //
					this.transformHeaders(template.getHeaders()));
		} catch (Exception e) {
			handler.onClientPrepareException(e);
		}
	}

	/**
	 * create response listener
	 * 
	 * @param handler
	 *            handler
	 * @param client
	 *            client
	 * @return listener
	 */
	protected ResponseListener createResponseListener(CommandExecutionHandler handler, RestClient client) {
		return new ResponseListener() {
			/**
			 * (non-Javadoc)
			 * 
			 * @see org.elasticsearch.client.ResponseListener#onSuccess(org.elasticsearch.client.Response)
			 */
			@Override
			public void onSuccess(Response response) {
				try {
					handler.onSuccess(response);
				} finally {
					try {
						client.close();
					} catch (IOException e) {
						handler.onClientCloseFailure(e);
					}
				}
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.elasticsearch.client.ResponseListener#onFailure(java.lang.Exception)
			 */
			@Override
			public void onFailure(Exception exception) {
				try {
					handler.onFailure(exception);
				} finally {
					try {
						client.close();
					} catch (IOException e) {
						handler.onClientCloseFailure(e);
					}
				}
			}
		};
	}

	/**
	 * tranform endpoint
	 * 
	 * @param endpoint
	 *            endpoint
	 * @param params
	 * @return endpoint as string
	 */
	protected String transformEndpoint(Endpoint endpoint, Object params) {
		StringBuilder sb = new StringBuilder(200);

		List<Token> tokens = endpoint.getTokens();
		for (Token token : tokens) {
			sb.append(token.getValue(params));
		}

		String endpointAsString = sb.toString();
		if (this.getLogger().isDebugEnabled()) {
			this.getLogger().debug("The endpoint[" + endpointAsString + "] should be sent.");
		}
		return endpointAsString;
	}

	/**
	 * transform params
	 * 
	 * @param params
	 *            params
	 * @return empty map if given params is null, otherwise returns itself
	 */
	protected Map<String, String> transformParams(Map<String, String> params) {
		return params == null ? Collections.emptyMap() : params;
	}

	/**
	 * transform body to http entity
	 * 
	 * @param body
	 *            body
	 * @return http entity
	 * @throws UnsupportedCharsetException
	 * @throws JsonProcessingException
	 */
	protected HttpEntity transformBody(Map<String, Object> body)
			throws UnsupportedCharsetException, JsonProcessingException {
		if (body == null) {
			return null;
		}

		String bodyAsString = new ObjectMapper().writeValueAsString(body);
		if (this.getLogger().isDebugEnabled()) {
			this.getLogger().debug("The body should be sent as below:");
			this.getLogger().debug(bodyAsString);
			this.getLogger().debug("The body should be sent ends here.");
		}

		return new StringEntity(bodyAsString, "UTF-8");
	}

	/**
	 * transform headers
	 * 
	 * @param headers
	 *            given headers in map format
	 * @return headers as array
	 */
	protected Header[] transformHeaders(Map<String, String> headers) {
		if (headers == null || headers.size() == 0) {
			return new Header[0];
		}
		List<Header> list = new LinkedList<Header>();
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			list.add(new BasicHeader(entry.getKey(), entry.getValue()));
		}
		return list.toArray(new Header[list.size()]);
	}

	/**
	 * @return the logger
	 */
	protected Logger getLogger() {
		return logger;
	}
}
