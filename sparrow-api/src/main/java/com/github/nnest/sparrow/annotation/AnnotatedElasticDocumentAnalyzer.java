/**
 * 
 */
package com.github.nnest.sparrow.annotation;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.github.nnest.sparrow.AbstractElasticDocumentAnalyzer;
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
	private static PropertyDetective detective = new PropertyDetectiveChain(Arrays.asList(new IdProperrtyDetective(),
			new IgnoredPropertyDetective(), new VersionPropertyDetective(), new NormalPropertyDetective()));

	private Map<Class<?>, ElasticDocumentDescriptor> descriptorMap = new ConcurrentHashMap<Class<?>, ElasticDocumentDescriptor>();

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.AbstractElasticDocumentAnalyzer#doAnalysis(java.lang.Class)
	 */
	@Override
	protected ElasticDocumentDescriptor doAnalysis(Class<?> documentType) {
		{
			ElasticDocumentDescriptor descriptor = descriptorMap.get(documentType);
			if (descriptor != null) {
				return descriptor;
			}
		}

		ElasticDocumentDescriptor descriptor = readToDocumentDescriptor(documentType);
		descriptorMap.put(documentType, descriptor);
		return descriptor;
	}

	/**
	 * read document type to descriptor
	 * 
	 * @param documentType
	 *            document type
	 * @return document descriptor
	 */
	protected ElasticDocumentDescriptor readToDocumentDescriptor(Class<?> documentType) {
		AnnotatedElasticDocumentDescriptor descriptor = new AnnotatedElasticDocumentDescriptor();
		ElasticDocument doc = documentType.getAnnotation(ElasticDocument.class);
		descriptor.setDocumentClass(documentType);
		descriptor.setDocument(doc);

		Field[] fields = documentType.getDeclaredFields();
		for (Field field : fields) {
			detective.detect(field, descriptor);
		}
		Method[] methods = documentType.getDeclaredMethods();
		for (Method method : methods) {
			detective.detect(method, descriptor);
		}
		return descriptor;
	}

	/**
	 * detective chain
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class PropertyDetectiveChain implements PropertyDetective {
		private List<PropertyDetective> detectives = null;

		public PropertyDetectiveChain(List<PropertyDetective> detectives) {
			this.detectives = detectives;
		}

		/**
		 * @return the detectives
		 */
		protected List<PropertyDetective> getDetectives() {
			return detectives;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#detect(java.lang.reflect.Field,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		public boolean detect(Field field, AnnotatedElasticDocumentDescriptor descriptor) {
			for (PropertyDetective detective : this.getDetectives()) {
				if (detective.detect(field, descriptor)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#detect(java.lang.reflect.Method,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		public boolean detect(Method method, AnnotatedElasticDocumentDescriptor descriptor) {
			for (PropertyDetective detective : this.getDetectives()) {
				if (detective.detect(method, descriptor)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * property descriptor detective
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static interface PropertyDetective {
		/**
		 * detect field, write to descriptor if detected
		 * 
		 * @param field
		 *            field
		 * @param descriptor
		 *            descriptor
		 * @return true if detected
		 */
		public boolean detect(Field field, AnnotatedElasticDocumentDescriptor descriptor);

		/**
		 * detect method, write to descriptor if detected
		 * 
		 * @param method
		 *            method
		 * @param descriptor
		 *            descriptor
		 * @return true if detected
		 */
		public boolean detect(Method method, AnnotatedElasticDocumentDescriptor descriptor);
	}

	/**
	 * abstract property detective
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static abstract class AbstractPropertyDetective implements PropertyDetective {
		/**
		 * get property name from method name
		 * 
		 * @param method
		 *            method
		 * @param mustbe
		 *            must get property, if method is not getter/setter, throw
		 *            exception
		 * @return property name. might not found, then return
		 *         {@linkplain Optional#empty()}
		 */
		protected Optional<String> getPropertyName(Method method, boolean mustbe) {
			String methodName = method.getName();
			String propertyName = null;
			if (methodName.startsWith("set")) {
				propertyName = methodName.substring(3);
			} else if (methodName.startsWith("get")) {
				propertyName = methodName.substring(3);
			} else if (methodName.startsWith("is")) {
				propertyName = methodName.substring(2);
			} else if (mustbe) {
				throw new ElasticDocumentValidationException(ErrorCodes.ERR_ILLEGAL_FIELD_ASSIGN,
						String.format("Method[%1$s] name should be a java bean getter or setter.", method));
			} else {
				return Optional.empty();
			}

			return Optional.of(propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1));
		}
	}

	/**
	 * id property detective
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class IdProperrtyDetective extends AbstractPropertyDetective {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#detect(java.lang.reflect.Method,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		public boolean detect(Method method, AnnotatedElasticDocumentDescriptor descriptor) {
			ElasticId id = method.getAnnotation(ElasticId.class);
			if (id != null) {
				descriptor.setIdField(this.getPropertyName(method, true).get());
				return true;
			} else {
				return false;
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#detect(java.lang.reflect.Field,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		public boolean detect(Field field, AnnotatedElasticDocumentDescriptor descriptor) {
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
	public static class IgnoredPropertyDetective extends AbstractPropertyDetective {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#detect(java.lang.reflect.Method,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		public boolean detect(Method method, AnnotatedElasticDocumentDescriptor descriptor) {
			ElasticIgnored ignored = method.getAnnotation(ElasticIgnored.class);
			if (ignored != null) {
				descriptor.registerAsIgnored(this.getPropertyName(method, true).get());
				return true;
			} else {
				return false;
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#detect(java.lang.reflect.Field,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		public boolean detect(Field field, AnnotatedElasticDocumentDescriptor descriptor) {
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
	 * Version property detective
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class VersionPropertyDetective extends AbstractPropertyDetective {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#detect(java.lang.reflect.Method,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		public boolean detect(Method method, AnnotatedElasticDocumentDescriptor descriptor) {
			ElasticVersioning ver = method.getAnnotation(ElasticVersioning.class);
			if (ver != null) {
				descriptor.setVersionField(this.getPropertyName(method, true).get());
				return true;
			} else {
				return false;
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#detect(java.lang.reflect.Field,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		public boolean detect(Field field, AnnotatedElasticDocumentDescriptor descriptor) {
			ElasticVersioning ver = field.getAnnotation(ElasticVersioning.class);
			if (ver != null) {
				descriptor.setVersionField(field.getName());
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
	public static class NormalPropertyDetective extends AbstractPropertyDetective {
		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#detect(java.lang.reflect.Method,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		public boolean detect(Method method, AnnotatedElasticDocumentDescriptor descriptor) {
			ElasticField fieldAnnotation = method.getAnnotation(ElasticField.class);
			if (fieldAnnotation != null) {
				descriptor.registerField(this.getPropertyName(method, true).get(), fieldAnnotation);
			} else {
				Optional<String> property = this.getPropertyName(method, false);
				if (property.isPresent()) {
					String propertyName = property.get();
					try {
						PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName,
								method.getDeclaringClass());
						if ((propertyDescriptor.getReadMethod().getModifiers() & Modifier.PUBLIC) != 0
								&& (propertyDescriptor.getWriteMethod().getModifiers() & Modifier.PUBLIC) != 0) {
							descriptor.registerField(propertyName, fieldAnnotation);
						}
					} catch (IntrospectionException e) {
						// no getter/setter
						// ignored
					}
				}
			}
			// always return false
			return false;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer.PropertyDetective#detect(java.lang.reflect.Field,
		 *      com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentDescriptor)
		 */
		@Override
		public boolean detect(Field field, AnnotatedElasticDocumentDescriptor descriptor) {
			ElasticField fieldAnnotation = field.getAnnotation(ElasticField.class);
			if (fieldAnnotation != null) {
				descriptor.registerField(field.getName(), fieldAnnotation);
			}
			// always return false
			return false;
		}
	}
}
