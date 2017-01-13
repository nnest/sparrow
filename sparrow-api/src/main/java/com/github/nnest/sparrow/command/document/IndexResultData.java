/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommandResultData;

/**
 * Index result data
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface IndexResultData extends ElasticCommandResultData {
	/**
	 * @param <T>
	 *            document
	 * @return the document
	 */
	public <T> T getDocument();

	/**
	 * @return the resultType
	 */
	public IndexResultType getResultType();
}
