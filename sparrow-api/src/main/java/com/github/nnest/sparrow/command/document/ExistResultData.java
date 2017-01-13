/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommandResultData;

/**
 * exist result data
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ExistResultData extends ElasticCommandResultData {
	/**
	 * found or not
	 * 
	 * @return found or not
	 */
	boolean isFound();
}
