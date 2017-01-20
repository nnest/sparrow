/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

import java.math.BigDecimal;

import com.github.nnest.sparrow.command.document.query.fuzzy.Fuzziness;
import com.github.nnest.sparrow.command.document.query.shouldmatch.MinimumShouldMatch;

/**
 * Match example<br>
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Match extends AbstractMatch<Match> {
	private ExampleTextConjunction conjuction = null;
	private MinimumShouldMatch minimumShouldMatch = null;
	private Fuzziness fuzziness = null;
	private ZeroTermsQuery zeroTermsQuery = null;
	private BigDecimal cutoffFrequency = null;
	private Boolean lenient = null;

	public Match(String exampleText) {
		super(exampleText);
	}

	/**
	 * @return the conjuction
	 */
	public ExampleTextConjunction getConjuction() {
		return conjuction;
	}

	/**
	 * @param conjuction
	 *            the conjuction to set
	 * @return this
	 */
	protected Match with(ExampleTextConjunction conjuction) {
		assert conjuction != null : "Conjuction cannot be null.";

		this.conjuction = conjuction;
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
	protected Match with(MinimumShouldMatch minimumShouldMatch) {
		assert minimumShouldMatch != null : "Minimum should match cannot be null.";

		this.minimumShouldMatch = minimumShouldMatch;
		return this;
	}

	/**
	 * @return the fuzziness
	 */
	public Fuzziness getFuzziness() {
		return fuzziness;
	}

	/**
	 * @param fuzziness
	 *            the fuzziness to set
	 * @return this
	 */
	protected Match with(Fuzziness fuzziness) {
		assert fuzziness != null : "Fuzziness cannot be null.";

		this.fuzziness = fuzziness;
		return this;
	}

	/**
	 * @return the zeroTermsQuery
	 */
	public ZeroTermsQuery getZeroTermsQuery() {
		return zeroTermsQuery;
	}

	/**
	 * @param zeroTermsQuery
	 *            the zeroTermsQuery to set
	 * @return this
	 */
	protected Match with(ZeroTermsQuery zeroTermsQuery) {
		assert zeroTermsQuery != null : "Zero terms query cannot be null.";

		this.zeroTermsQuery = zeroTermsQuery;
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
	protected Match withCutoffFrequency(BigDecimal cutoffFrequency) {
		assert cutoffFrequency != null : "Cutoff frequence cannot be null.";
		double v = cutoffFrequency.doubleValue();
		assert v >= 0 : "Cutoff frequence must in range [0, 1) or absolute if greater or equal to 1.0.";

		this.cutoffFrequency = cutoffFrequency;
		return this;
	}

	/**
	 * @return the lenient
	 */
	public Boolean getLenient() {
		return lenient;
	}

	/**
	 * @param lenient
	 *            the lenient to set
	 */
	public void setLenient(Boolean lenient) {
		this.lenient = lenient;
	}
}
