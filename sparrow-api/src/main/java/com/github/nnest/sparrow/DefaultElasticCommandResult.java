/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * default elastic command result
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DefaultElasticCommandResult implements ElasticCommandResult {
	private Object resultObject = null;

	public DefaultElasticCommandResult() {

	}

	public DefaultElasticCommandResult(Object resultObject) {
		this.setResultObject(resultObject);
	}

	/**
	 * @param resultObject
	 *            the resultObject to set
	 */
	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandResult#getResultObject()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getResultObject() {
		return (T) this.resultObject;
	}
}
