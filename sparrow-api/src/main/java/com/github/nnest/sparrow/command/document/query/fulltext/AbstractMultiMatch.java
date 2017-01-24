/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fulltext;

import java.math.BigDecimal;
import java.util.Set;

import com.github.nnest.sparrow.command.document.query.attrs.ExampleTextConjunction;
import com.github.nnest.sparrow.command.document.query.attrs.ZeroTermsQuery;
import com.github.nnest.sparrow.command.document.query.attrs.rewrite.Rewrite;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch;
import com.google.common.collect.Sets;

/**
 * abstract multiple match text.<br>
 * according to elastic search document {@code 5.1}, some attributes are not
 * comfortable for some multiple match type. but after testing, seems no syntax
 * exception raised by elastic search server. so these attributes are kept here.
 * for how to use them, see official document please.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractMultiMatch<T extends AbstractMultiMatch<T>> extends AbstractMatch<T> {
	private Set<String> fieldNames = null;
	private BigDecimal tieBreaker = null;

	private ExampleTextConjunction conjunction = null;
	private MinimumShouldMatch minimumShouldMatch = null;
	private ZeroTermsQuery zeroTermsQuery = null;
	private BigDecimal cutoffFrequency = null;
	private Boolean lenient = null;
	private Integer prefixLength = null;
	private Integer maxExpansions = null;
	private Rewrite rewrite = null;

	public AbstractMultiMatch(String exampleText) {
		super(exampleText);
	}

	/**
	 * @return the fieldNames
	 */
	public Set<String> getFieldNames() {
		return fieldNames;
	}

	/**
	 * @param fieldNames
	 *            the fieldNames to set
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T withFieldNames(Set<String> fieldNames) {
		assert fieldNames != null && fieldNames.size() != 0 : "Field names cannot be null or empty.";

		this.fieldNames = fieldNames;
		return (T) this;
	}

	/**
	 * with field names
	 * 
	 * @param fieldNames
	 *            field names
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T withFieldNames(String... fieldNames) {
		assert fieldNames != null && fieldNames.length != 0 : "Field names cannot be null or empty.";

		this.fieldNames = Sets.newHashSet(fieldNames);
		return (T) this;
	}

	/**
	 * @return the tieBreaker
	 */
	public BigDecimal getTieBreaker() {
		return tieBreaker;
	}

	/**
	 * @param tieBreaker
	 *            the tieBreaker to set
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T withTieBreaker(BigDecimal tieBreaker) {
		assert tieBreaker != null : "Tie breaker cannot be null.";
		double v = tieBreaker.doubleValue();
		assert v >= 0 && v <= 1 : "Tie breaker must in range of [0, 1]";

		this.tieBreaker = tieBreaker;
		return (T) this;
	}

	/**
	 * @return the conjunction
	 */
	public ExampleTextConjunction getConjunction() {
		return conjunction;
	}

	/**
	 * @param conjunction
	 *            the Conjunction to set
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T with(ExampleTextConjunction conjunction) {
		assert conjunction != null : "Conjunction cannot be null.";

		this.conjunction = conjunction;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T with(MinimumShouldMatch minimumShouldMatch) {
		assert minimumShouldMatch != null : "Minimum should match cannot be null.";

		this.minimumShouldMatch = minimumShouldMatch;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T with(ZeroTermsQuery zeroTermsQuery) {
		assert zeroTermsQuery != null : "Zero terms query cannot be null.";

		this.zeroTermsQuery = zeroTermsQuery;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T withCutoffFrequency(BigDecimal cutoffFrequency) {
		assert cutoffFrequency != null : "Cutoff frequence cannot be null.";
		double v = cutoffFrequency.doubleValue();
		assert v >= 0 : "Cutoff frequence must in range [0, 1) or absolute if greater or equal to 1.0.";

		this.cutoffFrequency = cutoffFrequency;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T withLenient(Boolean lenient) {
		assert lenient != null : "Lenient cannot be null";

		this.lenient = lenient;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T withPrefixLength(Integer prefixLength) {
		assert prefixLength != null
				&& prefixLength >= 0 : "Prefix length cannot be null, and must be zero or positive.";

		this.prefixLength = prefixLength;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T withMaxExpansions(Integer maxExpansions) {
		assert maxExpansions != null
				&& maxExpansions >= 0 : "Max expansions cannot be null, and must be zero or positive.";

		this.maxExpansions = maxExpansions;
		return (T) this;
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
	@SuppressWarnings("unchecked")
	public T with(Rewrite rewrite) {
		assert rewrite != null : "Rewrite cannot be null.";

		this.rewrite = rewrite;
		return (T) this;
	}
}
