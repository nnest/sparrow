/**
 * 
 */
package com.github.nnest.sparrow.command.document.sort;

import com.google.common.base.Strings;

/**
 * sort by field
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class SortByField implements SortBy {
	private String fieldName = null;

	public SortByField(String fieldName) {
		this.withFieldName(fieldName);
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 * @return this
	 */
	public SortByField withFieldName(String fieldName) {
		assert Strings.nullToEmpty(fieldName).trim().length() != 0 : "Field name cannot be null or blank.";

		this.fieldName = fieldName;
		return this;
	}

	/**
	 * value of
	 * 
	 * @param fieldName
	 *            field name
	 * @return sort by
	 */
	public static SortByField valueOf(String fieldName) {
		return new SortByField(fieldName);
	}
}
