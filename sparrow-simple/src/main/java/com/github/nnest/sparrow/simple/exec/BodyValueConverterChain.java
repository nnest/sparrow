/**
 * 
 */
package com.github.nnest.sparrow.simple.exec;

import java.util.List;

import com.github.nnest.sparrow.simple.TemplateParseException;
import com.google.common.collect.Lists;

/**
 * body value converter chain
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class BodyValueConverterChain implements BodyValueConverter {
	private List<BodyValueConverter> converters = null;

	public BodyValueConverterChain() {
	}

	public BodyValueConverterChain(BodyValueConverter... converters) {
		assert converters != null : "Converters cannot be null.";

		this.setConverters(Lists.newArrayList(converters));
	}

	/**
	 * @return the converters
	 */
	public List<BodyValueConverter> getConverters() {
		return converters;
	}

	/**
	 * @param converters
	 *            the converters to set
	 */
	public void setConverters(List<BodyValueConverter> converters) {
		this.converters = converters;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.exec.BodyValueConverter#convert(java.lang.Object)
	 */
	@Override
	public String convert(Object value) {
		for (BodyValueConverter converter : converters) {
			if (converter.accept(value)) {
				return converter.convert(value);
			}
		}
		throw new TemplateParseException( //
				String.format("Converters doesn't support value[%1$s]", //
						value == null ? "null" : value.getClass()));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.exec.BodyValueConverter#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(Object value) {
		for (BodyValueConverter converter : converters) {
			if (converter.accept(value)) {
				return true;
			}
		}
		return false;
	}
}
