/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * elastic command result handler
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticCommandResultHanlder {
	/**
	 * handle elastic command result
	 * 
	 * @param result
	 */
	void handle(ElasticCommandResult result);
}
