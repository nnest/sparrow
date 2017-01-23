/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.term;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.google.common.base.Strings;

/**
 * range term
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Range extends AbstractTermLevelQuery<Range> {
	private String max = null;
	private String min = null;
	private boolean maxInclude = false;
	private boolean minInclude = false;
	private String format = null;
	private String timeZone = null;

	public Range(String fieldName) {
		super(fieldName);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.RANGE;
	}

	/**
	 * @return the max
	 */
	public String getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 * @return this
	 */
	public Range withMax(String max) {
		assert Strings.nullToEmpty(max).trim().length() != 0 : "Max value cannot be null or blank.";

		this.max = max;
		return this;
	}

	/**
	 * 
	 * @param max
	 *            the max to set
	 * @param include
	 *            max value is included or not
	 * @return this
	 */
	public Range withMax(String max, boolean include) {
		this.maxInclude = include;
		return this.withMax(max);
	}

	/**
	 * @return the min
	 */
	public String getMin() {
		return min;
	}

	/**
	 * @param min
	 *            the min to set
	 * @return this
	 */
	public Range withMin(String min) {
		assert Strings.nullToEmpty(min).trim().length() != 0 : "Min value cannot be null or blank.";

		this.min = min;
		return this;
	}

	/**
	 * 
	 * @param min
	 *            the min to set
	 * @param include
	 *            min value is included or not
	 * @return this
	 */
	public Range withMin(String min, boolean include) {
		this.minInclude = include;
		return this.withMin(min);
	}

	/**
	 * @return the maxInclude
	 */
	public boolean isMaxInclude() {
		return maxInclude;
	}

	/**
	 * @return the minInclude
	 */
	public boolean isMinInclude() {
		return minInclude;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            the format to set
	 * @return this
	 */
	public Range withFormat(String format) {
		assert Strings.nullToEmpty(format).trim().length() != 0 : "Format cannot be null or blank.";

		this.format = format;
		return this;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone
	 *            the timeZone to set
	 * @return this
	 */
	public Range withTimeZone(String timeZone) {
		assert Strings.nullToEmpty(timeZone).trim().length() != 0 : "Time zone cannot be null or blank.";

		this.timeZone = timeZone;
		return this;
	}
}
