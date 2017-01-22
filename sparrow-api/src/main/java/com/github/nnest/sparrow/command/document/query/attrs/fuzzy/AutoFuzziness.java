/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.fuzzy;

/**
 * auto fuzziness. singleton
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class AutoFuzziness implements Fuzziness {
	public static final Fuzziness AUTO = new AutoFuzziness();

	private AutoFuzziness() {
	}
}
