/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import com.github.nnest.sparrow.command.document.query.fulltext.AbstractSingleMatch;
import com.github.nnest.sparrow.command.document.query.fulltext.Match;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ExampleTextConjunctionSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.FuzzinessSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.MinimumShouldMatchSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.RewriteSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ZeroTermsQuerySerializer;

/**
 * single match mixin. all methods signature are same as them in
 * {@linkplain AbstractSingleMatch}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 * @see Match
 */
@JsonInclude(Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public interface SingleMatchMixin {
	/**
	 * get analyzer name
	 * 
	 * @return
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
	 * @return
	 */
	@JsonProperty("query")
	String getExampleText();

	/**
	 * get example type
	 * 
	 * @return
	 */
	@JsonIgnore
	ExampleType getExampleType();

	/**
	 * get field name
	 * 
	 * @return
	 */
	@JsonIgnore
	String getFieldName();

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
	 * get transpositions
	 * 
	 * @return transpositions
	 */
	@JsonProperty("fuzzy_transpositions")
	Boolean getTranspositions();

	/**
	 * get zero terms query
	 * 
	 * @return zero terms query
	 */
	@JsonSerialize(using = ZeroTermsQuerySerializer.class)
	ZeroTermsQuery getZeroTermsQuery();
}
