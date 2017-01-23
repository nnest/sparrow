/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommandResultData;

/**
 * Query result data
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface QueryResultData extends ElasticCommandResultData {
	/**
	 * get result count
	 * 
	 * @return result count
	 */
	int getResultCount();
}
