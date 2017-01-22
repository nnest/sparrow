/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.score;

/**
 * random score function
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RandomScoreFunction implements ScoreFunction {
	private Long seed = null;

	public RandomScoreFunction() {
	}

	public RandomScoreFunction(Long seed) {
		this.withSeed(seed);
	}

	/**
	 * @return the seed
	 */
	public Long getSeed() {
		return seed;
	}

	/**
	 * @param seed
	 *            the seed to set
	 * @return this
	 */
	public RandomScoreFunction withSeed(Long seed) {
		assert seed != null : "Seed cannot be null.";
		this.seed = seed;
		return this;
	}
}
