/**
 * 
 */
package com.github.nnest.sparrow.simple;

import java.util.Date;
import java.util.List;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class TwitterTweet {
	private Long id = null;
	private String user = null;
	private Date postDate = null;
	private String message = null;
	private List<Topic> topic = null;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
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
	public Date getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate
	 *            the postDate to set
	 */
	public void setPostDate(Date postDate) {
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

	/**
	 * @return the topic
	 */
	public List<Topic> getTopic() {
		return topic;
	}

	/**
	 * @param topic
	 *            the topic to set
	 */
	public void setTopic(List<Topic> topic) {
		this.topic = topic;
	}

	public static class Topic {
		private String id = null;
		private Boolean liked = null;

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
		 * @return the liked
		 */
		public Boolean getLiked() {
			return liked;
		}

		/**
		 * @param liked
		 *            the liked to set
		 */
		public void setLiked(Boolean liked) {
			this.liked = liked;
		}
	}
}
