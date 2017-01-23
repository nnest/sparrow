/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fulltext;

import java.math.BigDecimal;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.attrs.ExampleTextConjunction;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch;
import com.google.common.base.Strings;

/**
 * Common terms query
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class CommonTerms extends AbstractFullTextQuery<CommonTerms> {
	private String fieldName = null;
	private BigDecimal cutoffFrequency = null;
	private Boolean disableCoord = null;
	private MinimumShouldMatch highMinimumShouldMatch = null;
	private ExampleTextConjunction highConjunction = null;
	private MinimumShouldMatch lowMinimumShouldMatch = null;
	private ExampleTextConjunction lowConjunction = null;

	public CommonTerms(String exampleText) {
		super(exampleText);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.COMMON_TERMS;
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
	public CommonTerms withFieldName(String fieldName) {
		assert Strings.nullToEmpty(fieldName).trim().length() != 0 : "Field name cannot be null or blank.";

		this.fieldName = fieldName;
		return this;
	}

	/**
	 * @return the cutoffFrequency
	 */
	public BigDecimal getCutoffFrequency() {
		return cutoffFrequency;
	}

	/**
	 * @param cutoffFrequency
	 *            the cutoffFrequency to set
	 * @return this
	 */
	public CommonTerms withCutoffFrequency(BigDecimal cutoffFrequency) {
		assert cutoffFrequency != null : "Cutoff frequence cannot be null.";
		double v = cutoffFrequency.doubleValue();
		assert v >= 0 : "Cutoff frequence must in range [0, 1) or absolute if greater or equal to 1.0.";

		this.cutoffFrequency = cutoffFrequency;
		return this;
	}

	/**
	 * @return the lowMinimumShouldMatch
	 */
	public MinimumShouldMatch getLowMinimumShouldMatch() {
		return lowMinimumShouldMatch;
	}

	/**
	 * @param lowMinimumShouldMatch
	 *            the lowMinimumShouldMatch to set
	 * @return this
	 */
	public CommonTerms withLow(MinimumShouldMatch lowMinimumShouldMatch) {
		assert lowMinimumShouldMatch != null : "Minimum should match cannot be null.";

		this.lowMinimumShouldMatch = lowMinimumShouldMatch;
		return this;
	}

	/**
	 * @return the lowConjunction
	 */
	public ExampleTextConjunction getLowConjunction() {
		return lowConjunction;
	}

	/**
	 * @param lowConjunction
	 *            the lowConjunction to set
	 * @return this
	 */
	public CommonTerms withLow(ExampleTextConjunction lowConjunction) {
		assert lowConjunction != null : "Conjunction cannot be null.";

		this.lowConjunction = lowConjunction;
		return this;
	}

	/**
	 * @return the highMinimumShouldMatch
	 */
	public MinimumShouldMatch getHighMinimumShouldMatch() {
		return highMinimumShouldMatch;
	}

	/**
	 * @param highMinimumShouldMatch
	 *            the highMinimumShouldMatch to set
	 * @return this
	 */
	public CommonTerms withHigh(MinimumShouldMatch highMinimumShouldMatch) {
		assert highMinimumShouldMatch != null : "Minimum should match cannot be null.";

		this.highMinimumShouldMatch = highMinimumShouldMatch;
		return this;
	}

	/**
	 * @return the highConjunction
	 */
	public ExampleTextConjunction getHighConjunction() {
		return highConjunction;
	}

	/**
	 * @param highConjunction
	 *            the highConjunction to set
	 * @return this
	 */
	public CommonTerms withHigh(ExampleTextConjunction highConjunction) {
		assert highConjunction != null : "Conjunction cannot be null.";

		this.highConjunction = highConjunction;
		return this;
	}

	/**
	 * @return the disableCoord
	 */
	public Boolean getDisableCoord() {
		return disableCoord;
	}

	/**
	 * @param disableCoord
	 *            the disableCoord to set
	 * @return this
	 */
	public CommonTerms withDisableCoord(Boolean disableCoord) {
		assert disableCoord != null : "Disable coord cannot not be null.";

		this.disableCoord = disableCoord;
		return this;
	}
}
