/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import java.math.BigDecimal;
import java.util.List;

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

	/**
	 * get result items
	 * 
	 * @return result items
	 */
	List<QueryResultItem> getResultItems();

	/**
	 * query result item
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static interface QueryResultItem {
		/**
		 * get score of document
		 * 
		 * @return score
		 */
		BigDecimal getScore();

		/**
		 * get document
		 * 
		 * @param <T>
		 *            any document
		 * @return document
		 */
		<T> T getDocument();
	}
}
