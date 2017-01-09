/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * elastic command executor creator
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticCommandExecutorCreator {
	/**
	 * create command executor by given settings
	 * 
	 * @param settings
	 *            settings
	 * @return command executor
	 */
	ElasticCommandExecutor create(ElasticSettings settings);
}
