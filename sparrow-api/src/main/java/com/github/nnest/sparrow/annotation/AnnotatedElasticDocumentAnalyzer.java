/**
 * 
 */
package com.github.nnest.sparrow.annotation;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.nnest.sparrow.AbstractElasticDocumentAnalyzer;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.ElasticDocumentValidationException;
import com.github.nnest.sparrow.ErrorCodes;

/**
 * annotated elastic document analyzer
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class AnnotatedElasticDocumentAnalyzer extends AbstractElasticDocumentAnalyzer {
	private Map<Class<?>, ElasticDocumentDescriptor> descriptorMap = new ConcurrentHashMap<Class<?>, ElasticDocumentDescriptor>();

	private static PropertyDetective idDetective = new IdProperrtyDetective();
	private static PropertyDetective ignoredDetective = new IgnoredPropertyDetective();
	private static PropertyDetective normalDetective = new NormalPropertyDetective();

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.AbstractElasticDocumentAnalyzer#getDocumentDescriptor(com.github.nnest.sparrow.ElasticCommandKind,
	 *      java.lang.Object)
	 */
	@Override
	protected ElasticDocumentDescriptor getDocumentDescriptor(ElasticCommandKind commandKind, Object document) {
		Class<?> docType = document.getClass();

		{
			ElasticDocumentDescriptor descriptor = descriptorMap.get(docType);
			if (descriptor != null) {
				return descriptor;
			}
		}

		ElasticDocumentDescriptor descriptor = readToDocumentDescriptor(docType);
		descriptorMap.put(docType, descriptor);
		return descriptor;
	}

	/**
	 * read document type to descriptor
	 * 
	 * @param docType
	 * @return
	 */
	protected ElasticDocumentDescriptor readToDocumentDescriptor(Class<?> docType) {
		AnnotatedElasticDocumentDescriptor descriptor = new AnnotatedElasticDocumentDescriptor();
		ElasticDocument doc = docType.getAnnotation(ElasticDocument.class);
		descriptor.setDocumentClass(docType);
		descriptor.setDocument(doc);

		Field[] fields = docType.getDeclaredFields();
		for (Field field : fields) {
			idDetective.detect(field, descriptor);
		}
		Method[] methods = docType.getDeclaredMethods();
		for (Method method : methods) {
			idDetective.detect(method, descriptor);
		}
		return descriptor;
	}

	/**
	 * property descriptor detective
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	private static interface PropertyDetective {
		/**
		 * detect field
		 * 
		 * @param field
		 * @param descriptor
		 */
		public void detect(Field field, AnnotatedElasticDocumentDescriptor descriptor);

		/**
		 * detect method
		 * 
		 * @param method
		 * @param descriptor
		 */
		public void detect(Method method, AnnotatedElasticDocumentDescriptor descriptor);

		/**
		 * get next detective
		 * 
		 * @return
		 */
		public PropertyDetective getNext();
	}

	/**
	 * abstract property detective
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	private static abstract class AbstractPropertyDetective implements PropertyDetective {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#detect(java.lang.reflect.Field,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		public void detect(Field field, AnnotatedElasticDocumentDescriptor descriptor) {
			if (!this.doDetect(field, descriptor)) {
				PropertyDetective next = this.getNext();
				if (next != null) {
					next.detect(field, descriptor);
				}
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#detect(java.lang.reflect.Method,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		public void detect(Method method, AnnotatedElasticDocumentDescriptor descriptor) {
			if (!this.doDetect(method, descriptor)) {
				PropertyDetective next = this.getNext();
				if (next != null) {
					next.detect(method, descriptor);
				}
			}
		}

		/**
		 * get property name from method name
		 * 
		 * @param methodName
		 * @param lowercaseFirst
		 * @return
		 */
		protected String getPropertyName(Method method, boolean lowercaseFirst) {
			String methodName = method.getName();
			String propertyName = null;
			if (methodName.startsWith("set")) {
				propertyName = methodName.substring(3);
			} else if (methodName.startsWith("get")) {
				propertyName = methodName.substring(3);
			} else if (methodName.startsWith("is")) {
				propertyName = methodName.substring(2);
			} else {
				throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_FIELD_ASSIGN,
						String.format("Method[%1s] name should be a java bean getter or setter.", method));
			}

			if (lowercaseFirst) {
				return propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1);
			} else {
				return propertyName;
			}
		}

		/**
		 * do detect, success return true, otherwise return false
		 * 
		 * @param method
		 * @param descriptor
		 * @return
		 */
		protected abstract boolean doDetect(Method method, AnnotatedElasticDocumentDescriptor descriptor);

		/**
		 * do detect, success return true, otherwise return false
		 * 
		 * @param field
		 * @param descriptor
		 * @return
		 */
		protected abstract boolean doDetect(Field field, AnnotatedElasticDocumentDescriptor descriptor);
	}

	/**
	 * id property detective
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	private static class IdProperrtyDetective extends AbstractPropertyDetective {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#getNext()
		 */
		@Override
		public PropertyDetective getNext() {
			return ignoredDetective;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.AbstractPropertyDetective#doDetect(java.lang.reflect.Method,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		protected boolean doDetect(Method method, AnnotatedElasticDocumentDescriptor descriptor) {
			ElasticId id = method.getAnnotation(ElasticId.class);
			if (id != null) {
				descriptor.setIdField(this.getPropertyName(method, true));
				return true;
			} else {
				return false;
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.AbstractPropertyDetective#doDetect(java.lang.reflect.Field,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		protected boolean doDetect(Field field, AnnotatedElasticDocumentDescriptor descriptor) {
			ElasticId id = field.getAnnotation(ElasticId.class);
			if (id != null) {
				descriptor.setIdField(field.getName());
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * ignored property detective
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	private static class IgnoredPropertyDetective extends AbstractPropertyDetective {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#getNext()
		 */
		@Override
		public PropertyDetective getNext() {
			return normalDetective;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.AbstractPropertyDetective#doDetect(java.lang.reflect.Method,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		protected boolean doDetect(Method method, AnnotatedElasticDocumentDescriptor descriptor) {
			ElasticIgnored ignored = method.getAnnotation(ElasticIgnored.class);
			if (ignored != null) {
				descriptor.registerAsIgnored(this.getPropertyName(method, true));
				return true;
			} else {
				return false;
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.AbstractPropertyDetective#doDetect(java.lang.reflect.Field,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		protected boolean doDetect(Field field, AnnotatedElasticDocumentDescriptor descriptor) {
			ElasticIgnored ignored = field.getAnnotation(ElasticIgnored.class);
			if (ignored != null) {
				descriptor.registerAsIgnored(field.getName());
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * normal property detective, always register the field into document
	 * descriptor
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	private static class NormalPropertyDetective extends AbstractPropertyDetective {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#getNext()
		 */
		@Override
		public PropertyDetective getNext() {
			return null;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.AbstractPropertyDetective#doDetect(java.lang.reflect.Method,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		protected boolean doDetect(Method method, AnnotatedElasticDocumentDescriptor descriptor) {
			ElasticField fieldAnnotation = method.getAnnotation(ElasticField.class);
			if (fieldAnnotation != null) {
				descriptor.registerField(this.getPropertyName(method, true), fieldAnnotation);
			} else {
				String propertyName = this.getPropertyName(method, false);
				try {
					new PropertyDescriptor(propertyName, method.getDeclaringClass());
					descriptor.registerField(propertyName, fieldAnnotation);
				} catch (IntrospectionException e) {
					// no getter/setter
					// ignored
				}
			}
			// always return false
			return false;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.AbstractPropertyDetective#doDetect(java.lang.reflect.Field,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		protected boolean doDetect(Field field, AnnotatedElasticDocumentDescriptor descriptor) {
			ElasticField fieldAnnotation = field.getAnnotation(ElasticField.class);
			descriptor.registerField(field.getName(), fieldAnnotation);
			// always return false
			return false;
		}
	}
}
