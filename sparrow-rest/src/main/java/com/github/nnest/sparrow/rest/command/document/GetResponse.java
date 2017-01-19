/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import com.github.nnest.sparrow.command.document.GetResultData;

/**
 * Get response
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class GetResponse implements GetResultData {
	private String index = null;
	private String type = null;
	private String id = null;
	private String version = null;
	private boolean found = true;
	private Object document = null;

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
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandResultData#isSuccessful()
	 */
	@Override
	public boolean isSuccessful() {
		return this.isFound();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.GetResultData#getDocument()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getDocument() {
		return (T) this.document;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setJsonNode(Object document) {
		this.document = document;
	}
}
