/**
 * 
 */
package com.github.nnest.sparrow.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * index response
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class IndexResponse implements RestResponseObject {
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
	private boolean created = false;
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
	 * @return the created
	 */
	public boolean isCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(boolean created) {
		this.created = created;
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
}
