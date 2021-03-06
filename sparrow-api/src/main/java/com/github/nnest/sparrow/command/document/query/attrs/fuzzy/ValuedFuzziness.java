/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.fuzzy;

/**
 * valued fuzziness
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ValuedFuzziness implements Fuzziness {
	private int digits = -1;

	public ValuedFuzziness(int digits) {
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
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.attrs.fuzzy.Fuzziness#asString()
	 */
	@Override
	public String asString() {
		return String.valueOf(this.getDigits());
	}

	/**
	 * create fuzziness by given digits
	 * 
	 * @param digits
	 *            digits
	 * @return fuzziness
	 */
	public static ValuedFuzziness valueOf(int digits) {
		return new ValuedFuzziness(digits);
	}
}
