/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fulltext;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.attrs.fuzzy.Fuzziness;

/**
 * multiple match most fields
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MultiMatchMostFields extends AbstractMultiMatch<MultiMatchMostFields> {
	private Fuzziness fuzziness = null;

	public MultiMatchMostFields(String exampleText) {
		super(exampleText);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.MULTI_MOST_FIELDS;
	}

	/**
	 * @return the fuzziness
	 */
	public Fuzziness getFuzziness() {
		return fuzziness;
	}

	/**
	 * @param fuzziness
	 *            the fuzziness to set
	 * @return this
	 */
	public MultiMatchMostFields with(Fuzziness fuzziness) {
		assert fuzziness != null : "Fuzziness cannot be null.";

		this.fuzziness = fuzziness;
		return this;
	}
}
