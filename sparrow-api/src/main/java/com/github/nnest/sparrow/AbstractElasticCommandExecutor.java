/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * abstract elastic command executor
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractElasticCommandExecutor implements ElasticCommandExecutor {
	private ElasticSettings settings = null;

	public AbstractElasticCommandExecutor(ElasticSettings settings) {
		this.setSettings(settings);
	}

	/**
	 * @return the settings
	 */
	protected ElasticSettings getSettings() {
		return settings;
	}

	/**
	 * @param settings
	 *            the settings to set
	 */
	protected void setSettings(ElasticSettings settings) {
		assert settings != null : "Elastic settings cannot be null.";

		this.settings = settings;
	}

	/**
	 * default do nothing, release resource if exists
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandExecutor#close()
	 */
	@Override
	public void close() throws ElasticCommandException {
	}
}
