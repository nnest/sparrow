/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.compound;

import java.math.BigDecimal;
import java.util.List;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.attrs.ScoreMode;
import com.github.nnest.sparrow.command.document.query.attrs.score.NestedScoreFunction;
import com.github.nnest.sparrow.command.document.query.attrs.score.ScoreFunction;
import com.google.common.collect.Lists;

/**
 * function score query.<br>
 * note the {@linkplain #function} and {@linkplain #functions} are exclusive.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class FunctionScore extends AbstractCompoundQuery<FunctionScore> {
	private Example example = null;
	private ScoreMode scoreMode = null;
	private BigDecimal minScore = null;
	private ScoreMode boostMode = null;
	private BigDecimal maxBoost = null;
	private ScoreFunction function = null;
	private List<NestedScoreFunction> functions = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.FUNCTION_SCORE;
	}

	/**
	 * @return the example
	 */
	public Example getExample() {
		return example;
	}

	/**
	 * @param example
	 *            the example to set
	 * @return this
	 */
	public FunctionScore with(Example example) {
		assert example != null : "Example cannot be null.";

		this.example = example;
		return this;
	}

	/**
	 * @return the scoreMode
	 */
	public ScoreMode getScoreMode() {
		return scoreMode;
	}

	/**
	 * @param scoreMode
	 *            the scoreMode to set
	 * @return this
	 */
	public FunctionScore withScoreMode(ScoreMode scoreMode) {
		assert scoreMode != null : "Score mode cannot be null.";

		this.scoreMode = scoreMode;
		return this;
	}

	/**
	 * @return the minScore
	 */
	public BigDecimal getMinScore() {
		return minScore;
	}

	/**
	 * @param minScore
	 *            the minScore to set
	 * @return this
	 */
	public FunctionScore withMinScore(BigDecimal minScore) {
		assert minScore != null
				&& minScore.doubleValue() >= 0 : "Min score cannot be null, and must be zero or positive.";

		this.minScore = minScore;
		return this;
	}

	/**
	 * @return the boostMode
	 */
	public ScoreMode getBoostMode() {
		return boostMode;
	}

	/**
	 * @param boostMode
	 *            the boostMode to set
	 * @return this
	 */
	public FunctionScore withBoostMode(ScoreMode boostMode) {
		assert boostMode != null : "Boost mode cannot be null.";

		this.boostMode = boostMode;
		return this;
	}

	/**
	 * @return the maxBoost
	 */
	public BigDecimal getMaxBoost() {
		return maxBoost;
	}

	/**
	 * @param maxBoost
	 *            the maxBoost to set
	 * @return this
	 */
	public FunctionScore withMaxBoost(BigDecimal maxBoost) {
		assert maxBoost != null
				&& maxBoost.doubleValue() >= 0 : "Max boost cannot be null, and must be zero or positive.";

		this.maxBoost = maxBoost;
		return this;
	}

	/**
	 * @return the function
	 */
	public ScoreFunction getFunction() {
		return function;
	}

	/**
	 * @param function
	 *            the function to set
	 * @return this
	 */
	public FunctionScore withFunction(ScoreFunction function) {
		assert function != null : "Function cannot be null.";

		this.function = function;
		return this;
	}

	/**
	 * @return the functions
	 */
	public List<NestedScoreFunction> getFunctions() {
		return functions;
	}

	/**
	 * @param functions
	 *            the functions to set
	 * @return this
	 */
	public FunctionScore withFunctions(List<NestedScoreFunction> functions) {
		assert functions != null && functions.size() > 0 : "Functions cannot be null or empty.";

		this.functions = functions;
		return this;
	}

	/**
	 * @param functions
	 *            the functions to set
	 * @return this
	 */
	public FunctionScore withFunctions(NestedScoreFunction... functions) {
		assert functions != null && functions.length > 0 : "Functions cannot be null or empty.";

		this.functions = Lists.newArrayList(functions);
		return this;
	}
}
