/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nnest.sparrow.command.document.DeleteResultData;

/**
 * Delete response
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DeleteResponse implements DeleteResultData {
	@JsonProperty("_shards")
	private RestResponseShards shards = null;
	@JsonProperty("_index")
	private String index = null;
	@JsonProperty("_type")
	private String type = null;
	@JsonProperty("_id")
	private String id = null;
	@JsonProperty("_version")
	private String version = null;
	private boolean found = false;
	private String result = null;

	/**
	 * @return the shards
	 */
	public RestResponseShards getShards() {
		return shards;
	}

	/**
	 * @param shards
	 *            the shards to set
	 */
	public void setShards(RestResponseShards shards) {
		this.shards = shards;
	}

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the found
	 */
	public boolean isFound() {
		return found;
	}

	/**
	 * @param found
	 *            the found to set
	 */
	public void setFound(boolean found) {
		this.found = found;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandResultData#isSuccessful()
	 */
	@Override
	public boolean isSuccessful() {
		return this.isFound() && "deleted".equals(this.getResult());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.DeleteResultData#isDeleted()
	 */
	@Override
	public boolean isDeleted() {
		return this.isSuccessful();
	}
}
