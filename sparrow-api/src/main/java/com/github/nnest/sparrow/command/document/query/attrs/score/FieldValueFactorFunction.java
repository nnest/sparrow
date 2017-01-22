/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.score;

import java.math.BigDecimal;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * field value factor function
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class FieldValueFactorFunction implements ScoreFunction {
	private Set<String> fieldNames = null;
	private BigDecimal factor = null;
	private ScoreModifier modifier = null;
	private BigDecimal missing = null;

	public FieldValueFactorFunction(String fieldName) {
		this.withFieldNames(fieldName);
	}

	public FieldValueFactorFunction(String... fieldNames) {
		this.withFieldNames(fieldNames);
	}

	public FieldValueFactorFunction(Set<String> fieldNames) {
		this.withFieldNames(fieldNames);
	}

	/**
	 * @return the fieldNames
	 */
	public Set<String> getFieldNames() {
		return fieldNames;
	}

	/**
	 * @param fieldNames
	 *            the fieldNames to set
	 * @return this
	 */
	public FieldValueFactorFunction withFieldNames(Set<String> fieldNames) {
		assert fieldNames != null && fieldNames.size() > 0 : "Field names cannot be null or empty.";

		this.fieldNames = fieldNames;
		return this;
	}

	/**
	 * @param fieldNames
	 *            the fieldNames to set
	 * @return this
	 */
	public FieldValueFactorFunction withFieldNames(String... fieldNames) {
		assert fieldNames != null && fieldNames.length > 0 : "Field names cannot be null or empty.";

		this.fieldNames = Sets.newHashSet(fieldNames);
		return this;
	}

	/**
	 * @return the factor
	 */
	public BigDecimal getFactor() {
		return factor;
	}

	/**
	 * @param factor
	 *            the factor to set
	 * @return this
	 */
	public FieldValueFactorFunction withFactor(BigDecimal factor) {
		assert factor != null : "Factor cannot be null.";

		this.factor = factor;
		return this;
	}

	/**
	 * @return the modifier
	 */
	public ScoreModifier getModifier() {
		return modifier;
	}

	/**
	 * @param modifier
	 *            the modifier to set
	 * @return this
	 */
	public FieldValueFactorFunction with(ScoreModifier modifier) {
		assert modifier != null : "Modifier cannot be null.";

		this.modifier = modifier;
		return this;
	}

	/**
	 * @return the missing
	 */
	public BigDecimal getMissing() {
		return missing;
	}

	/**
	 * @param missing
	 *            the missing to set
	 * @return this
	 */
	public FieldValueFactorFunction withMissing(BigDecimal missing) {
		assert missing != null : "Missing cannot be null.";

		this.missing = missing;
		return this;
	}
}
