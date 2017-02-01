/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentAnalyzer;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.sort.Sort;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * query documents.<br>
 * use {@linkplain #scope} to describe the query scope.<br>
 * for {@linkplain #hitTypes}, always add, never remove.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Query implements ElasticCommand {
	private static final String DEFAULT_HINT = "";
	// scope
	private Map<Class<?>, ElasticDocumentDescriptor> scope = null;
	private Map<Class<?>, Object> typeIgnoredInScope = new HashMap<>();
	// hits
	private Map<Class<?>, String> hitTypes = new HashMap<>();
	private Map<String, ElasticDocumentDescriptor> hitDocumentDescriptors = new HashMap<>();
	// example
	private Example example = null;

	private Integer from = null;
	private Integer size = null;

	private List<Sort> sorts = null;
	private Boolean trackScores = null;

	public Query(Example example) {
		this.withExample(example);
	}

	/**
	 * @return the example
	 */
	public Example getExample() {
		return example;
	}

	/**
	 * @param example
	 *            the example to set
	 * @return this
	 */
	public Query withExample(Example example) {
		assert example != null : "Example cannot be null.";

		this.example = example;
		return this;
	}

	/**
	 * @return the from
	 */
	public Integer getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 * @return this
	 */
	public Query withFrom(Integer from) {
		assert from != null && from >= 0 : "From cannot be null, and must be zero or positive.";

		this.from = from;
		return this;
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
	 * @return this
	 */
	public Query withSize(Integer size) {
		assert size != null && size > 0 : "Size cannot be null, and must be positive.";

		this.size = size;
		return this;
	}

	/**
	 * @return the sorts
	 */
	public List<Sort> getSorts() {
		return sorts;
	}

	/**
	 * @param sorts
	 *            the sorts to set
	 * @return this
	 */
	public Query withSorts(List<Sort> sorts) {
		assert sorts != null && sorts.size() > 0 : "Sorts cannot be null or empty.";

		this.sorts = sorts;
		return this;
	}

	/**
	 * with sorts
	 * 
	 * @param sorts
	 * @return this
	 */
	public Query withSorts(Sort... sorts) {
		assert sorts != null && sorts.length > 0 : "Sorts cannot be null or empty.";

		this.sorts = Lists.newArrayList(sorts);
		return this;
	}

	/**
	 * @return the trackScores
	 */
	public Boolean getTrackScores() {
		return trackScores;
	}

	/**
	 * @param trackScores
	 *            the trackScores to set
	 * @return this
	 */
	public Query withTrackScores(Boolean trackScores) {
		assert trackScores != null : "Track scores cannot be null.";

		this.trackScores = trackScores;
		return this;
	}

	/**
	 * @return the scope
	 */
	public Set<Class<?>> getScopeClasses() {
		return this.scope == null ? null : this.scope.keySet();
	}

	/**
	 * get scope class document descriptor
	 * 
	 * @param scopeClass
	 *            scope class
	 * @return document descriptor
	 */
	public ElasticDocumentDescriptor getScopeDocumentDescriptor(Class<?> scopeClass) {
		return this.scope == null ? null : this.scope.get(scopeClass);
	}

	/**
	 * @param scope
	 *            the scope to set
	 * @return this
	 */
	public Query withScope(List<Class<?>> scope) {
		return this.withScope(scope, false);
	}

	/**
	 * 
	 * @param scope
	 *            scope classes
	 * @param ignoreType
	 *            flag of the type of scope class should be ignored or not
	 * @return this
	 */
	public Query withScope(List<Class<?>> scope, boolean ignoreType) {
		if (this.scope == null) {
			this.scope = new HashMap<>();
		}
		this.scope.clear();
		if (scope != null) {
			for (Class<?> scopeClass : scope) {
				this.scope.put(scopeClass, null);
				if (ignoreType) {
					// flag as type ignored
					this.typeIgnoredInScope.put(scopeClass, null);
				} else {
					// remove the type ignored flag
					this.typeIgnoredInScope.remove(scopeClass);
				}
			}
		}
		return this;
	}

	/**
	 * @param scope
	 *            the scope to set
	 * @return this
	 */
	public Query withScope(Class<?>... scope) {
		return this.withScope(false, scope);
	}

	/**
	 * 
	 * @param ignoreType
	 *            lag of the type of scope class should be ignored or not
	 * @param scope
	 *            scope classes
	 * @return this
	 */
	public Query withScope(boolean ignoreType, Class<?>... scope) {
		return this.withScope(scope == null ? Lists.newArrayList() : Lists.newArrayList(scope), ignoreType);
	}

	/**
	 * check the given scope class is type ignored or not
	 * 
	 * @param scopeClass
	 *            scope class
	 * @return ignored or not
	 */
	public boolean isTypeIgnored(Class<?> scopeClass) {
		return this.typeIgnoredInScope.containsKey(scopeClass);
	}

	/**
	 * @return the hitTypes
	 */
	public Set<Class<?>> getHitTypes() {
		return hitTypes.keySet();
	}

	/**
	 * with hit document type, add
	 * 
	 * @param documentTypes
	 *            document types
	 * @return this
	 */
	public Query withHit(Set<Class<?>> documentTypes) {
		assert documentTypes != null && documentTypes.size() != 0 : "Document types cannot be null.";

		// put into hit mapping using qualified class name
		for (Class<?> documentType : documentTypes) {
			this.hitTypes.put(documentType, null);
		}
		return this;
	}

	/**
	 * with hit document type, add
	 * 
	 * @param documentTypes
	 *            document type
	 * @return this
	 */
	public Query withHit(Class<?>... documentTypes) {
		assert documentTypes != null && documentTypes.length != 0 : "Document types cannot be null.";

		return this.withHit(Sets.newHashSet(documentTypes));
	}

	/**
	 * with hit document type, add, for given index
	 * 
	 * @param documentType
	 *            document type
	 * @param index
	 *            index name
	 * @return this
	 */
	public Query withHit(Class<?> documentType, String index) {
		assert documentType != null : "Document type cannot be null.";
		assert Strings.nullToEmpty(index).trim().length() != 0 : "Index name cannot be null or blank.";

		this.hitTypes.put(documentType, index);
		return this;
	}

	/**
	 * with hit document type, add for given index and type
	 * 
	 * @param documentType
	 *            document type
	 * @param index
	 *            index name
	 * @param type
	 *            type name
	 * @return this
	 */
	public Query withHit(Class<?> documentType, String index, String type) {
		assert documentType != null : "Document type cannot be null.";
		assert Strings.nullToEmpty(index).trim().length() != 0 : "Index name cannot be null or blank.";
		assert Strings.nullToEmpty(type).trim().length() != 0 : "Type name cannot be null or blank.";

		this.hitTypes.put(documentType, index + "/" + type);
		return this;
	}

	/**
	 * with default hint
	 * 
	 * @param documentType
	 *            document type
	 * @return this
	 */
	public Query withDefaultHint(Class<?> documentType) {
		assert documentType != null : "Document type cannot be null.";

		this.hitTypes.put(documentType, DEFAULT_HINT);
		return this;
	}

	/**
	 * get hit document descriptor
	 * 
	 * @param index
	 *            index name
	 * @param type
	 *            type name
	 * @return document descriptor
	 */
	public ElasticDocumentDescriptor getHitDocumentDescriptor(String index, String type) {
		// find by index/type
		ElasticDocumentDescriptor documentDescriptor = this.hitDocumentDescriptors.get(index + "/" + type);
		// find by index only
		if (documentDescriptor == null) {
			documentDescriptor = this.hitDocumentDescriptors.get(index);
		}
		// find in scopes
		if (documentDescriptor == null && this.scope != null) {
			for (ElasticDocumentDescriptor descriptor : this.scope.values()) {
				if (descriptor.getIndex().equals(index) && descriptor.getType().equals(type)) {
					return descriptor;
				}
			}
		}

		if (documentDescriptor == null) {
			documentDescriptor = this.hitDocumentDescriptors.get(DEFAULT_HINT);
		}
		return documentDescriptor;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#getCommandKind()
	 */
	@Override
	public ElasticCommandKind getCommandKind() {
		return ElasticCommandKind.QUERY;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#analysis(com.github.nnest.sparrow.ElasticDocumentAnalyzer)
	 */
	@Override
	public ElasticCommand analysis(ElasticDocumentAnalyzer documentAnalyzer) {
		// analyze hits
		for (Map.Entry<Class<?>, String> entry : this.hitTypes.entrySet()) {
			ElasticDocumentDescriptor descriptor = documentAnalyzer.analysis(entry.getKey());
			String value = entry.getValue();
			if (value == null) {
				this.hitDocumentDescriptors.put(descriptor.getIndex() + "/" + descriptor.getType(), descriptor);
			} else {
				this.hitDocumentDescriptors.put(value, descriptor);
			}
		}

		// analyze scope
		if (this.scope != null) {
			for (Map.Entry<Class<?>, ElasticDocumentDescriptor> entry : this.scope.entrySet()) {
				Class<?> scopeClass = entry.getKey();
				this.scope.put(scopeClass, documentAnalyzer.analysis(scopeClass));
			}
		}
		return this;
	}
}
