/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.term;

import java.util.List;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * terms lookup external term
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class TermsLookupExternal extends AbstractTermLevelQuery<TermsLookupExternal> {
	private List<ExternalDocumentTerm> terms = null;

	public TermsLookupExternal(String fieldName) {
		super(fieldName);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.TERMS;
	}

	/**
	 * @return the terms
	 */
	public List<ExternalDocumentTerm> getTerms() {
		return terms;
	}

	/**
	 * @param terms
	 *            the terms to set
	 * @return this
	 */
	public TermsLookupExternal withTerms(List<ExternalDocumentTerm> terms) {
		assert terms != null && terms.size() > 0 : "Terms cannot be null or empty.";

		this.terms = terms;
		return this;
	}

	/**
	 * @param terms
	 *            the terms to set
	 * @return this
	 */
	public TermsLookupExternal withTerms(ExternalDocumentTerm... terms) {
		assert terms != null && terms.length > 0 : "Terms cannot be null or empty.";

		this.terms = Lists.newArrayList(terms);
		return this;
	}

	/**
	 * external document term
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class ExternalDocumentTerm {
		private String id = null;
		private String index = null;
		private String type = null;
		private String path = null;

		public ExternalDocumentTerm(String index, String type, String id, String path) {
			this.withIndex(index) //
					.withType(type) //
					.withId(id) //
					.withPath(path);
		}

		/**
		 * id of external document
		 * 
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id
		 *            the id to set
		 * @return this
		 */
		protected ExternalDocumentTerm withId(String id) {
			assert Strings.nullToEmpty(id).trim().length() != 0 : "Id cannot be null or blank.";

			this.id = id;
			return this;
		}

		/**
		 * @return the index
		 */
		public String getIndex() {
			return index;
		}

		/**
		 * @param index
		 *            the index to set
		 * @return this
		 */
		public ExternalDocumentTerm withIndex(String index) {
			assert Strings.nullToEmpty(index).trim().length() != 0 : "Index name cannot be null or blank.";

			this.index = index;
			return this;
		}

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * @param type
		 *            the type to set
		 * @return this
		 */
		public ExternalDocumentTerm withType(String type) {
			assert Strings.nullToEmpty(type).trim().length() != 0 : "Type name cannot be null or blank.";

			this.type = type;
			return this;
		}

		/**
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * @param path
		 *            the path to set
		 * @return this
		 */
		protected ExternalDocumentTerm withPath(String path) {
			assert Strings.nullToEmpty(path).trim().length() != 0 : "Path cannot be null or blank.";

			this.path = path;
			return this;
		}
	}
}
