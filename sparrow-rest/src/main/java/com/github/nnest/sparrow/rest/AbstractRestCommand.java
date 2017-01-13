/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.github.nnest.sparrow.DefaultElasticCommandResult;
import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandException;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticCommandResultData;
import com.github.nnest.sparrow.ElasticCommandResultHandler;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.google.common.collect.Lists;

/**
 * abstract rest command
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractRestCommand<C extends ElasticCommand> implements RestCommand<C> {
	private static Map<Class<?>, DocumentFieldValueVisitor> idVisitors = new ConcurrentHashMap<Class<?>, DocumentFieldValueVisitor>();
	private static Map<Class<?>, DocumentFieldValueVisitor> versionVisitors = new ConcurrentHashMap<Class<?>, DocumentFieldValueVisitor>();

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.RestCommand#performRequest(org.elasticsearch.client.RestClient,
	 *      com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	public ElasticCommandResult performRequest(RestClient restClient, C command)
			throws ElasticCommandException, ElasticExecutorException {
		RestRequest request = this.convertToRestRequest(command);
		Response response;
		try {
			response = restClient.performRequest(request.getMethod(), request.getEndpoint(), request.getParams(),
					request.getEntity(), request.getHeaders());
		} catch (IOException e) {
			throw new ElasticCommandException(String.format("Fail to perform request with command[%1$s]", command), e);
		}
		return this.convertToCommandResult(response, command);
	}

	/**
	 * convert rest response to result
	 * 
	 * @param response
	 *            response of rest client
	 * @param command
	 *            command to execute
	 * @return command execution result
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	protected ElasticCommandResult convertToCommandResult(Response response, C command)
			throws ElasticExecutorException {
		HttpEntity entity = response.getEntity();
		try {
			return new DefaultElasticCommandResult(command, this.readResponse(command, entity.getContent()));
		} catch (ElasticExecutorException e) {
			throw e;
		} catch (Exception e) {
			throw new ElasticExecutorException("Fail to read data from response.", e);
		}
	}

	/**
	 * read result data from input stream
	 * 
	 * @param command
	 *            command
	 * @param stream
	 *            input stream
	 * @return result data
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	protected abstract ElasticCommandResultData readResponse(C command, InputStream stream)
			throws ElasticExecutorException;

	/**
	 * convert command to rest request
	 * 
	 * @param command
	 *            command to execute
	 * @return rest request
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	protected abstract RestRequest convertToRestRequest(C command) throws ElasticExecutorException;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.RestCommand#performRequestAsync(org.elasticsearch.client.RestClient,
	 *      com.github.nnest.sparrow.ElasticCommand,
	 *      com.github.nnest.sparrow.ElasticCommandResultHandler)
	 */
	@Override
	public void performRequestAsync(RestClient restClient, C command,
			ElasticCommandResultHandler commandResultHandler) {
		try {
			RestRequest request = this.convertToRestRequest(command);
			ResponseListener responseListener = this.getResponseListener(command, commandResultHandler);
			restClient.performRequestAsync(request.getMethod(), request.getEndpoint(), request.getParams(),
					request.getEntity(), responseListener, request.getHeaders());
		} catch (ElasticExecutorException e) {
			commandResultHandler.handleFail(e);
		}
	}

	/**
	 * get response listener from command result handler
	 * 
	 * @param command
	 *            command to execute
	 * @param commandResultHandler
	 *            command execution result handler
	 * @return response listener
	 */
	protected ResponseListener getResponseListener(C command, ElasticCommandResultHandler commandResultHandler) {
		return new ResponseListener() {
			/**
			 * (non-Javadoc)
			 * 
			 * @see org.elasticsearch.client.ResponseListener#onSuccess(org.elasticsearch.client.Response)
			 */
			@Override
			public void onSuccess(Response response) {
				try {
					commandResultHandler.handleSuccess(convertToCommandResult(response, command));
				} catch (ElasticExecutorException e) {
					this.onFailure(e);
				}
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.elasticsearch.client.ResponseListener#onFailure(java.lang.Exception)
			 */
			@Override
			public void onFailure(Exception exception) {
				commandResultHandler.handleFail(exception);
			}
		};
	}

	/**
	 * get id value of given document by given id field
	 * 
	 * @param document
	 *            document
	 * @param idField
	 *            id field name
	 * @return value of id field, convert to string
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	protected String getIdValue(Object document, String idField) throws ElasticExecutorException {
		return this.getFieldValue(document, idField, idVisitors);
	}

	/**
	 * get version value of given document by given version field
	 * 
	 * @param document
	 *            document
	 * @param versionField
	 *            version field name
	 * @return value of version field, convert to string
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	protected String getVersionValue(Object document, String versionField) throws ElasticExecutorException {
		return this.getFieldValue(document, versionField, versionVisitors);
	}

	/**
	 * get field value of given document
	 * 
	 * @param document
	 *            document
	 * @param fieldName
	 *            field name
	 * @param visitors
	 *            field visitors map
	 * @return value of field, convert to string
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	protected String getFieldValue(Object document, String fieldName, Map<Class<?>, DocumentFieldValueVisitor> visitors)
			throws ElasticExecutorException {
		Class<?> documentType = document.getClass();
		DocumentFieldValueVisitor visitor = visitors.get(documentType);
		if (visitor == null) {
			visitor = new DocumentFieldValueVisitor(documentType, fieldName);
			visitors.put(documentType, visitor);
		}
		Object value = visitor.getValue(document);
		if (value == null) {
			return null;
		} else if (value instanceof Date) {
			return String.valueOf(((Date) value).getTime());
		} else {
			return value.toString();
		}
	}

	/**
	 * set id value to given document by given id field
	 * 
	 * @param document
	 *            document
	 * @param idField
	 *            id field name
	 * @param idValue
	 *            id value
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	protected void setIdValue(Object document, String idField, String idValue) throws ElasticExecutorException {
		Class<?> documentType = document.getClass();
		DocumentFieldValueVisitor visitor = idVisitors.get(documentType);
		if (visitor == null) {
			visitor = new DocumentFieldValueVisitor(documentType, idField);
			idVisitors.put(documentType, visitor);
		}
		visitor.setValue(document, idValue);
	}

	/**
	 * create {@linkplain ObjectMapper} according to given document descriptor
	 * 
	 * @param documentDescriptor
	 *            document descriptor
	 * @return object mapper
	 */
	protected ObjectMapper createObjectMapper(ElasticDocumentDescriptor documentDescriptor) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper
				.setVisibility(new DocumentFieldVisibilityChecker(mapper.getVisibilityChecker(), documentDescriptor));
	}

	/**
	 * document field value visitor
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class DocumentFieldValueVisitor {
		private static final Object[] NO_PARAM_OBJECTS = new Object[0];

		private Field field = null;
		private Method getter = null;
		private Method setter = null;

		public DocumentFieldValueVisitor(Class<?> documentType, String fieldName) throws ElasticExecutorException {
			this.findGetterSetter(documentType, fieldName);
			this.findField(documentType, fieldName);

			boolean hasField = this.field != null;

			if (!hasField && getter == null) {
				throw new ElasticExecutorException(
						String.format("No getter for field[%1$s] on document[%2$s]", fieldName, documentType));
			}

			if (!hasField && setter == null) {
				throw new ElasticExecutorException(
						String.format("No setter for field[%1$s] on document[%2$s]", fieldName, documentType));
			}
		}

		/**
		 * find field
		 * 
		 * @param documentClass
		 *            document class
		 * @param idFieldName
		 *            id field name
		 */
		protected void findField(Class<?> documentClass, String idFieldName) {
			try {
				this.field = documentClass.getDeclaredField(idFieldName);
				this.field.setAccessible(true);
			} catch (Exception ex) {
				// ignored
			}
		}

		/**
		 * find getter and setter
		 * 
		 * @param documentClass
		 *            document class
		 * @param idFieldName
		 *            id field name
		 */
		protected void findGetterSetter(Class<?> documentClass, String idFieldName) {
			String methodName = idFieldName.substring(0, 1).toUpperCase() + idFieldName.substring(1);
			String getterName = "get" + methodName;
			String setterName = "set" + methodName;
			Map<Integer, Method> setterMap = new HashMap<Integer, Method>();
			Method[] methods = documentClass.getDeclaredMethods();
			for (Method method : methods) {
				String name = method.getName();
				if (name.equals(getterName)) {
					// found getter
					if (method.getParameterCount() == 0 && method.getReturnType() != Void.class) {
						// exactly the getter
						this.getter = method;
						this.getter.setAccessible(true);
					}
				} else if (name.equals(setterName)) {
					// found setter
					if (method.getReturnType() == Void.class && method.getParameterCount() == 1) {
						// no return, only one parameter, exactly the setter
						Class<?> paramType = method.getParameterTypes()[0];
						if (paramType == String.class) {
							// string as highest score
							setterMap.put(Integer.valueOf(100), method);
						} else if (paramType == Long.class) {
							// Long as highest score
							setterMap.put(Integer.valueOf(10), method);
						} else if (paramType == Integer.class) {
							// Long as highest score
							setterMap.put(Integer.valueOf(1), method);
						}
					}
				}
			}

			// found the highest score, as setter
			if (setterMap.size() > 0) {
				List<Integer> scores = Lists.newArrayList(setterMap.keySet());
				Collections.sort(scores);
				this.setter = setterMap.get(scores.get(scores.size() - 1));
				this.setter.setAccessible(true);
			}
		}

		/**
		 * set value to given document
		 * 
		 * @param document
		 *            document
		 * @param idValue
		 *            id value of document
		 */
		public void setValue(Object document, String idValue) throws ElasticExecutorException {
			try {
				if (this.setter != null) {
					Object value = this.castValue(idValue, this.setter.getParameterTypes()[0]);
					this.setter.invoke(document, value);
				} else if (this.field != null) {
					Object value = this.castValue(idValue, this.field.getType());
					this.field.set(document, value);
				} else {
					throw new ElasticExecutorException(
							String.format("Fail to find visitor on document[%1$s]", document.getClass()));
				}
			} catch (ElasticExecutorException e) {
				throw e;
			} catch (Exception e) {
				throw new ElasticExecutorException(
						String.format("Fail to set id value on document[%1$s]", document.getClass()), e);
			}
		}

		/**
		 * cast string value to given class
		 * 
		 * @param value
		 *            string value
		 * @param type
		 *            target class, should be string, long or integer
		 * @return value casted
		 * @throws ElasticExecutorException
		 *             executor exception
		 */
		protected Object castValue(String value, Class<?> type) throws ElasticExecutorException {
			if (type == String.class) {
				return value;
			} else if (type == Long.class) {
				return Long.valueOf(value);
			} else if (type == Integer.class) {
				return Integer.valueOf(value);
			} else {
				throw new ElasticExecutorException(String.format("Fail to cast id value[%1$s] to [%2$s]", value, type));
			}
		}

		/**
		 * get value of given document
		 * 
		 * @param document
		 *            document
		 * @return id value of document
		 * @throws ElasticExecutorException
		 *             executor exception
		 */
		public Object getValue(Object document) throws ElasticExecutorException {
			try {
				if (this.getter != null) {
					return this.getter.invoke(document, NO_PARAM_OBJECTS);
				} else if (this.field != null) {
					return this.field.get(document);
				} else {
					throw new ElasticExecutorException(
							String.format("Fail to find visitor on document[%1$s]", document.getClass()));
				}
			} catch (ElasticExecutorException e) {
				throw e;
			} catch (Exception e) {
				throw new ElasticExecutorException(
						String.format("Fail to visit id value on document[%1$s]", document.getClass()), e);
			}
		}
	}

	/**
	 * Rest request object
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class RestRequest {
		private static final Header[] NO_HEADER = new Header[0];

		private String method = null;
		private String endpoint = null;
		private Map<String, String> params = null;
		private HttpEntity entity = null;
		private Header[] headers = null;

		/**
		 * @return the method
		 */
		public String getMethod() {
			return method;
		}

		/**
		 * @param method
		 *            the method to set
		 */
		public void setMethod(String method) {
			this.method = method;
		}

		/**
		 * @return the endpoint
		 */
		public String getEndpoint() {
			return endpoint;
		}

		/**
		 * @param endpoint
		 *            the endpoint to set
		 */
		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}

		/**
		 * @return the params
		 */
		public Map<String, String> getParams() {
			return params == null ? Collections.emptyMap() : params;
		}

		/**
		 * @param params
		 *            the params to set
		 */
		public void setParams(Map<String, String> params) {
			this.params = params;
		}

		/**
		 * @return the entity
		 */
		public HttpEntity getEntity() {
			return entity;
		}

		/**
		 * @param entity
		 *            the entity to set
		 */
		public void setEntity(HttpEntity entity) {
			this.entity = entity;
		}

		/**
		 * @return the headers
		 */
		public Header[] getHeaders() {
			return headers == null ? NO_HEADER : headers;
		}

		/**
		 * @param headers
		 *            the headers to set
		 */
		public void setHeaders(Header[] headers) {
			this.headers = headers;
		}
	}

	/**
	 * document field visibility checker
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	@SuppressWarnings("rawtypes")
	public static class DocumentFieldVisibilityChecker extends VisibilityChecker.Std {
		private static final long serialVersionUID = 6321985313833406329L;

		private VisibilityChecker parentChecker = null;
		private ElasticDocumentDescriptor documentDescriptor = null;

		public DocumentFieldVisibilityChecker(VisibilityChecker parentChecker,
				ElasticDocumentDescriptor documentDescriptor) {
			super(Visibility.DEFAULT);
			this.parentChecker = parentChecker;
			this.documentDescriptor = documentDescriptor;
		}

		/**
		 * @return the parentChecker
		 */
		public VisibilityChecker getParentChecker() {
			return parentChecker;
		}

		/**
		 * @return the documentDescriptor
		 */
		public ElasticDocumentDescriptor getDocumentDescriptor() {
			return documentDescriptor;
		}

		/**
		 * the visibility of given property name
		 * 
		 * @param name
		 *            property name
		 * @return true if visible
		 */
		protected boolean isPropertyVisible(String name) {
			ElasticDocumentDescriptor descriptor = this.getDocumentDescriptor();
			return descriptor.getIdField().equals(name) || descriptor.getFields().contains(name)
					|| name.equals(descriptor.getVersionField());
		}

		/**
		 * get property name by given method. method should be getter or setter
		 * 
		 * @param method
		 *            method
		 * @return property name
		 */
		protected String getPropertyName(Method method) {
			String methodName = method.getName().substring(3);
			return methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
		}

		/**
		 * get property name by given method. method should be is-getter
		 * 
		 * @param method
		 *            method
		 * @return property name
		 */
		protected String getIsPropertyName(Method method) {
			String methodName = method.getName().substring(2);
			return methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std#isFieldVisible(java.lang.reflect.Field)
		 */
		@Override
		public boolean isFieldVisible(Field f) {
			return this.isPropertyVisible(f.getName()) && super.isFieldVisible(f);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std#isGetterVisible(java.lang.reflect.Method)
		 */
		@Override
		public boolean isGetterVisible(Method m) {
			return this.isPropertyVisible(this.getPropertyName(m)) && super.isGetterVisible(m);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std#isIsGetterVisible(java.lang.reflect.Method)
		 */
		@Override
		public boolean isIsGetterVisible(Method m) {
			return this.isPropertyVisible(this.getIsPropertyName(m)) && super.isIsGetterVisible(m);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std#isSetterVisible(java.lang.reflect.Method)
		 */
		@Override
		public boolean isSetterVisible(Method m) {
			return this.isPropertyVisible(this.getPropertyName(m)) && super.isSetterVisible(m);
		}
	}
}
