/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import com.github.nnest.sparrow.command.document.ExistResultData;

/**
 * exist response.<br>
 * always return true on {@linkplain #isSuccessful()}, even not exists.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ExistResponse implements ExistResultData {
	private boolean found = false;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandResultData#isSuccessful()
	 */
	@Override
	public boolean isSuccessful() {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.ExistResultData#isFound()
	 */
	@Override
	public boolean isFound() {
		return this.found;
	}

	/**
	 * @param found
	 *            the found to set
	 */
	public void setFound(boolean found) {
		this.found = found;
	}
}
