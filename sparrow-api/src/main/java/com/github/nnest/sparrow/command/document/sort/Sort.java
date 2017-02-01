/**
 * 
 */
package com.github.nnest.sparrow.command.document.sort;

import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.type.DataType;
import com.google.common.base.Strings;

/**
 * Sort.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Sort {
	public final static String BY_SCORE = "_score";
	public final static String MISSING_VALUE_LAST = "_last";
	public final static String MISSING_VALUE_FIRST = "_first";

	private SortBy by = null;
	private SortOrder order = null;
	private SortMode mode = null;
	private String nestedPath = null;
	private Example nestedFilter = null;
	private String missingValue = null;
	private DataType unmappedType = null;

	public Sort(SortBy by) {
		this.with(by);
	}

	/**
	 * @return the by
	 */
	public SortBy getBy() {
		return by;
	}

	/**
	 * @param by
	 *            the by to set
	 * @return this
	 */
	public Sort with(SortBy by) {
		assert by != null : "Sort by cannot be null.";

		this.by = by;
		return this;
	}

	/**
	 * @return the order
	 */
	public SortOrder getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 * @return this
	 */
	public Sort with(SortOrder order) {
		assert order != null : "Sort order cannot be null.";

		this.order = order;
		return this;
	}

	/**
	 * @return the mode
	 */
	public SortMode getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 * @return this
	 */
	public Sort with(SortMode mode) {
		assert mode != null : "Sort mode cannot be null.";

		this.mode = mode;
		return this;
	}

	/**
	 * @return the nestedPath
	 */
	public String getNestedPath() {
		return nestedPath;
	}

	/**
	 * @param nestedPath
	 *            the nestedPath to set
	 * @return this
	 */
	public Sort withNestedPath(String nestedPath) {
		assert Strings.nullToEmpty(nestedPath).trim().length() != 0 : "Nested path cannot be null or blank.";

		this.nestedPath = nestedPath;
		return this;
	}

	/**
	 * @return the nestedFilter
	 */
	public Example getNestedFilter() {
		return nestedFilter;
	}

	/**
	 * @param nestedFilter
	 *            the nestedFilter to set
	 * @return this
	 */
	public Sort with(Example nestedFilter) {
		assert nestedFilter != null : "Nested filter cannot be null.";

		this.nestedFilter = nestedFilter;
		return this;
	}

	/**
	 * @return the missingValue
	 */
	public String getMissingValue() {
		return missingValue;
	}

	/**
	 * get missing value, should be {@linkplain #MISSING_VALUE_FIRST} or
	 * {@linkplain #MISSING_VALUE_LAST} or any other custom value.
	 * 
	 * @param missingValue
	 *            the missingValue to set
	 * @return this
	 * @see #MISSING_VALUE_FIRST
	 * @see #MISSING_VALUE_LAST
	 */
	public Sort withMissingValue(String missingValue) {
		assert missingValue != null : "Missing value cannot be null.";

		this.missingValue = missingValue;
		return this;
	}

	/**
	 * @return the unmappedType
	 */
	public DataType getUnmappedType() {
		return unmappedType;
	}

	/**
	 * @param unmappedType
	 *            the unmappedType to set
	 * @return this
	 */
	public Sort with(DataType unmappedType) {
		assert unmappedType != null : "Unmapped type cannot be null.";

		this.unmappedType = unmappedType;
		return this;
	}
}
