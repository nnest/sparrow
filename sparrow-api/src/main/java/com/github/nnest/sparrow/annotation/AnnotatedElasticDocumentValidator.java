/**
 * 
 */
package com.github.nnest.sparrow.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
					this.checkIdFieldType(field);
					if (idProperty != null) {
						throw new ElasticDocumentValidationException(ErrorCodes.ERR_DUPLICATED_ID, String.format(
								"Duplicated ids[%1s, %2s] found on document[%3s].", idProperty, field.getName(), type));
					}
					idProperty = field.getName();
				}
			}

			Method[] methods = type.getDeclaredMethods();
			for (Method method : methods) {
				ElasticId id = method.getAnnotation(ElasticId.class);
				if (id != null) {
					// check id on correct method
					String name = this.checkMethod(type, method, id);
					if (idProperty != null) {
						throw new ElasticDocumentValidationException(ErrorCodes.ERR_DUPLICATED_ID, String
								.format("Duplicated ids[%1s, %2s] found on document[%3s].", idProperty, method, type));
					}
					idProperty = name;
				} else {
					ElasticField field = method.getAnnotation(ElasticField.class);
					if (field != null) {
						this.checkMethod(type, method, field);
					}
				}
			}

			if (idProperty == null) {
				throw new ElasticDocumentValidationException(ErrorCodes.ERR_ID_NOT_FOUND,
						String.format("Id not found on document[%1s]", type.getName()));
			}
		}
	}

	/**
	 * check method modifier
	 * 
	 * @param method
	 */
	protected void checkMethodModifier(Method method) {
		if (method.getModifiers() != Modifier.PUBLIC) {
			throw new ElasticDocumentValidationException(ErrorCodes.ERR_METHOD_NOT_PUBLIC,
					String.format("Method[%1s] is illegal for assign, it must be public", method));
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
	 * @param type
	 * @param method
	 * @param annotation
	 * @return
	 */
	protected String checkMethod(Class<?> type, Method method, Annotation annotation) {
		String errorCode = annotation instanceof ElasticId ? ErrorCodes.ERR_ILLEGAL_ID_ASSIGN
				: ErrorCodes.ERR_ILLEGAL_FIELD_ASSIGN;

		this.checkMethodModifier(method);

		String methodName = method.getName();
		if ("getClass".equals(methodName)) {
			throw new ElasticDocumentValidationException(errorCode, String.format(
					"Method[getClass] is illegal for assign on document[%1s]", method.getDeclaringClass().getName()));
		}

		int length = methodName.length();
		if (length <= 2) {
			throw new ElasticDocumentValidationException(errorCode, String
					.format("Method[%1s] is illgeal for assign, should be a java bean getter or setter.", method));
		}

		String propertyName = null;
		String pairName = null;
		Class<?>[] pairParamTypes = null;
		// at least 3 digits
		if (methodName.startsWith("is")) {
			if (method.getParameterCount() != 0) {
				throw new ElasticDocumentValidationException(errorCode,
						String.format("Method[%1s] is illgeal for assign, should be a java bean getter.", method));
			}
			this.checkReturnType(annotation, method);
			propertyName = methodName.substring(2);
			pairName = "set" + propertyName;
			pairParamTypes = new Class<?>[] { method.getReturnType() };
		} else if (methodName.startsWith("get")) {
			if (method.getParameterCount() != 0) {
				throw new ElasticDocumentValidationException(errorCode,
						String.format("Method[%1s] is illgeal for assign, should be a java bean getter.", method));
			}
			this.checkReturnType(annotation, method);
			propertyName = methodName.substring(3);
			pairName = "set" + propertyName;
			pairParamTypes = new Class<?>[] { method.getReturnType() };
		} else if (methodName.startsWith("set")) {
			// getX, must returns something
			if (method.getParameterCount() != 1 || method.getReturnType() != Void.class) {
				throw new ElasticDocumentValidationException(errorCode,
						String.format("Method[%1s] is illgeal for assign, should be a java bean setter.", method));
			}
			this.checkParamType(annotation, method);
			propertyName = methodName.substring(3);
			pairName = "get" + propertyName;
			pairParamTypes = null;
		} else {
			throw new ElasticDocumentValidationException(errorCode, String
					.format("Method[%1s] is illgeal for assign, should be a java bean getter or setter.", method));
		}

		try {
			Method pairMethod = type.getDeclaredMethod(pairName, pairParamTypes);
			// if pair method found, check its modifier
			this.checkMethodModifier(pairMethod);
		} catch (Exception e) {
			throw new ElasticDocumentValidationException(errorCode,
					String.format("Method[%1s] is illegal for assign, no pair method found.", method), e);
		}

		return propertyName;
	}

	protected void checkParamType(Annotation annotation, Method method) {
		if (annotation instanceof ElasticId) {
			this.checkIdParamType(method);
		} else if (annotation instanceof ElasticField) {
			this.checkFieldParamType(method);
		}
	}

	protected void checkFieldParamType(Method method) {
		Class<?> type = method.getParameterTypes()[0];
		if (type == Void.class || type == null) {
			throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_FIELD_ASSIGN,
					String.format("Method[%1s] is illgeal for assign, parameter type cannot be void.", method));
		}
	}

	protected void checkIdParamType(Method method) {
		Class<?> type = method.getParameterTypes()[0];
		if (type != Integer.class && type != Long.class && type != String.class) {
			throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_ID_ASSIGN, String.format(
					"Method[%1s] is illgeal for assign, parameter type must be Integer, Long or String.", method));
		}
	}

	protected void checkReturnType(Annotation annotation, Method method) {
		if (annotation instanceof ElasticId) {
			this.checkIdReturnType(method);
		} else if (annotation instanceof ElasticField) {
			this.checkFieldReturnType(method);
		}
	}

	protected void checkFieldReturnType(Method method) {
		Class<?> type = method.getReturnType();
		if (type == Void.class || type == null) {
			throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_FIELD_ASSIGN,
					String.format("Method[%1s] is illgeal for assign, return type cannot be void.", method));
		}
	}

	protected void checkIdReturnType(Method method) {
		Class<?> type = method.getReturnType();
		if (type != Integer.class && type != Long.class && type != String.class) {
			throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_ID_ASSIGN, String
					.format("Method[%1s] is illgeal for assign, return type must be Integer, Long or String.", method));
		}
	}

	protected void checkIdFieldType(Field field) {
		Class<?> type = field.getType();
		if (type != Integer.class && type != Long.class && type != String.class) {
			throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_ID_ASSIGN,
					String.format(
							"Field[%1s] is illgeal for assign ElasticId, return type must be Integer, Long or String.",
							field.getName()));
		}
	}
}
