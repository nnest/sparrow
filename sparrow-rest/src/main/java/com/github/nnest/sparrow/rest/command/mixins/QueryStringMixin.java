/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.attrs.ExampleTextConjunction;
import com.github.nnest.sparrow.command.document.query.attrs.ParseFeatureFlag;
import com.github.nnest.sparrow.command.document.query.attrs.fuzzy.Fuzziness;
import com.github.nnest.sparrow.command.document.query.attrs.rewrite.Rewrite;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch;
import com.github.nnest.sparrow.command.document.query.fulltext.QueryString;
import com.github.nnest.sparrow.command.document.query.fulltext.SimpleQueryString;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ExampleTextConjunctionSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.FuzzinessSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.MinimumShouldMatchSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ParseFeatureFlagsSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.RewriteSerializer;

/**
 * query string mixin, see {@linkplain QueryString} and
 * {@linkplain SimpleQueryString}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@JsonInclude(Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public interface QueryStringMixin {
	/**
	 * get analyzer name
	 * 
	 * @return analyzer name
	 */
	@JsonProperty("analyzer")
	String getAnalyzerName();

	/**
	 * get example text conjunction
	 * 
	 * @return conjunction
	 */
	@JsonProperty("default_operator")
	@JsonSerialize(using = ExampleTextConjunctionSerializer.class)
	ExampleTextConjunction getConjunction();

	/**
	 * get example text
	 * 
	 * @return example text
	 */
	@JsonProperty("query")
	String getExampleText();

	/**
	 * get example type
	 * 
	 * @return example type
	 */
	@JsonIgnore
	ExampleType getExampleType();

	/**
	 * get field names
	 * 
	 * @return field names
	 */
	@JsonProperty("fields")
	Set<String> getFieldNames();

	/**
	 * get flags
	 * 
	 * @return flags
	 */
	@JsonSerialize(using = ParseFeatureFlagsSerializer.class)
	Set<ParseFeatureFlag> getFlags();

	/**
	 * get fuzziness
	 * 
	 * @return fuzziness
	 */
	@JsonSerialize(using = FuzzinessSerializer.class)
	Fuzziness getFuzziness();

	/**
	 * get minimum should match
	 * 
	 * @return minimum should match
	 */
	@JsonSerialize(using = MinimumShouldMatchSerializer.class)
	MinimumShouldMatch getMinimumShouldMatch();

	/**
	 * get rewrite
	 * 
	 * @return rewrite
	 */
	@JsonProperty("fuzzy_rewrite")
	@JsonSerialize(using = RewriteSerializer.class)
	Rewrite getRewrite();
}
