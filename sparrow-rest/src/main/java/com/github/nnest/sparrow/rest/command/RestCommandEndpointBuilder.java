/**
 * 
 */
package com.github.nnest.sparrow.rest.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
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
		return buildEndpoint(documentDescriptor, idValue, null);
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
	 * @return endpoint
	 */
	public static String buildEndpoint(ElasticDocumentDescriptor documentDescriptor, String idValue,
			String endpointCommand) {
		return buildEndpoint(documentDescriptor.getIndex(), documentDescriptor.getType(), idValue, endpointCommand);
	}

	/**
	 * build endpoint by given parameters.
	 * 
	 * @param index
	 *            index name
	 * @param type
	 *            type name
	 * @param idValue
	 *            id value
	 * @param endpointCommand
	 *            endpoint command
	 * @return endpoint
	 */
	public static String buildEndpoint(String index, String type, String idValue, String endpointCommand) {
		return buildEndpoint(Sets.newHashSet(index), Sets.newHashSet(type), idValue, endpointCommand);
	}

	/**
	 * build endpoint by given parameters
	 * 
	 * @param indices
	 *            index names
	 * @param types
	 *            type names
	 * @param idValue
	 *            id value
	 * @param endpointCommand
	 *            endpoint command
	 * @return endpoint
	 */
	public static String buildEndpoint(Set<String> indices, Set<String> types, String idValue, String endpointCommand) {
		List<String> parts = new LinkedList<String>();
		if (indices != null && indices.size() > 0) {
			Joiner joiner = Joiner.on(",").skipNulls();
			String indicesString = joiner.join(indices);
			if (Strings.nullToEmpty(indicesString).trim().length() != 0) {
				parts.add(indicesString);
				if (types != null && types.size() > 0) {
					String typesString = joiner.join(types);
					if (Strings.nullToEmpty(typesString).trim().length() != 0) {
						parts.add(typesString);
					}
				}
			}
		}
		if (idValue != null) {
			parts.add(idValue);
		}
		parts.add(endpointCommand);
		return Joiner.on('/').skipNulls().join(parts);
	}

	/**
	 * transform query parameters to string map
	 * 
	 * @param params
	 *            parameters
	 * @return map
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> transformQueryParameters(Set<QueryParam> params) {
		if (params == null || params.size() == 0) {
			return null;
		} else {
			Map<String, String> map = new HashMap<>();
			for (QueryParam param : params) {
				map.put(param.getKey(), param.getValueAsString());
			}
			return map;
		}
	}

	/**
	 * transform query parameters to string map
	 * 
	 * @param params
	 *            parameters
	 * @return map
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> transformQueryParameters(QueryParam... params) {
		if (params == null || params.length == 0) {
			return null;
		} else {
			Map<String, String> map = new HashMap<>();
			for (QueryParam param : params) {
				map.put(param.getKey(), param.getValueAsString());
			}
			return map;
		}
	}

	/**
	 * query parameter of endpoint
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static interface QueryParam<V> {
		/**
		 * get value as string
		 * 
		 * @return string value
		 */
		String getValueAsString();

		/**
		 * get key of param
		 * 
		 * @return key
		 */
		String getKey();
	}

	/**
	 * abstract query parameter
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static abstract class AbstractQueryParam<V> implements QueryParam<V> {
		private String key = null;
		private V value = null;

		public AbstractQueryParam(String key, V value) {
			assert Strings.nullToEmpty(key).trim().length() != 0 : "Key of query parameter cannot be null or blank.";

			this.setKey(key);
			this.setValue(value);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder.QueryParam#getKey()
		 */
		@Override
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
		 * @see com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder.QueryParam#getValueAsString()
		 */
		@Override
		public String getValueAsString() {
			return this.getValue();
		}
	}

	/**
	 * version query parameter
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class VersionQueryParam extends SimpleQueryParam {
		public VersionQueryParam(String value) {
			super("version", value);
		}

		/**
		 * with version
		 * 
		 * @param value
		 *            value
		 * @return new VersionQueryParam
		 */
		public static VersionQueryParam withVersion(String value) {
			return new VersionQueryParam(value);
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
		 * @see com.github.nnest.sparrow.rest.command.RestCommandEndpointBuilder.QueryParam#getValueAsString()
		 */
		@Override
		public String getValueAsString() {
			Set<String> values = this.getValue();
			if (values == null || values.size() == 0) {
				return null;
			} else {
				return Joiner.on(",").skipNulls().join(values);
			}
		}
	}
}
