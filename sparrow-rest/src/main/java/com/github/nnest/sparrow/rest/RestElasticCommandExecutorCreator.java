/**
 * 
 */
package com.github.nnest.sparrow.rest;

import com.github.nnest.sparrow.ElasticCommandExecutor;
import com.github.nnest.sparrow.ElasticCommandExecutorCreator;
import com.github.nnest.sparrow.ElasticSettings;

/**
 * rest elastic command executor creator
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestElasticCommandExecutorCreator implements ElasticCommandExecutorCreator {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandExecutorCreator#create(com.github.nnest.sparrow.ElasticSettings)
	 */
	@Override
	public ElasticCommandExecutor create(ElasticSettings settings) {
		return new RestElasticCommandExecutor(settings);
	}
}
