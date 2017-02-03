/**
 * 
 */
package com.github.nnest.sparrow.simple;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collections;
import java.util.HashMap;
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
	private BodyValueConverter bodyValueConverter = null;

	private boolean nullValueIgnoredInBody = true;

	public DefaultCommandExecutor() {
		this.bodyValueConverter = new BodyValueConverterChain( //
//				new PrimitiveBodyValueConverter(), //
				new JacksonBodyValueConverter());
	}

	public DefaultCommandExecutor(CommandTemplateContext templateContext, RestClientBuilder restClientBuilder) {
		this();
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
	 * @return the bodyValueConverter
	 */
	public BodyValueConverter getBodyValueConverter() {
		return bodyValueConverter;
	}

	/**
	 * @param bodyValueConverter
	 *            the bodyValueConverter to set
	 */
	public void setBodyValueConverter(BodyValueConverter bodyValueConverter) {
		this.bodyValueConverter = bodyValueConverter;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutor#isNullValueIgnoredInBody()
	 */
	@Override
	public boolean isNullValueIgnoredInBody() {
		return this.nullValueIgnoredInBody;
	}

	/**
	 * @param nullValueIgnoredInBody
	 *            the nullValueIgnoredInBody to set
	 */
	public void setNullValueIgnoredInBody(boolean nullValueIgnoredInBody) {
		this.nullValueIgnoredInBody = nullValueIgnoredInBody;
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
					this.transformEndpoint(template.getTransformedEndpoint(), params), //
					this.transformParams(template.getTransformedParams(), params), //
					this.transformBody(template.getTransformedBody(), params), //
					this.transformHeaders(template.getTransformedHeaders(), params));
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
					this.transformEndpoint(template.getTransformedEndpoint(), params), //
					this.transformParams(template.getTransformedParams(), params), //
					this.transformBody(template.getTransformedBody(), params), //
					this.createResponseListener(handler, client), //
					this.transformHeaders(template.getTransformedHeaders(), params));
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
		String endpointAsString = this.transformTokens(endpoint, params);
		if (this.getLogger().isDebugEnabled()) {
			this.getLogger().debug("The endpoint[" + endpointAsString + "] should be sent.");
		}
		return endpointAsString;
	}

	/**
	 * transform params
	 * 
	 * @param transformedParams
	 *            params
	 * @param params
	 *            params
	 * @return empty map if given params is null, otherwise returns itself
	 */
	protected Map<String, String> transformParams(Map<ParamKey, ParamValue> transformedParams, Object params) {
		if (transformedParams == null) {
			return Collections.emptyMap();
		}

		Map<String, String> targetMap = new HashMap<>();
		for (Map.Entry<ParamKey, ParamValue> entry : transformedParams.entrySet()) {
			targetMap.put( //
					this.transformTokens(entry.getKey(), params), //
					this.transformTokens(entry.getValue(), params));
		}

		if (this.getLogger().isDebugEnabled()) {
			for (Map.Entry<String, String> entry : targetMap.entrySet()) {
				this.getLogger().debug("The parameters should be sent as below:");
				this.getLogger().debug(String.format("Query string [%1$s=%2$s]", entry.getKey(), entry.getValue()));
				this.getLogger().debug("The parameters should be sent ends here.");
			}
		}
		return targetMap;
	}

	/**
	 * transform body to http entity
	 * 
	 * @param body
	 *            body
	 * @param params
	 * @return http entity
	 * @throws UnsupportedCharsetException
	 * @throws JsonProcessingException
	 */
	protected HttpEntity transformBody(Map<BodyKey, Object> body, Object params)
			throws UnsupportedCharsetException, JsonProcessingException {
		if (body == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder() //
				.append(this.transformBodyMap(body, params));

		String bodyAsString = sb.toString();
		if (this.getLogger().isDebugEnabled()) {
			this.getLogger().debug("The body should be sent as below:");
			this.getLogger().debug(bodyAsString);
			this.getLogger().debug("The body should be sent ends here.");
		}

		return new StringEntity(bodyAsString, "UTF-8");
	}

	/**
	 * transform body map
	 * 
	 * @param map
	 *            map
	 * @param params
	 *            params
	 * @return string
	 */
	protected String transformBodyMap(Map<BodyKey, Object> map, Object params) {
		List<StringBuilder> attrs = new LinkedList<StringBuilder>();
		for (Map.Entry<BodyKey, Object> entry : map.entrySet()) {
			StringBuilder attr = new StringBuilder(128);
			String propertyName = this.transformBodyKey(entry.getKey(), params);
			attr.append('"').append(propertyName).append("\":");
			Object propertyValue = this.transformBodyValue(entry.getValue(), params);
			if (propertyValue == null) {
				if (this.isNullValueIgnoredInBody()) {
					// null value ignored
					continue;
				} else {
					// null value not ignored
					attr.append("null");
				}
			} else {
				attr.append(this.getBodyValueConverter().convert(propertyValue));
			}
			attrs.add(attr);
		}

		if (attrs.size() == 0) {
			// no attributes
			return null;
		}

		StringBuilder sb = new StringBuilder(1024);
		sb.append('{');
		for (int index = 0, count = attrs.size(); index < count; index++) {
			sb.append(attrs.get(index).toString());
			if (index < count - 1) {
				sb.append(',');
			}
		}
		sb.append('}');
		return sb.toString();
	}

	/**
	 * transform body value
	 * 
	 * @param value
	 *            body value
	 * @param params
	 *            params
	 * @return string
	 */
	@SuppressWarnings("unchecked")
	protected Object transformBodyValue(Object value, Object params) {
		if (value == null) {
			return null;
		} else if (value instanceof Map) {
			return this.transformBodyMap((Map<BodyKey, Object>) value, params);
		} else if (value instanceof BodyValue) {
			return this.transformTokensToObject((BodyValue) value, params);
		} else {
			return value;
		}
	}

	/**
	 * transform body key
	 * 
	 * @param key
	 *            body key
	 * @param params
	 *            params
	 * @return string
	 */
	protected String transformBodyKey(BodyKey key, Object params) {
		return this.transformTokens(key, params);
	}

	/**
	 * transform headers
	 * 
	 * @param headers
	 *            given headers in map format
	 * @param params
	 *            params
	 * @return headers as array
	 */
	protected Header[] transformHeaders(Map<HeaderKey, HeaderValue> headers, Object params) {
		if (headers == null || headers.size() == 0) {
			return new Header[0];
		}
		List<Header> list = new LinkedList<Header>();
		for (Map.Entry<HeaderKey, HeaderValue> entry : headers.entrySet()) {
			list.add(new BasicHeader(this.transformTokens(entry.getKey(), params),
					this.transformTokens(entry.getValue(), params)));
		}
		if (this.getLogger().isDebugEnabled()) {
			for (Header header : list) {
				this.getLogger().debug("The header should be sent as below:");
				this.getLogger().debug(String.format("Header [%1$s=%2$s]", header.getName(), header.getValue()));
				this.getLogger().debug("The header should be sent ends here.");
			}
		}
		return list.toArray(new Header[list.size()]);
	}

	/**
	 * transform tokens
	 * 
	 * @param tokens
	 *            tokens
	 * @param params
	 *            params
	 * @return tokens as string
	 */
	protected String transformTokens(Tokens tokens, Object params) {
		StringBuilder sb = new StringBuilder(128);

		boolean hasValue = false;
		List<Token> tokenList = tokens.getTokens();
		for (Token token : tokenList) {
			Object value = token.getValue(params);
			if (value != null) {
				sb.append(token.getValue(params));
				hasValue = true;
			}
		}
		return hasValue ? sb.toString() : null;
	}

	/**
	 * transform tokens to object
	 * 
	 * @param tokens
	 *            tokens
	 * @param params
	 *            params
	 * @return tokens to object
	 */
	protected Object transformTokensToObject(Tokens tokens, Object params) {
		if (tokens.tokenCount() == 1) {
			return tokens.getTokens().get(0).getValue(params);
		} else {
			return this.transformTokens(tokens, params);
		}
	}

	/**
	 * @return the logger
	 */
	protected Logger getLogger() {
		return logger;
	}
}
