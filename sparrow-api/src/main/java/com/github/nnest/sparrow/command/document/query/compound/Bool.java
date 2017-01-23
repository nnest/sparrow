/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.compound;

import java.util.List;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch;
import com.google.common.collect.Lists;

/**
 * Boolean query<br>
 * Named query is not supported.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Bool extends AbstractCompoundQuery<Bool> {
	private List<Example> must = null;
	private List<Example> filter = null;
	private List<Example> mustNot = null;
	private List<Example> should = null;
	private MinimumShouldMatch minimumShouldMatch = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.BOOL;
	}

	/**
	 * @return the must
	 */
	public List<Example> getMust() {
		return must;
	}

	/**
	 * @param must
	 *            the must to set
	 * @return this
	 */
	public Bool withMust(List<Example> must) {
		assert must != null && must.size() > 0 : "Must examples cannot be null or empty.";

		this.must = must;
		return this;
	}

	/**
	 * @param must
	 *            the must to set
	 * @return this
	 */
	public Bool withMust(Example... must) {
		assert must != null && must.length > 0 : "Must examples cannot be null or empty.";

		this.must = Lists.newArrayList(must);
		return this;
	}

	/**
	 * @return the filter
	 */
	public List<Example> getFilter() {
		return filter;
	}

	/**
	 * @param filter
	 *            the filter to set
	 * @return this
	 */
	public Bool withFilter(List<Example> filter) {
		assert filter != null && filter.size() > 0 : "Filter examples cannot be null or empty.";

		this.filter = filter;
		return this;
	}

	/**
	 * @param filter
	 *            the filter to set
	 * @return this
	 */
	public Bool withFilter(Example... filter) {
		assert filter != null && filter.length > 0 : "Filter examples cannot be null or empty.";

		this.filter = Lists.newArrayList(filter);
		return this;
	}

	/**
	 * @return the mustNot
	 */
	public List<Example> getMustNot() {
		return mustNot;
	}

	/**
	 * @param mustNot
	 *            the mustNot to set
	 * @return this
	 */
	public Bool withMustNot(List<Example> mustNot) {
		assert mustNot != null && mustNot.size() > 0 : "Must Not examples cannot be null or empty.";

		this.mustNot = mustNot;
		return this;
	}

	/**
	 * @param mustNot
	 *            the mustNot to set
	 * @return this
	 */
	public Bool withMustNot(Example... mustNot) {
		assert mustNot != null && mustNot.length > 0 : "Must Not examples cannot be null or empty.";

		this.mustNot = Lists.newArrayList(mustNot);
		return this;
	}

	/**
	 * @return the should
	 */
	public List<Example> getShould() {
		return should;
	}

	/**
	 * @param should
	 *            the should to set
	 * @return this
	 */
	public Bool withShould(List<Example> should) {
		assert should != null && should.size() > 0 : "Should examples cannot be null or empty.";

		this.should = should;
		return this;
	}

	/**
	 * @param should
	 *            the should to set
	 * @return this
	 */
	public Bool withShould(Example... should) {
		assert should != null && should.length > 0 : "Should examples cannot be null or empty.";

		this.should = Lists.newArrayList(should);
		return this;
	}

	/**
	 * @return the minimumShouldMatch
	 */
	public MinimumShouldMatch getMinimumShouldMatch() {
		return minimumShouldMatch;
	}

	/**
	 * @param minimumShouldMatch
	 *            the minimumShouldMatch to set
	 * @return this
	 */
	public Bool with(MinimumShouldMatch minimumShouldMatch) {
		assert minimumShouldMatch != null : "Minimum should match cannot be null.";

		this.minimumShouldMatch = minimumShouldMatch;
		return this;
	}
}
