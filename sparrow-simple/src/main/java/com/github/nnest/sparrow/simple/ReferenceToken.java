/**
 * 
 */
package com.github.nnest.sparrow.simple;

import ognl.Ognl;
import ognl.OgnlException;

/**
 * reference token
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ReferenceToken extends StringToken {
	public ReferenceToken(String token) {
		super(token);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.StringToken#getValue(java.lang.Object)
	 */
	@Override
	public Object getValue(Object from) {
		if (from == null) {
			return null;
		} else {
			Object value;
			try {
				value = Ognl.getValue(this.getToken(), from);
			} catch (OgnlException e) {
				throw new TemplateParseException( //
						String.format( //
								"Value cannot be fetched by token[%1$s] from [%2$s]", //
								this.getToken(), //
								from.getClass()), //
						e);
			}
			if (value == null) {
				return null;
			} else {
				return value.toString();
			}
		}
	}
}
