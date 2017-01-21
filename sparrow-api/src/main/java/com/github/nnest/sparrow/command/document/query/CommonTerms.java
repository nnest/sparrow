/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

import java.math.BigDecimal;

import com.github.nnest.sparrow.command.document.query.shouldmatch.MinimumShouldMatch;

/**
 * Common terms query
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class CommonTerms extends AbstractFullTextQuery<CommonTerms> {
	private BigDecimal cutoffFrequency = null;
	private MinimumShouldMatch minimumShouldMatch = null;
	private Boolean disableCoord = null;

	public CommonTerms(String exampleText) {
		super(exampleText);
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
	 * @return the minimumShouldMatch
	 */
	public MinimumShouldMatch getMinimumShouldMatch() {
		return minimumShouldMatch;
	}

	/**
	 * @param minimumShouldMatch
	 *            the miuimumShouldMatch to set
	 * @return this
	 */
	public CommonTerms with(MinimumShouldMatch minimumShouldMatch) {
		assert minimumShouldMatch != null : "Minimum should match cannot be null.";

		this.minimumShouldMatch = minimumShouldMatch;
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
