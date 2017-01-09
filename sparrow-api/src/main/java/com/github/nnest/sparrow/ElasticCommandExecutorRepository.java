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
	 * @return command executor
	 */
	ElasticCommandExecutor getCommandExecutor();

	/**
	 * destroy repository
	 * 
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	void destroy() throws ElasticExecutorException;
}
