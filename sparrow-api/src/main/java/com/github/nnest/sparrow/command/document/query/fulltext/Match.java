/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fulltext;

import java.math.BigDecimal;

import com.github.nnest.sparrow.command.document.query.attrs.ExampleTextConjunction;
import com.github.nnest.sparrow.command.document.query.attrs.ZeroTermsQuery;
import com.github.nnest.sparrow.command.document.query.attrs.fuzzy.Fuzziness;
import com.github.nnest.sparrow.command.document.query.attrs.rewrite.Rewrite;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch;

/**
 * Match basic, in elastic search, the {@code match} api.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Match extends AbstractSingleMatch<Match> {
	private ExampleTextConjunction conjuction = null;
	private MinimumShouldMatch minimumShouldMatch = null;
	private Fuzziness fuzziness = null;
	private ZeroTermsQuery zeroTermsQuery = null;
	private BigDecimal cutoffFrequency = null;
	private Boolean lenient = null;
	private Integer prefixLength = null;
	private Integer maxExpansions = null;
	private Rewrite rewrite = null;
	private Boolean transpositions = null;

	public Match(String exampleText) {
		super(exampleText);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.fulltext.AbstractMatch#getType()
	 */
	@Override
	public MatchType getType() {
		return MatchType.SINGLE_MATCH;
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
	public Match with(ExampleTextConjunction conjuction) {
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
	public Match with(MinimumShouldMatch minimumShouldMatch) {
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
	public Match with(Fuzziness fuzziness) {
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
	public Match with(ZeroTermsQuery zeroTermsQuery) {
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
	public Match withCutoffFrequency(BigDecimal cutoffFrequency) {
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
	 * @return this
	 */
	public Match withLenient(Boolean lenient) {
		assert lenient != null : "Lenient cannot be null";

		this.lenient = lenient;
		return this;
	}

	/**
	 * @return the prefixLength
	 */
	public Integer getPrefixLength() {
		return prefixLength;
	}

	/**
	 * @param prefixLength
	 *            the prefixLength to set
	 * @return this
	 */
	public Match withPrefixLength(Integer prefixLength) {
		assert prefixLength != null
				&& prefixLength >= 0 : "Prefix length cannot be null, and must be zero or positive.";

		this.prefixLength = prefixLength;
		return this;
	}

	/**
	 * @return the maxExpansions
	 */
	public Integer getMaxExpansions() {
		return maxExpansions;
	}

	/**
	 * @param maxExpansions
	 *            the maxExpansions to set
	 * @return this
	 */
	public Match withMaxExpansions(Integer maxExpansions) {
		assert maxExpansions != null
				&& maxExpansions >= 0 : "Max expansions cannot be null, and must be zero or positive.";

		this.maxExpansions = maxExpansions;
		return this;
	}

	/**
	 * @return the rewrite
	 */
	public Rewrite getRewrite() {
		return rewrite;
	}

	/**
	 * @param rewrite
	 *            the rewrite to set
	 * @return this
	 */
	public Match with(Rewrite rewrite) {
		assert rewrite != null : "Rewrite cannot be null.";

		this.rewrite = rewrite;
		return this;
	}

	/**
	 * @return the transpositions
	 */
	public Boolean getTranspositions() {
		return transpositions;
	}

	/**
	 * @param transpositions
	 *            the transpositions to set
	 * @return this
	 */
	public Match withTranspositions(Boolean transpositions) {
		assert transpositions != null : "Transpositions cannot be null.";

		this.transpositions = transpositions;
		return this;
	}
}
