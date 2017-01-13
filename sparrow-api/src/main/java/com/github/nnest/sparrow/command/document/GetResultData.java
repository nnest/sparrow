/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommandResultData;

/**
 * Get result data
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface GetResultData extends ElasticCommandResultData {
	/**
	 * @param <T>
	 *            document
	 * @return the document
	 */
	public <T> T getDocument();
}
