/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommandResultData;

/**
 * update result data
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface UpdateResultData extends ElasticCommandResultData {
	/**
	 * returns true when no change detected. only effective when
	 * {@linkplain Update#isDetectNoopChanged()} is {@code true}. otherwise always
	 * return false.
	 * 
	 * @return
	 */
	boolean isNoopChanged();
}
