/**
 * 
 */
package com.github.nnest.sparrow.simple.exec;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.elasticsearch.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nnest.sparrow.simple.CommandExecutionException;
import com.github.nnest.sparrow.simple.CommandExecutionHandler;

/**
 * Jackson command executor handler
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractJacksonCommandExecutionHandler implements CommandExecutionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private ObjectMapper objectMapper = null;

	public AbstractJacksonCommandExecutionHandler() {
		this.setObjectMapper(this.createObjectMapper());
	}

	/**
	 * create object mapper
	 * 
	 * @return object mapper
	 */
	protected ObjectMapper createObjectMapper() {
		return new ObjectMapper();
	}

	/**
	 * @return the objectMapper
	 */
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	/**
	 * @param objectMapper
	 *            the objectMapper to set
	 */
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutionHandler#onSuccess(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void onSuccess(Object response) {
		if (response instanceof Response) {
			HttpEntity httpEntity = ((Response) response).getEntity();
			if (httpEntity.getContentLength() != 0) {
				try {
					Map map = this.getObjectMapper().readValue(httpEntity.getContent(), Map.class);
					this.doOnSuccess(map);
				} catch (UnsupportedOperationException | IOException e) {
					throw new CommandExecutionException("Failed on transform response.", e);
				}
			} else {
				// no content
				this.doOnSuccessNoContent();
			}
		} else {
			this.doOnSuccess(response);
		}
	}

	/**
	 * do on success without content
	 */
	protected abstract void doOnSuccessNoContent();

	/**
	 * do on success
	 * 
	 * @param response
	 *            response, which already parsed by jackson
	 */
	protected abstract void doOnSuccess(Object response);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutionHandler#onClientCloseFailure(java.lang.Exception)
	 */
	@Override
	public void onClientCloseFailure(Exception exception) {
		this.getLogger().warn("Failed on close rest client.", exception);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutionHandler#onClientPrepareException(java.lang.Exception)
	 */
	@Override
	public void onClientPrepareException(Exception exception) {
		throw new CommandExecutionException("Failed on client preparation.", exception);
	}

	/**
	 * @return the logger
	 */
	protected Logger getLogger() {
		return logger;
	}
}
