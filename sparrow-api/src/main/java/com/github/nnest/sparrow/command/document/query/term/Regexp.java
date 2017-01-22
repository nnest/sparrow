/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.term;

import java.util.Set;

import com.github.nnest.sparrow.command.document.query.attrs.RegexpFlag;
import com.github.nnest.sparrow.command.document.query.attrs.rewrite.Rewrite;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

/**
 * Regexp term
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Regexp extends AbstractTermLevelQuery<Regexp> {
	private String exampleText = null;
	private Rewrite rewrite = null;
	private Set<RegexpFlag> flags = null;
	private Integer maxDeterminizedStates = null;

	public Regexp(String fieldName) {
		super(fieldName);
	}

	/**
	 * @return the exampleText
	 */
	public String getExampleText() {
		return exampleText;
	}

	/**
	 * @param exampleText
	 *            the exampleText to set
	 * @return this
	 */
	public Regexp withExampleText(String exampleText) {
		assert Strings.nullToEmpty(exampleText).length() != 0 : "Example text cannot be null or empty.";

		this.exampleText = exampleText;
		return this;
	}

	/**
	 * @return the rewrite
	 */
	public Rewrite getRewrite() {
		return rewrite;
	}

	/**
	 * @param rewrite
	 *            the rewrite to set
	 * @return this
	 */
	public Regexp with(Rewrite rewrite) {
		assert rewrite != null : "Rewrite cannot be null.";

		this.rewrite = rewrite;
		return this;
	}

	/**
	 * @return the flags
	 */
	public Set<RegexpFlag> getFlags() {
		return flags;
	}

	/**
	 * @param flags
	 *            the flags to set
	 * @return this
	 */
	public Regexp withFlags(Set<RegexpFlag> flags) {
		assert flags != null && flags.size() > 0 : "Flags cannot be null or empty.";

		this.flags = flags;
		return this;
	}

	/**
	 * @param flags
	 *            the flags to set
	 * @return this
	 */
	public Regexp withFlags(RegexpFlag... flags) {
		assert flags != null && flags.length > 0 : "Flags cannot be null or empty.";

		this.flags = Sets.newHashSet(flags);
		return this;
	}

	/**
	 * @return the maxDeterminizedStates
	 */
	public Integer getMaxDeterminizedStates() {
		return maxDeterminizedStates;
	}

	/**
	 * @param maxDeterminizedStates
	 *            the maxDeterminizedStates to set
	 * @return this
	 */
	public Regexp withMaxDeterminizedStates(Integer maxDeterminizedStates) {
		assert maxDeterminizedStates != null
				&& maxDeterminizedStates >= 0 : "Max determinized states cannot be null, and must be zero or positive.";

		this.maxDeterminizedStates = maxDeterminizedStates;
		return this;
	}
}
