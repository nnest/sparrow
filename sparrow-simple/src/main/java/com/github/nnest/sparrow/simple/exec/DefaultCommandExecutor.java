/**
 * 
 */
package com.github.nnest.sparrow.simple.exec;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
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
import com.github.nnest.sparrow.simple.CommandExecutionHandler;
import com.github.nnest.sparrow.simple.CommandExecutor;
import com.github.nnest.sparrow.simple.CommandTemplate;
import com.github.nnest.sparrow.simple.CommandTemplateContext;
import com.github.nnest.sparrow.simple.token.BodyKey;
import com.github.nnest.sparrow.simple.token.BodyValue;
import com.github.nnest.sparrow.simple.token.Endpoint;
import com.github.nnest.sparrow.simple.token.HeaderKey;
import com.github.nnest.sparrow.simple.token.HeaderValue;
import com.github.nnest.sparrow.simple.token.ParamKey;
import com.github.nnest.sparrow.simple.token.ParamValue;
import com.github.nnest.sparrow.simple.token.Token;
import com.github.nnest.sparrow.simple.token.Tokens;
import com.google.common.base.Joiner;

/**
 * default command executor.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DefaultCommandExecutor implements CommandExecutor {
	public static final String BODY_KEY_NOOP = "@noop";
	public static final String BODY_VALUE_NOOP_OBJECT = "@noopobj";
	public static final char BODY_KEY_BULK_PREFIX = '@';

	private Logger logger = LoggerFactory.getLogger(getClass());

	private CommandTemplateContext templateContext = null;
	private RestClientBuilder restClientBuilder = null;
	private BodyValueConverter bodyValueConverter = null;

	private boolean nullValueIgnoredInBody = true;

	public DefaultCommandExecutor() {
		this.bodyValueConverter = new BodyValueConverterChain( //
				// new PrimitiveBodyValueConverter(), //
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
	 *            params
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
	 *            params
	 * @return http entity
	 * @throws UnsupportedCharsetException
	 *             exception
	 * @throws JsonProcessingException
	 *             exception
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
	 * transform body map.
	 * 
	 * @param map
	 *            map
	 * @param params
	 *            params
	 * @return string
	 * @version 0.0.3
	 */
	protected String transformBodyMap(Map<BodyKey, Object> map, Object params) {
		BodyProperties attrs = new BodyProperties();

		for (Map.Entry<BodyKey, Object> entry : map.entrySet()) {
			String propertyName = this.transformBodyKey(entry.getKey(), params);
			String propertyValue = this.transformBodyValue(entry.getValue(), params);
			if (propertyValue == null) {
				if (this.isNullValueIgnoredInBody()) {
					// null value ignored
					continue;
				} else {
					// null value not ignored
					propertyValue = "null";
				}
			}
			attrs.push(propertyName, propertyValue);
		}

		return attrs.print();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected String transformBodyValue(Object value, Object params) {
		if (value == null) {
			return null;
		} else if (value instanceof Map) {
			return this.transformBodyMap((Map<BodyKey, Object>) value, params);
		} else if (value instanceof List) {
			List<String> list = new ArrayList<>(((List) value).size());
			for (Object o : (List<Object>) value) {
				list.add(this.transformBodyValue(o, params));
			}
			return "[" + Joiner.on(',').skipNulls().join(list) + "]";
		} else if (value instanceof BodyValue) {
			BodyValue bodyValue = (BodyValue) value;
			if (bodyValue.tokenCount() == 1 //
					&& BODY_VALUE_NOOP_OBJECT.equals(bodyValue.getTokens().get(0).getToken())) {
				return "{}";
			}
			return this.getBodyValueConverter().convert( //
					this.transformTokensToObject((BodyValue) value, params));
		} else {
			return this.getBodyValueConverter().convert(value);
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
		if (key.tokenCount() == 1) {
			String token = key.getTokens().get(0).getToken();
			if (BODY_KEY_NOOP.equals(token)) {
				return BODY_KEY_NOOP;
			} else if (token.length() > 0 && token.charAt(0) == BODY_KEY_BULK_PREFIX) {
				return token;
			}
		}
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

	public static class BodyProperties {
		private List<BodyProperty> properties = new LinkedList<BodyProperty>();
		private boolean bulk = false;
		private boolean noopKey = false;

		/**
		 * push property into properties
		 * 
		 * @param name
		 *            name
		 * @param value
		 *            value
		 */
		public void push(String name, String value) {
			this.push(new BodyProperty(name, value));
		}

		/**
		 * push property into properties
		 * 
		 * @param property
		 *            property
		 */
		public void push(BodyProperty property) {
			// check the properties is bulk or not
			String name = property.getName();
			if (name.length() > 0 && property.getName().charAt(0) == BODY_KEY_BULK_PREFIX) {
				this.bulk = true;
				this.noopKey = true;
			} else if (BODY_KEY_NOOP.equals(name)) {
				this.noopKey = true;
			}
			this.properties.add(property);
		}

		/**
		 * @return the bulk
		 */
		public boolean isBulk() {
			return bulk;
		}

		/**
		 * @return the noop key
		 */
		public boolean isNoopKey() {
			return noopKey;
		}

		/**
		 * @return the properties
		 */
		public List<BodyProperty> getProperties() {
			return properties;
		}

		/**
		 * size of properties
		 * 
		 * @return size
		 */
		public int count() {
			return properties.size();
		}

		/**
		 * print properties to string format
		 * 
		 * @return properties string
		 */
		public String print() {
			if (this.count() == 0) {
				return null;
			}

			List<BodyProperty> properties = this.getProperties();
			if (this.isBulk()) {
				Collections.sort(properties);
			}
			StringBuilder sb = new StringBuilder(1024);
			if (!this.isNoopKey()) {
				sb.append('{');
			}
			for (int index = 0, count = this.count(); index < count; index++) {
				sb.append(properties.get(index).print());
				if (index < count - 1) {
					if (this.isBulk()) {
						sb.append('\n');
					} else {
						sb.append(',');
					}
				}
			}
			if (this.isBulk()) {
				sb.append('\n');
			}
			if (!this.isNoopKey()) {
				sb.append('}');
			}
			return sb.toString();
		}
	}

	/**
	 * body property
	 * 
	 * @author brad.wu
	 * @since 0.0.3
	 * @version 0.0.3
	 */
	public static class BodyProperty implements Comparable<BodyProperty> {
		private String name = null;
		private String value = null;

		public BodyProperty(String name, String value) {
			this.setName(name);
			this.setValue(value);
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value
		 *            the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * print this
		 * 
		 * @return string
		 */
		public String print() {
			StringBuilder sb = new StringBuilder(64);
			String name = this.getName();
			if (BODY_KEY_NOOP.equals(name)) {
				// do nothing
			} else if (name.length() > 0 && name.charAt(0) == BODY_KEY_BULK_PREFIX) {
				// do nothing
			} else {
				sb.append('"').append(name).append("\":");
			}
			sb.append(this.getValue());
			return sb.toString();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(BodyProperty another) {
			return this.getName().compareTo(another.getName());
		}
	}
}
