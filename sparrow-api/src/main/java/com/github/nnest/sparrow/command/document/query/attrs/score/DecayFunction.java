/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.score;

import java.math.BigDecimal;

import com.google.common.base.Strings;

/**
 * Decay function
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DecayFunction implements ScoreFunction {
	private DecayFunctionType type = null;
	private String fieldName = null;
	private String origin = null;
	private String scale = null;
	private String offset = null;
	private BigDecimal decay = null;

	public DecayFunction(DecayFunctionType type, String fieldName) {
		this.with(type).withFieldName(fieldName);
	}

	/**
	 * @return the type
	 */
	public DecayFunctionType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 * @return this
	 */
	public DecayFunction with(DecayFunctionType type) {
		assert type != null : "Type cannot be null.";

		this.type = type;
		return this;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 * @return this
	 */
	public DecayFunction withFieldName(String fieldName) {
		assert Strings.nullToEmpty(fieldName).trim().length() != 0 : "Field name cannot be null or blank.";

		this.fieldName = fieldName;
		return this;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 *            the origin to set
	 * @return this
	 */
	public DecayFunction setOrigin(String origin) {
		assert Strings.nullToEmpty(origin).trim().length() != 0 : "Origin cannot be null or blank.";

		this.origin = origin;
		return this;
	}

	/**
	 * @return the scale
	 */
	public String getScale() {
		return scale;
	}

	/**
	 * @param scale
	 *            the scale to set
	 * @return this
	 */
	public DecayFunction setScale(String scale) {
		assert Strings.nullToEmpty(scale).trim().length() != 0 : "Scale cannot be null or blank.";

		this.scale = scale;
		return this;
	}

	/**
	 * @return the offset
	 */
	public String getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 * @return this
	 */
	public DecayFunction setOffset(String offset) {
		assert Strings.nullToEmpty(offset).trim().length() != 0 : "Offset cannot be null or blank.";

		this.offset = offset;
		return this;
	}

	/**
	 * @return the decay
	 */
	public BigDecimal getDecay() {
		return decay;
	}

	/**
	 * @param decay
	 *            the decay to set
	 * @return this
	 */
	public DecayFunction setDecay(BigDecimal decay) {
		assert decay != null : "Decay cannot be null.";

		this.decay = decay;
		return this;
	}
}
