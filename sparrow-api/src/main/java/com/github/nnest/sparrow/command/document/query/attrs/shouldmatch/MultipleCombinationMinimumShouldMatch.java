/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.shouldmatch;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

/**
 * multiple combination minimum should match
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MultipleCombinationMinimumShouldMatch implements MinimumShouldMatch {
	private List<CombinationMinimumShouldMatch> matches = new LinkedList<CombinationMinimumShouldMatch>();

	public MultipleCombinationMinimumShouldMatch(CombinationMinimumShouldMatch... matches) {
		this.with(matches);
	}

	/**
	 * @return the matches
	 */
	public List<CombinationMinimumShouldMatch> getMatches() {
		return matches;
	}

	/**
	 * @param matches
	 *            the matches to set
	 */
	protected void setMatches(List<CombinationMinimumShouldMatch> matches) {
		this.matches.clear();
		if (matches != null && matches.size() > 0) {
			this.matches.addAll(matches);
		}
	}

	/**
	 * with given matches
	 * 
	 * @param matches
	 *            matches
	 * @return this
	 */
	public MultipleCombinationMinimumShouldMatch with(CombinationMinimumShouldMatch... matches) {
		if (matches != null && matches.length != 0) {
			this.matches.addAll(Arrays.asList(matches));
		}
		return this;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch#asString()
	 */
	@Override
	public String asString() {
		return Joiner.on(' ')
				.join(Iterables.transform(this.getMatches(), new Function<CombinationMinimumShouldMatch, String>() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.google.common.base.Function#apply(java.lang.Object)
					 */
					@Override
					public String apply(CombinationMinimumShouldMatch input) {
						return input.asString();
					}
				}));
	}
}
