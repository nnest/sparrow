/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.attrs.ExampleTextConjunction;
import com.github.nnest.sparrow.command.document.query.attrs.ZeroTermsQuery;
import com.github.nnest.sparrow.command.document.query.attrs.fuzzy.Fuzziness;
import com.github.nnest.sparrow.command.document.query.attrs.rewrite.Rewrite;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch;
import com.github.nnest.sparrow.command.document.query.fulltext.AbstractMultiMatch;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ExampleTextConjunctionSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ExampleTypeSeralizer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.FuzzinessSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.MinimumShouldMatchSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.RewriteSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ZeroTermsQuerySerializer;

/**
 * best fields match mixin, see {@linkplain AbstractMultiMatch}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@JsonInclude(Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public interface MultiMatchMixin {
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
	@JsonProperty("operator")
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
	@JsonProperty("type")
	@JsonSerialize(using = ExampleTypeSeralizer.class)
	ExampleType getExampleType();

	/**
	 * get field names
	 * 
	 * @return field names
	 */
	@JsonProperty("fields")
	Set<String> getFieldNames();

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

	/**
	 * get zero terms query
	 * 
	 * @return zero terms query
	 */
	@JsonSerialize(using = ZeroTermsQuerySerializer.class)
	ZeroTermsQuery getZeroTermsQuery();
}
