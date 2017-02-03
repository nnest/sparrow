/**
 * 
 */
package com.github.nnest.sparrow.simple.exec;

/**
 * value converter
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface BodyValueConverter {
	/**
	 * convert value
	 * 
	 * @param value
	 *            value
	 * @return string value
	 */
	String convert(Object value);

	/**
	 * accept object or not
	 * 
	 * @param value
	 *            value
	 * @return acceptable
	 */
	boolean accept(Object value);
}
