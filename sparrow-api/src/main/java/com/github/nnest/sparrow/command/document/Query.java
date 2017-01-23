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
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * query documents.<br>
 * use {@linkplain #scope} to describe the query scope.<br>
 * for {@linkplain #hintMapping}, key is "index/type". to describe how to
 * receive the data.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Query implements ElasticCommand {
	// scope
	private Map<Class<?>, ElasticDocumentDescriptor> scope = null;
	private Map<Class<?>, Object> typeIgnoredInScope = new HashMap<>();
	// hints
	private Map<String, Class<?>> hintMapping = null;
	private Map<String, ElasticDocumentDescriptor> hintDocumentDescriptors = new HashMap<>();
	// example
	private Example example = null;

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
	 * @return the hintMapping
	 */
	public Map<String, Class<?>> getHintMapping() {
		return hintMapping;
	}

	/**
	 * @param hintMapping
	 *            the hintMapping to set
	 * @return this
	 */
	public Query withHintMapping(Map<String, Class<?>> hintMapping) {
		this.hintMapping = hintMapping;
		return this;
	}

	/**
	 * with hint mapping
	 * 
	 * @param index
	 *            index name
	 * @param type
	 *            type name
	 * @param documentType
	 *            document type
	 * @return this
	 */
	public Query withHintMapping(String index, String type, Class<?> documentType) {
		assert Strings.nullToEmpty(index).trim().length() != 0 : "Index cannot be null or blank.";
		assert Strings.nullToEmpty(type).trim().length() != 0 : "Type cannot be null or blank.";

		if (this.hintMapping == null) {
			this.hintMapping = new HashMap<>();
		}
		this.hintMapping.put(index + "/" + type, documentType);
		return this;
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
		// analyze hints
		for (Map.Entry<String, Class<?>> entry : this.getHintMapping().entrySet()) {
			String key = entry.getKey();
			Class<?> documentType = entry.getValue();
			this.hintDocumentDescriptors.put(key, documentAnalyzer.analysis(documentType));
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
