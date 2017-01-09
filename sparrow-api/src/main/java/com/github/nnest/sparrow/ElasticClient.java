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
	 * index a document, if index not exists, auto create by elastic server.
	 * 
	 * @param document
	 *            document to index
	 * @return indexed document
	 * @throws ElasticCommandException
	 *             command exception
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	<T> T index(T document) throws ElasticCommandException, ElasticExecutorException;

	/**
	 * index a document asynchronized, if index not exists, auto create by
	 * elastic server.
	 * 
	 * @param document
	 *            document to index
	 * @param commandResultHandler
	 *            command execution result handler
	 */
	<T> void indexAsync(T document, ElasticCommandResultHandler commandResultHandler);
}
