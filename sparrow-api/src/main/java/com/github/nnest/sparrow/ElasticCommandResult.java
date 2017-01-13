/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * elastic command result
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticCommandResult {
	/**
	 * get command which executed
	 * 
	 * @return command
	 */
	ElasticCommand getCommand();

	/**
	 * get result data
	 * 
	 * @param <T>
	 *            command result data
	 * @return data
	 */
	<T extends ElasticCommandResultData> T getResultData();
}
