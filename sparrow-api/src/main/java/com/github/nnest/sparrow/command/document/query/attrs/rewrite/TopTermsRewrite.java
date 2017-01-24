/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.attrs.rewrite;

/**
 * top terms rewrite
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class TopTermsRewrite implements Rewrite {
	private TopTermsRewriteType type = null;
	private Integer size = null;

	public TopTermsRewrite(TopTermsRewriteType type, Integer size) {
		this.setType(type);
		this.setSize(size);
	}

	/**
	 * @return the type
	 */
	public TopTermsRewriteType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	protected void setType(TopTermsRewriteType type) {
		assert type != null : "Type cannot be null.";
		this.type = type;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	protected void setSize(Integer size) {
		assert size != null && size > 0 : "Size cannot be null, and must be positive.";
		this.size = size;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.attrs.rewrite.Rewrite#asString()
	 */
	@Override
	public String asString() {
		return this.getType().asString(this.getSize());
	}

	public interface TopTermsRewriteType {
		/**
		 * get top terms rewrite type as string
		 * 
		 * @param size
		 *            size control
		 * @return type as string
		 */
		String asString(Integer size);
	}

	/**
	 * default valued rewrite type
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public enum DefaultTopTermsRewriteType implements TopTermsRewriteType {
		DEFAULT("top_terms"), //
		BOOST("top_terms_boost"), //
		BLENDED_FREQUENCY("top_terms_blended_freqs");

		private String name = null;

		private DefaultTopTermsRewriteType(String name) {
			this.name = name;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.command.document.query.attrs.rewrite.TopTermsRewrite.TopTermsRewriteType#asString(java.lang.Integer)
		 */
		@Override
		public String asString(Integer size) {
			return this.name + "_" + size;
		}
	}
}
