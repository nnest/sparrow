/**
 * 
 */
package com.github.nnest.sparrow.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.github.nnest.sparrow.AbstractElasticDocumentValidator;
import com.github.nnest.sparrow.ElasticDocumentValidationException;
import com.github.nnest.sparrow.ErrorCodes;
import com.google.common.base.Strings;

/**
 * annotated elastic document validator
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class AnnotatedElasticDocumentValidator extends AbstractElasticDocumentValidator {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.AbstractElasticDocumentValidator#doValidate(java.lang.Object)
	 */
	@Override
	protected void doValidate(Object document) {
		if (document == null)
			return;

		Class<?> type = document.getClass();
		ElasticDocument elasticDoc = type.getAnnotation(ElasticDocument.class);
		if (elasticDoc == null) {
			// no annotated
			return;
		} else {
			this.checkDocument(elasticDoc, type);

			String idProperty = null;

			Field[] fields = type.getDeclaredFields();
			for (Field field : fields) {
				ElasticId id = field.getAnnotation(ElasticId.class);
				if (id != null) {
					if (!this.isIdTypeValid(field.getType())) {
						throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_ID_ASSIGN,
								String.format(
										"Field[%1s] is illgeal for assign ElasticId, return type must be Integer, Long or String.",
										field.getName()));
					}
					if (idProperty != null) {
						throw new ElasticDocumentValidationException(ErrorCodes.ERR_DUPLICATED_ID,
								String.format("Duplicated ids[%1s, %2s] found on document[%3s].", idProperty,
										field.getName(), type.getName()));
					}
					idProperty = field.getName();
				}
			}

			Method[] methods = type.getDeclaredMethods();
			for (Method method : methods) {
				ElasticId id = method.getAnnotation(ElasticId.class);
				if (id != null) {
					// check id on correct method
					String name = this.guessIdName(method);
					if (idProperty != null) {
						throw new ElasticDocumentValidationException(ErrorCodes.ERR_DUPLICATED_ID,
								String.format("Duplicated ids[%1s, %2s] found on document[%3s].", idProperty, method,
										type.getName()));
					}
					idProperty = name;
				}
			}

			if (idProperty == null) {
				throw new ElasticDocumentValidationException(ErrorCodes.ERR_ID_NOT_FOUND,
						String.format("Id not found on document[%1s]", type.getName()));
			}
		}
	}

	/**
	 * check document
	 * 
	 * @param elasticDoc
	 * @param clazz
	 */
	protected void checkDocument(ElasticDocument elasticDoc, Class<?> clazz) {
		String index = elasticDoc.index();
		if (Strings.nullToEmpty(index).trim().length() == 0) {
			throw new ElasticDocumentValidationException(ErrorCodes.ERR_INDEX_NOT_FOUND,
					String.format("Index not found on document[%1s]", clazz));
		}

		String type = elasticDoc.type();
		if (Strings.nullToEmpty(type).trim().length() == 0) {
			throw new ElasticDocumentValidationException(ErrorCodes.ERR_TYPE_NOT_FOUND,
					String.format("Index not found on document[%1s]", clazz));
		}
	}

	/**
	 * guess field name by given method. if method is standard getter or setter,
	 * return its property name. otherwise return null, getClass returns null
	 * 
	 * @param method
	 * @return
	 */
	protected String guessIdName(Method method) {
		String methodName = method.getName();
		if ("getClass".equals(methodName)) {
			throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_ID_ASSIGN, String.format(
					"Cannot assign ElasticId to getClass on document[%1s]", method.getDeclaringClass().getName()));
		}

		int length = methodName.length();
		if (length <= 3) {
			throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_ID_ASSIGN,
					String.format("Method[%1s] is illgeal for assign ElasticId on class[%2s]", method,
							method.getDeclaringClass().getName()));
		}

		// at least 4 digits
		if (methodName.startsWith("get")) {
			if (method.getParameterCount() != 0) {
				throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_ID_ASSIGN, String
						.format("Method[%1s] is illgeal for assign ElasticId, should be a java bean getter.", method));
			}
			Class<?> returnType = method.getReturnType();
			if (!isIdTypeValid(returnType)) {
				throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_ID_ASSIGN,
						String.format(
								"Method[%1s] is illgeal for assign ElasticId, return type must be Integer, Long or String.",
								method));
			}
			// getX, must returns something
			return methodName.substring(3);
		} else if (methodName.startsWith("set")) {
			// getX, must returns something
			if (method.getParameterCount() != 1 || method.getReturnType() != Void.class) {
				throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_ID_ASSIGN, String
						.format("Method[%1s] is illgeal for assign ElasticId, should be a java bean setter.", method));
			}
			Class<?> paramType = method.getParameterTypes()[0];
			if (!isIdTypeValid(paramType)) {
				throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_ID_ASSIGN,
						String.format(
								"Method[%1s] is illgeal for assign ElasticId, return type must be Integer, Long or String.",
								method));
			}
			return methodName.substring(3);
		} else {
			throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_ID_ASSIGN,
					String.format("Method[%1s] is illgeal for assign ElasticId on class[%2s]", method,
							method.getDeclaringClass().getName()));
		}
	}

	/**
	 * check id type, must be {@linkplain Integer}, {@linkplain Long} or
	 * {@linkplain String}
	 * 
	 * @param type
	 * @return
	 */
	protected boolean isIdTypeValid(Class<?> type) {
		return type == Integer.class || type == Long.class || type == String.class;
	}
}
