/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fulltext;

import com.github.nnest.sparrow.command.document.query.Example;
import com.google.common.base.Strings;

/**
 * abstract single match
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractSingleMatchText<T extends AbstractSingleMatchText<T>> extends AbstractMatchText<T> {
	private String fieldName = Example.ALL_FIELDS;

	public AbstractSingleMatchText(String exampleText) {
		super(exampleText);
	}

	/**
	 * get field name
	 * 
	 * @return field name
	 */
	public String getFieldName() {
		return this.fieldName;
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 * @return this;
	 */
	@SuppressWarnings("unchecked")
	public T withFieldName(String fieldName) {
		assert Strings.nullToEmpty(fieldName).trim().length() != 0 : "Field name cannot be null or blank.";

		this.fieldName = fieldName;
		return (T) this;
	}
}
