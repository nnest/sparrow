/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.score;

import java.util.List;

import com.github.nnest.sparrow.command.document.query.Example;
import com.google.common.collect.Lists;

/**
 * Nested score function
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class NestedScoreFunction implements ScoreFunction {
	private List<Example> examples = null;
	private List<ScoreFunction> functions = null;

	public NestedScoreFunction(ScoreFunction... functions) {
		this.withFunctions(functions);
	}

	public NestedScoreFunction(Example filter, ScoreFunction... functions) {
		this.withExamples(filter).withFunctions(functions);
	}

	/**
	 * @return the examples
	 */
	public List<Example> getExamples() {
		return examples;
	}

	/**
	 * @param examples
	 *            the examples to set
	 * @return this
	 */
	public NestedScoreFunction withExamples(List<Example> examples) {
		assert examples != null && examples.size() > 0 : "Examples cannot be null or empty.";

		this.examples = examples;
		return this;
	}

	/**
	 * @param examples
	 *            the examples to set
	 * @return this
	 */
	public NestedScoreFunction withExamples(Example... examples) {
		assert examples != null && examples.length > 0 : "Examples cannot be null or empty.";

		this.examples = Lists.newArrayList(examples);
		return this;
	}

	/**
	 * @return the functions
	 */
	public List<ScoreFunction> getFunctions() {
		return functions;
	}

	/**
	 * @param functions
	 *            the functions to set
	 * @return this
	 */
	public NestedScoreFunction withFunctions(List<ScoreFunction> functions) {
		assert functions != null && functions.size() > 0 : "Functions cannot be null or empty.";

		this.functions = functions;
		return this;
	}

	/**
	 * @param functions
	 *            the functions to set
	 * @return this
	 */
	public NestedScoreFunction withFunctions(ScoreFunction... functions) {
		assert functions != null && functions.length > 0 : "Functions cannot be null or empty.";

		this.functions = Lists.newArrayList(functions);
		return this;
	}
}
