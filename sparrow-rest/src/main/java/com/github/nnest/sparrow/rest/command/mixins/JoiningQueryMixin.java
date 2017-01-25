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
import com.github.nnest.sparrow.command.document.query.joining.AbstractJoiningQuery;
import com.github.nnest.sparrow.command.document.query.joining.HasChild;
import com.github.nnest.sparrow.command.document.query.joining.Nested;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ScoreModeSerilaizer;

/**
 * Joining query mixin, see {@linkplain AbstractJoiningQuery}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@JsonInclude(Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public interface JoiningQueryMixin {
	/**
	 * get example type
	 * 
	 * @return example type
	 */
	@JsonIgnore
	ExampleType getExampleType();

	/**
	 * get score mode, from {@linkplain HasChild} and {@linkplain Nested}
	 * 
	 * @return
	 */
	@JsonSerialize(using = ScoreModeSerilaizer.class)
	ScoreMode getScoreMode();
}
