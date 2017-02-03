/**
 * 
 */
package com.github.nnest.sparrow.simple.exec;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * default body value converter
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class PrimitiveBodyValueConverter implements BodyValueConverter {
	private final static SimpleDateFormat SMF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.exec.BodyValueConverter#convert(java.lang.Object)
	 */
	@Override
	public String convert(Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof CharSequence) {
			return new StringBuilder(((CharSequence) value).length() + 2) //
					.append('"') //
					.append(value.toString()) //
					.append('"') //
					.toString();
		} else if (value instanceof Boolean) {
			return ((Boolean) value).booleanValue() ? "true" : "false";
		} else if (value instanceof Number) {
			return value.toString();
		} else if (value instanceof Date) {
			return new StringBuilder(19) //
					.append('"') //
					.append(SMF.format((Date) value)) //
					.append('"') //
					.toString();
		} else if (value instanceof Character) {
			return new StringBuilder(3) //
					.append('"') //
					.append(((Character) value).charValue()) //
					.append('"') //
					.toString();
		} else {
			return value.toString();
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.exec.BodyValueConverter#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(Object value) {
		return (value == null) || //
				(value instanceof CharSequence) || //
				(value instanceof Boolean) || //
				(value instanceof Number) || //
				(value instanceof Date) || //
				(value instanceof Character);
	}
}
