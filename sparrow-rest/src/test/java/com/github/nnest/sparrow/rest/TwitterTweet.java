/**
 * 
 */
package com.github.nnest.sparrow.rest;

import com.github.nnest.sparrow.annotation.ElasticDocument;
import com.github.nnest.sparrow.annotation.ElasticId;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@ElasticDocument(index = "twitter", type = "tweet")
public class TwitterTweet {
	@ElasticId
	private String id = null;
	private String user = null;
	private String postDate = null;
	private String message = null;

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
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the postDate
	 */
	public String getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate
	 *            the postDate to set
	 */
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
