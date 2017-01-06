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
	 * get result object
	 * 
	 * @return
	 */
	<T> T getResultObject();
}
