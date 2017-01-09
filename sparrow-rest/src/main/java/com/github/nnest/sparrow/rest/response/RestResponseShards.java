/**
 * 
 */
package com.github.nnest.sparrow.rest.response;

/**
 * Rest response shards part
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestResponseShards {
	private Integer total = null;
	private Integer failed = null;
	private Integer successful = null;

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the failed
	 */
	public Integer getFailed() {
		return failed;
	}

	/**
	 * @param failed
	 *            the failed to set
	 */
	public void setFailed(Integer failed) {
		this.failed = failed;
	}

	/**
	 * @return the successful
	 */
	public Integer getSuccessful() {
		return successful;
	}

	/**
	 * @param successful
	 *            the successful to set
	 */
	public void setSuccessful(Integer successful) {
		this.successful = successful;
	}
}
