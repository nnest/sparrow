/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.attrs.ScoreMode;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch;
import com.github.nnest.sparrow.command.document.query.compound.AbstractCompoundQuery;
import com.github.nnest.sparrow.command.document.query.compound.Bool;
import com.github.nnest.sparrow.command.document.query.compound.FunctionScore;
import com.github.nnest.sparrow.rest.command.mixins.serialize.MinimumShouldMatchSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ScoreModeSerilaizer;

/**
 * compound query mixin, see {@linkplain AbstractCompoundQuery}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@JsonInclude(Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public interface CompoundQueryMixin {
	/**
	 * get example type
	 * 
	 * @return example type
	 */
	@JsonIgnore
	ExampleType getExampleType();

	/**
	 * get minimum should match. from {@linkplain Bool}
	 * 
	 * @return minimum should match
	 */
	@JsonSerialize(using = MinimumShouldMatchSerializer.class)
	MinimumShouldMatch getMinimumShouldMatch();

	/**
	 * get score mode, from {@linkplain FunctionScore}
	 * 
	 * @return score mode
	 */
	@JsonSerialize(using = ScoreModeSerilaizer.class)
	ScoreMode getScoreMode();

	/**
	 * get boost mode, from {@linkplain FunctionScore}
	 * 
	 * @return boost mode
	 */
	@JsonSerialize(using = ScoreModeSerilaizer.class)
	ScoreMode getBoostMode();
}
