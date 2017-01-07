/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * Elastic client.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticClient {
	/**
	 * index a bean, if index not exists, auto create by elastic server.
	 * 
	 * @param bean
	 * @return
	 * @throws ElasticCommandException
	 * @throws ElasticExecutorException
	 */
	<T> T index(T bean) throws ElasticCommandException, ElasticExecutorException;
}
