/**
 * 
 */
package com.github.nnest.sparrow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * default elastic command executor repository
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DefaultElasticCommandExecutorRepository implements ElasticCommandExecutorRepository {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ElasticSettings settings = null;
	private ElasticCommandExecutorCreator commandExecutorCreator = null;

	public DefaultElasticCommandExecutorRepository(ElasticSettings settings,
			ElasticCommandExecutorCreator commandExecutorCreator) {
		this.setSettings(settings);
		this.setCommandExecutorCreator(commandExecutorCreator);
	}

	/**
	 * @return the settings
	 */
	public ElasticSettings getSettings() {
		return settings;
	}

	/**
	 * @param settings
	 *            the settings to set
	 */
	private void setSettings(ElasticSettings settings) {
		assert settings != null : "Elastic settings cannot be null.";

		this.settings = settings;
	}

	/**
	 * @return the commandExecutorCreator
	 */
	public ElasticCommandExecutorCreator getCommandExecutorCreator() {
		return commandExecutorCreator;
	}

	/**
	 * @param commandExecutorCreator
	 *            the commandExecutorCreator to set
	 */
	private void setCommandExecutorCreator(ElasticCommandExecutorCreator commandExecutorCreator) {
		assert commandExecutorCreator != null : "Elastic command executor creator cannot be null.";

		this.commandExecutorCreator = commandExecutorCreator;
	}

	/**
	 * @return the logger
	 */
	protected Logger getLogger() {
		return logger;
	}

	/**
	 * create a new command executor for each call
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandExecutorRepository#getCommandExecutor()
	 */
	@Override
	public ElasticCommandExecutor getCommandExecutor() {
		return this.getCommandExecutorCreator().create(this.getSettings());
	}

	/**
	 * default do nothing
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandExecutorRepository#destroy()
	 */
	@Override
	public void destroy() throws ElasticCommandException {
	}
}
