/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * elastic command executor repository
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticCommandExecutorRepository {
	/**
	 * get elastic commander
	 * 
	 * @return
	 */
	ElasticCommandExecutor getCommandExecutor();

	/**
	 * destroy repository
	 */
	void destroy() throws ElasticCommandException;
}
