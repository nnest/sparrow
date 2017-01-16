/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommandResultData;

/**
 * Delete result data
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface DeleteResultData extends ElasticCommandResultData {
	/**
	 * check the given data is deleted or not
	 * 
	 * @return is deleted or not
	 */
	boolean isDeleted();
}
