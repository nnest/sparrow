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
import com.github.nnest.sparrow.command.document.query.attrs.score.DecayFunction;
import com.github.nnest.sparrow.command.document.query.attrs.score.DecayFunctionType;
import com.github.nnest.sparrow.command.document.query.attrs.score.FieldValueFactorFunction;
import com.github.nnest.sparrow.command.document.query.attrs.score.ScoreFunction;
import com.github.nnest.sparrow.command.document.query.attrs.score.ScoreModifier;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ScoreModifierSerializer;

/**
 * score function mixin, see {@linkplain ScoreFunction}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@JsonInclude(Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public interface ScoreFunctionMixin {
	/**
	 * get field names, from {@linkplain FieldValueFactorFunction}
	 * 
	 * @return field names
	 */
	@JsonProperty("field")
	Set<String> getFieldNames();

	/**
	 * get modifier, from {@linkplain FieldValueFactorFunction}
	 * 
	 * @return modifier
	 */
	@JsonSerialize(using = ScoreModifierSerializer.class)
	ScoreModifier getModifier();

	/**
	 * get decay function type, from {@linkplain DecayFunction}
	 * 
	 * @return type
	 */
	@JsonIgnore
	DecayFunctionType getType();

	/**
	 * get decay field name, from {@linkplain DecayFunction}
	 * 
	 * @return field name
	 */
	@JsonIgnore
	String getFieldName();
}
