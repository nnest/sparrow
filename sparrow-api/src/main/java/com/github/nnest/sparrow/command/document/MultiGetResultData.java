/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommandResultData;

/**
 * multiple get result data
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface MultiGetResultData extends ElasticCommandResultData {
	/**
	 * is partial successful
	 * 
	 * @return true if some are failed, some are successful
	 */
	boolean isPartialSuccessful();
}
