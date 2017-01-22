/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

import java.util.Set;

import com.github.nnest.sparrow.command.document.query.shouldmatch.MinimumShouldMatch;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

/**
 * simple query string.<br>
 * the {@linkplain #allFields} cannot set when {@linkplain #fieldNames} has
 * values.<br>
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class SimpleQueryString extends AbstractFullTextQuery<SimpleQueryString> {
	private Set<String> fieldNames = null;
	private ExampleTextConjunction conjunction = null;
	private Boolean analyzeWildcard = null;
	private MinimumShouldMatch minimumShouldMatch = null;
	private Boolean lenient = null;
	private String quoteFieldSuffix = null;
	private Boolean allFields = null;
	private Set<ParseFeatureFlag> flags = null;

	public SimpleQueryString(String exampleText) {
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
	public SimpleQueryString withFieldNames(Set<String> fieldNames) {
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
	public SimpleQueryString withFieldNames(String... fieldNames) {
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
	public SimpleQueryString with(ExampleTextConjunction conjunction) {
		assert conjunction != null : "Conjunction cannot be null.";

		this.conjunction = conjunction;
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
	public SimpleQueryString withAnalyzeWildcard(Boolean analyzeWildcard) {
		assert analyzeWildcard != null : "Analyze wildcard cannot be null.";

		this.analyzeWildcard = analyzeWildcard;
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
	public SimpleQueryString with(MinimumShouldMatch minimumShouldMatch) {
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
	public SimpleQueryString withLenient(Boolean lenient) {
		assert lenient != null : "Lenient cannot be null.";

		this.lenient = lenient;
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
	public SimpleQueryString withQuoteFieldSuffix(String quoteFieldSuffix) {
		assert Strings.nullToEmpty(quoteFieldSuffix).trim()
				.length() != 0 : "Quote field suffix cannot be null or blank.";

		this.quoteFieldSuffix = quoteFieldSuffix;
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
	public SimpleQueryString withAllFields(Boolean allFields) {
		assert allFields != null : "All fields cannot be null.";

		this.allFields = allFields;
		return this;
	}

	/**
	 * @return the flags
	 */
	public Set<ParseFeatureFlag> getFlags() {
		return flags;
	}

	/**
	 * @param flags
	 *            the flags to set
	 * @return this
	 */
	public SimpleQueryString withFlags(Set<ParseFeatureFlag> flags) {
		assert flags != null && flags.size() > 0 : "Flags cannot be null or empty.";

		this.flags = flags;
		return this;
	}

	/**
	 * @param flags
	 *            the flags to set
	 * @return this
	 */
	public SimpleQueryString withFlags(ParseFeatureFlag... flags) {
		assert flags != null && flags.length > 0 : "Flags cannot be null or empty.";

		this.flags = Sets.newHashSet(flags);
		return this;
	}
}
