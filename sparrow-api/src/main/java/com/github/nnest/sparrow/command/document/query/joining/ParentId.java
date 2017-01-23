/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.joining;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.google.common.base.Strings;

/**
 * Parent id query.<br>
 * {@linkplain type} is child type.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ParentId extends AbstractJoiningQuery<ParentId> {
	private String type = null;
	private String id = null;

	public ParentId(String type, String id) {
		this.withType(type).withId(id);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.PARENT_ID;
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
	 * @return this
	 */
	public ParentId withType(String type) {
		assert Strings.nullToEmpty(type).trim().length() != 0 : "Type cannot be null or empty.";

		this.type = type;
		return this;
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
	 * @return this
	 */
	public ParentId withId(String id) {
		assert Strings.nullToEmpty(id).trim().length() != 0 : "Id cannot be null or empty.";

		this.id = id;
		return this;
	}
}
