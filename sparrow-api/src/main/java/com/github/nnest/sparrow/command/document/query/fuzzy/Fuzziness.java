/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fuzzy;

import com.github.nnest.sparrow.command.document.query.rewrite.Rewrite;

/**
 * Fuzziness
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Fuzziness {
	private int digits = -1;
	private Integer prefixLength = null;
	private Integer maxExpansions = null;
	private Rewrite rewrite = null;
	private Boolean transpositions = null;

	protected Fuzziness() {
	}

	public Fuzziness(int digits) {
		this.setDigits(digits);
	}

	/**
	 * @return the digits
	 */
	public int getDigits() {
		return digits;
	}

	/**
	 * @param digits
	 *            the digits to set
	 */
	protected void setDigits(int digits) {
		assert digits >= 0 : "Digits must be zero or positive.";

		this.digits = digits;
	}

	/**
	 * @return the prefixLength
	 */
	public Integer getPrefixLength() {
		return prefixLength;
	}

	/**
	 * @param prefixLength
	 *            the prefixLength to set
	 * @return this
	 */
	public Fuzziness withPrefixLength(Integer prefixLength) {
		assert prefixLength != null
				&& prefixLength >= 0 : "Prefix length cannot be null, and must be zero or positive.";

		this.prefixLength = prefixLength;
		return this;
	}

	/**
	 * @return the maxExpansions
	 */
	public Integer getMaxExpansions() {
		return maxExpansions;
	}

	/**
	 * @param maxExpansions
	 *            the maxExpansions to set
	 * @return this
	 */
	public Fuzziness withMaxExpansions(Integer maxExpansions) {
		assert maxExpansions != null
				&& maxExpansions >= 0 : "Max expansions cannot be null, and must be zero or positive.";

		this.maxExpansions = maxExpansions;
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
	public Fuzziness with(Rewrite rewrite) {
		assert rewrite != null : "Rewrite cannot be null.";

		this.rewrite = rewrite;
		return this;
	}

	/**
	 * @return the transpositions
	 */
	public Boolean getTranspositions() {
		return transpositions;
	}

	/**
	 * @param transpositions
	 *            the transpositions to set
	 * @return this
	 */
	public Fuzziness withTranspositions(Boolean transpositions) {
		assert transpositions != null : "Transpositions cannot be null.";

		this.transpositions = transpositions;
		return this;
	}
}
