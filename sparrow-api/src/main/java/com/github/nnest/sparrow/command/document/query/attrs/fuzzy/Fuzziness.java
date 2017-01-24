/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.fuzzy;

/**
 * Fuzziness
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface Fuzziness {
	/**
	 * auto fuzziness
	 */
	public final static Fuzziness AUTO = new Fuzziness() {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.command.document.query.attrs.fuzzy.Fuzziness#asString()
		 */
		@Override
		public String asString() {
			return "AUTO";
		}
	};

	/**
	 * get value as string
	 * 
	 * @return string value
	 */
	String asString();
}
