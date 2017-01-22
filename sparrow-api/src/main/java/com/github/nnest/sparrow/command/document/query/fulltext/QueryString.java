/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fulltext;

import java.util.Set;

import com.github.nnest.sparrow.command.document.query.attrs.ExampleTextConjunction;
import com.github.nnest.sparrow.command.document.query.attrs.fuzzy.Fuzziness;
import com.github.nnest.sparrow.command.document.query.attrs.rewrite.Rewrite;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

/**
 * query string.<br>
 * the {@linkplain #allFields} cannot set when {@linkplain #fieldNames} has
 * values.<br>
 * the {@linkplain #timeZone} is using <a href=
 * "http://www.joda.org/joda-time/apidocs/org/joda/time/DateTimeZone.html">JODA
 * Timezone</a><br>
 * <strong>Note:</strong><br>
 * after test, the {@linkplain #maxDeterminizedStates} is not supported by
 * 5.1.1, but according to the official documents, it is supported. so the
 * attribute is kept in definition, but verify the elastic search already fix
 * this issue before use it. see <a href=
 * "https://github.com/elastic/elasticsearch/issues/22722">https://github.com/elastic/elasticsearch/issues/22722</a>
 * for more information.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class QueryString extends AbstractFullTextQuery<QueryString> {
	private Set<String> fieldNames = null;
	private ExampleTextConjunction conjunction = null;
	private Boolean allowLeadingWildcard = null;
	private Boolean enablePositionIncrement = null;
	private Integer fuzzyMaxExpansions = null;
	private Fuzziness fuzziness = null;
	private Integer fuzzyPrefixLength = null;
	private Integer phraseSlop = null;
	private Boolean autoGeneratePhraseQueries = null;
	private Boolean analyzeWildcard = null;
	private Integer maxDeterminizedStates = null;
	private MinimumShouldMatch minimumShouldMatch = null;
	private Boolean lenient = null;
	private String timeZone = null;
	private String quoteFieldSuffix = null;
	private Boolean splitOnWhitespace = null;
	private Boolean allFields = null;
	private Rewrite rewrite = null;

	public QueryString(String exampleText) {
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
	public QueryString withFieldNames(Set<String> fieldNames) {
		assert fieldNames != null && fieldNames.size() != 0 : "Field names cannot be null or empty.";

		this.fieldNames = fieldNames;
		return this;
	}

	/**
	 * with field names
	 * 
	 * @param fieldNames
	 *            field names
	 * @return this
	 */
	public QueryString withFieldNames(String... fieldNames) {
		assert fieldNames != null && fieldNames.length != 0 : "Field names cannot be null or empty.";

		this.fieldNames = Sets.newHashSet(fieldNames);
		return this;
	}

	/**
	 * @return the conjunction
	 */
	public ExampleTextConjunction getConjunction() {
		return conjunction;
	}

	/**
	 * @param conjunction
	 *            the conjunction to set
	 * @return this
	 */
	public QueryString with(ExampleTextConjunction conjunction) {
		assert conjunction != null : "Conjunction cannot be null.";

		this.conjunction = conjunction;
		return this;
	}

	/**
	 * @return the allowLeadingWildcard
	 */
	public Boolean getAllowLeadingWildcard() {
		return allowLeadingWildcard;
	}

	/**
	 * @param allowLeadingWildcard
	 *            the allowLeadingWildcard to set
	 * @return this
	 */
	public QueryString withAllowLeadingWildcard(Boolean allowLeadingWildcard) {
		assert allowLeadingWildcard != null : "Allow leading wildcard cannot be null.";

		this.allowLeadingWildcard = allowLeadingWildcard;
		return this;
	}

	/**
	 * @return the enablePositionIncrement
	 */
	public Boolean getEnablePositionIncrement() {
		return enablePositionIncrement;
	}

	/**
	 * @param enablePositionIncrement
	 *            the enablePositionIncrement to set
	 * @return this
	 */
	public QueryString withEnablePositionIncrement(Boolean enablePositionIncrement) {
		assert enablePositionIncrement != null : "Enable position increments cannot be null.";

		this.enablePositionIncrement = enablePositionIncrement;
		return this;
	}

	/**
	 * @return the fuzzyMaxExpansions
	 */
	public Integer getFuzzyMaxExpansions() {
		return fuzzyMaxExpansions;
	}

	/**
	 * @param fuzzyMaxExpansions
	 *            the fuzzyMaxExpansions to set
	 * @return this
	 */
	public QueryString withFuzzyMaxExpansions(Integer fuzzyMaxExpansions) {
		assert fuzzyMaxExpansions != null
				&& fuzzyMaxExpansions >= 0 : "Fuzzy max expansions cannot be null, and must be zero or positive.";

		this.fuzzyMaxExpansions = fuzzyMaxExpansions;
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
	public QueryString with(Fuzziness fuzziness) {
		assert fuzziness != null : "Fuzziness cannot be null.";

		this.fuzziness = fuzziness;
		return this;
	}

	/**
	 * @return the fuzzyPrefixLength
	 */
	public Integer getFuzzyPrefixLength() {
		return fuzzyPrefixLength;
	}

	/**
	 * @param fuzzyPrefixLength
	 *            the fuzzyPrefixLength to set
	 * @return this
	 */
	public QueryString withFuzzyPrefixLength(Integer fuzzyPrefixLength) {
		assert fuzzyPrefixLength != null
				&& fuzzyPrefixLength >= 0 : "Fuzzy prefix length cannot be null, and must be zero or positive.";

		this.fuzzyPrefixLength = fuzzyPrefixLength;
		return this;
	}

	/**
	 * @return the phraseSlop
	 */
	public Integer getPhraseSlop() {
		return phraseSlop;
	}

	/**
	 * @param phraseSlop
	 *            the phraseSlop to set
	 * @return this
	 */
	public QueryString withPhraseSlop(Integer phraseSlop) {
		assert phraseSlop != null && phraseSlop > 0 : "Phrase slop cannot be null, and must be positive.";

		this.phraseSlop = phraseSlop;
		return this;
	}

	/**
	 * @return the autoGeneratePhraseQueries
	 */
	public Boolean getAutoGeneratePhraseQueries() {
		return autoGeneratePhraseQueries;
	}

	/**
	 * @param autoGeneratePhraseQueries
	 *            the autoGeneratePhraseQueries to set
	 * @return this
	 */
	public QueryString withAutoGeneratePhraseQueries(Boolean autoGeneratePhraseQueries) {
		assert autoGeneratePhraseQueries != null : "Auto generate phrase queries cannot be null.";

		this.autoGeneratePhraseQueries = autoGeneratePhraseQueries;
		return this;
	}

	/**
	 * @return the analyzeWildcard
	 */
	public Boolean getAnalyzeWildcard() {
		return analyzeWildcard;
	}

	/**
	 * @param analyzeWildcard
	 *            the analyzeWildcard to set
	 * @return this
	 */
	public QueryString withAnalyzeWildcard(Boolean analyzeWildcard) {
		assert analyzeWildcard != null : "Analyze wildcard cannot be null.";

		this.analyzeWildcard = analyzeWildcard;
		return this;
	}

	/**
	 * @return the maxDeterminizedStates
	 */
	public Integer getMaxDeterminizedStates() {
		return maxDeterminizedStates;
	}

	/**
	 * @param maxDeterminizedStates
	 *            the maxDeterminizedStates to set
	 * @return this
	 */
	public QueryString withMaxDeterminizedStates(Integer maxDeterminizedStates) {
		assert maxDeterminizedStates != null
				&& maxDeterminizedStates >= 0 : "Max determinized states cannot be null, and must be zero or positive.";

		this.maxDeterminizedStates = maxDeterminizedStates;
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
	 *            the minimumShouldMatch to set
	 * @return this
	 */
	public QueryString with(MinimumShouldMatch minimumShouldMatch) {
		assert minimumShouldMatch != null : "Minimum should match cannot be null.";

		this.minimumShouldMatch = minimumShouldMatch;
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
	public QueryString withLenient(Boolean lenient) {
		assert lenient != null : "Lenient cannot be null.";

		this.lenient = lenient;
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
	public QueryString withTimeZone(String timeZone) {
		assert Strings.nullToEmpty(timeZone).trim().length() != 0 : "Time zone cannot be null or blank.";

		this.timeZone = timeZone;
		return this;
	}

	/**
	 * @return the quoteFieldSuffix
	 */
	public String getQuoteFieldSuffix() {
		return quoteFieldSuffix;
	}

	/**
	 * @param quoteFieldSuffix
	 *            the quoteFieldSuffix to set
	 * @return this
	 */
	public QueryString withQuoteFieldSuffix(String quoteFieldSuffix) {
		assert Strings.nullToEmpty(quoteFieldSuffix).trim()
				.length() != 0 : "Quote field suffix cannot be null or blank.";

		this.quoteFieldSuffix = quoteFieldSuffix;
		return this;
	}

	/**
	 * @return the splitOnWhitespace
	 */
	public Boolean getSplitOnWhitespace() {
		return splitOnWhitespace;
	}

	/**
	 * @param splitOnWhitespace
	 *            the splitOnWhitespace to set
	 * @return this
	 */
	public QueryString withSplitOnWhitespace(Boolean splitOnWhitespace) {
		assert splitOnWhitespace != null : "Split on whitespace cannot be null.";

		this.splitOnWhitespace = splitOnWhitespace;
		return this;
	}

	/**
	 * @return the allFields
	 */
	public Boolean getAllFields() {
		return allFields;
	}

	/**
	 * @param allFields
	 *            the allFields to set
	 * @return this
	 */
	public QueryString withAllFields(Boolean allFields) {
		assert allFields != null : "All fields cannot be null.";

		this.allFields = allFields;
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
	public QueryString with(Rewrite rewrite) {
		assert rewrite != null : "Rewrite cannot be null.";

		this.rewrite = rewrite;
		return this;
	}
}
