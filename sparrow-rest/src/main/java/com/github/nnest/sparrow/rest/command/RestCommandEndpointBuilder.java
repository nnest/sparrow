/**
 * 
 */
package com.github.nnest.sparrow.rest.command;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * rest command uri helper
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public final class RestCommandEndpointBuilder {
	/**
	 * build endpoint by given document descriptor and id
	 * 
	 * @param documentDescriptor
	 *            document descriptor
	 * @param idValue
	 *            id
	 * @return endpoint
	 */
	public static String buildEndpoint(ElasticDocumentDescriptor documentDescriptor, String idValue) {
		return buildEndpoint(documentDescriptor, idValue, null, (String) null);
	}

	/**
	 * build endpoint by given parameters
	 * 
	 * @param documentDescriptor
	 *            document descriptor
	 * @param idValue
	 *            id
	 * @param versionValue
	 *            version
	 * @return endpoint
	 */
	public static String buildEndpoint(ElasticDocumentDescriptor documentDescriptor, String idValue,
			String versionValue) {
		return buildEndpoint(documentDescriptor, idValue, versionValue, (String) null);
	}

	/**
	 * build endpoint by given parameters
	 * 
	 * @param documentDescriptor
	 *            document descriptor
	 * @param idValue
	 *            id value
	 * @param versionValue
	 *            version value
	 * @param endpointCommand
	 *            command kind
	 * @return endpoint
	 */
	public static String buildEndpoint(ElasticDocumentDescriptor documentDescriptor, String idValue,
			String versionValue, String endpointCommand) {
		return buildEndpoint(documentDescriptor, idValue, endpointCommand,
				Sets.newHashSet(new SimpleQueryParam("version", versionValue)));
	}

	/**
	 * build endpoint by given parameters<br>
	 * index name and type name are get from {@code documentDescriptor},<br>
	 * other parameters are optional.
	 * 
	 * @param documentDescriptor
	 *            document descriptor
	 * @param idValue
	 *            id value
	 * @param endpointCommand
	 *            endpoint command
	 * @param queryParams
	 *            query parameters
	 * @return endpoint
	 */
	public static String buildEndpoint(ElasticDocumentDescriptor documentDescriptor, String idValue,
			String endpointCommand, Set<QueryParam> queryParams) {
		List<String> parts = new LinkedList<String>();
		parts.add(documentDescriptor.getIndex());
		parts.add(documentDescriptor.getType());
		if (idValue != null) {
			parts.add(idValue);
		}
		parts.add(endpointCommand);
		String loc = Joiner.on('/').skipNulls().join(parts);

		return Joiner.on("?").skipNulls().join(loc, generateQueryParams(queryParams));
	}

	/**
	 * generate query parameters string
	 * 
	 * @param queryParams
	 *            query parameters
	 * @return query string
	 */
	public static String generateQueryParams(Set<QueryParam> queryParams) {
		if (queryParams == null || queryParams.size() == 0) {
			return null;
		} else {
			return Joiner.on("&").skipNulls().join(Iterables.transform(queryParams, new Function<QueryParam, String>() {
				/**
				 * (non-Javadoc)
				 * 
				 * @see com.google.common.base.Function#apply(java.lang.Object)
				 */
				@Override
				public String apply(QueryParam input) {
					return input == null ? null : input.getQueryParamString();
				}
			}));
		}
	}

	/**
	 * query parameter of endpoint
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static interface QueryParam {
		/**
		 * get query parameter string, return null when the parameter can be
		 * ignored
		 * 
		 * @return
		 */
		String getQueryParamString();
	}

	/**
	 * abstract query parameter
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static abstract class AbstractQueryParam<V> implements QueryParam {
		private String key = null;
		private V value = null;

		public AbstractQueryParam(String key, V value) {
			assert Strings.nullToEmpty(key).trim().length() != 0 : "Key of query parameter cannot be null or blank.";

			this.setKey(key);
			this.setValue(value);
		}

		/**
		 * @return the key
		 */
		public String getKey() {
			return key;
		}

		/**
		 * @param key
		 *            the key to set
		 */
		protected void setKey(String key) {
			this.key = key;
		}

		/**
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * @param value
		 *            the value to set
		 */
		protected void setValue(V value) {
			this.value = value;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder.QueryParam#getQueryParamString()
		 */
		@Override
		public String getQueryParamString() {
			String strValue = this.valueToString(this.getValue());
			if (Strings.nullToEmpty(strValue).length() == 0) {
				// no value or it is empty when cast value to string
				return null;
			} else {
				return key + "=" + strValue;
			}
		}

		/**
		 * value to string
		 * 
		 * @param value
		 *            value
		 * @return string
		 */
		protected abstract String valueToString(V value);
	}

	/**
	 * simple query parameter
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class SimpleQueryParam extends AbstractQueryParam<String> {
		public SimpleQueryParam(String key, String value) {
			super(key, value);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder.AbstractQueryParam#valueToString(java.lang.Object)
		 */
		@Override
		protected String valueToString(String value) {
			return value;
		}
	}

	/**
	 * query parameter of set values.
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class SetQueryParam extends AbstractQueryParam<Set<String>> {
		public SetQueryParam(String key, Set<String> value) {
			super(key, value);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder.AbstractQueryParam#valueToString(java.lang.Object)
		 */
		@Override
		protected String valueToString(Set<String> value) {
			if (value == null || value.size() == 0) {
				return null;
			} else {
				return Joiner.on(",").skipNulls().join(value);
			}
		}
	}
}
