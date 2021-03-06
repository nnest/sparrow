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
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch;
import com.github.nnest.sparrow.command.document.query.fulltext.CommonTerms;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ExampleTextConjunctionSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier;

/**
 * common terms mixin, see {@linkplain CommonTerms}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@JsonInclude(Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public interface CommonTermsMixin {
	/**
	 * get analyzer name
	 * 
	 * @return analyzer name
	 */
	@JsonProperty("analyzer")
	String getAnalyzerName();

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
	 * get field name
	 * 
	 * @return field name
	 */
	@JsonIgnore
	String getFieldName();

	/**
	 * get high frequency conjunction
	 * 
	 * @return conjunction
	 */
	@JsonProperty("high_freq_operator")
	@JsonSerialize(using = ExampleTextConjunctionSerializer.class)
	ExampleTextConjunction getHighConjunction();

	/**
	 * get high minimum should match.<br>
	 * this property always be included for serialize the "minimum_should_match"
	 * part in json output. see {@linkplain QuerySerializerModifier} for more
	 * details.
	 * 
	 * @return minimum should match
	 */
	@JsonInclude()
	MinimumShouldMatch getHighMinimumShouldMatch();

	/**
	 * get low frequency conjunction
	 * 
	 * @return conjunction
	 */
	@JsonProperty("low_freq_operator")
	@JsonSerialize(using = ExampleTextConjunctionSerializer.class)
	ExampleTextConjunction getLowConjunction();

	/**
	 * get low minimum should match.<br>
	 * this property always be excluded for serialize. it will be serialized by
	 * {@linkplain #getHighMinimumShouldMatch()}
	 * 
	 * @return minimum should match
	 */
	@JsonIgnore
	MinimumShouldMatch getLowMinimumShouldMatch();
}
