/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import java.util.List;

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

	/**
	 * get inner responses
	 * 
	 * @return inner responses
	 */
	public List<InnerGetResultData> getInnerResponses();

	/**
	 * inner get result data, for each single get command in multi get.
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static interface InnerGetResultData extends GetResultData {
		/**
		 * get command
		 * 
		 * @return inner command
		 */
		Get getCommand();
	}
}
