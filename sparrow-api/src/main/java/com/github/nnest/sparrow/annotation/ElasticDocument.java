/**
 * 
 */
package com.github.nnest.sparrow.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * annotate a class as an elastic search document.<br>
 * in this class,<br>
 * <ol>
 * <li>must and only have an {@linkplain ElasticId},</li>
 * <li>fields are not auto detected, only if it is annotated by
 * {@linkplain ElasticId} or {@linkplain ElasticField},</li>
 * <li>{@linkplain ElasticId} and {@linkplain ElasticField} can be annotated on
 * field or method. if on method, method must follow the java bean standard
 * getter/setter format. Only one time for per field, like spring data
 * bean,</li>
 * <li>if don't want to keep value in elastic, annotate field or method with
 * {@linkplain ElasticIngored},</li>
 * <li>any getter/setter (must existed both) which not be annotated, is default
 * persist to elastic,</li>
 * </ol>
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ElasticDocument {
	/**
	 * the index name
	 * 
	 * @return
	 */
	String index();

	/**
	 * the type name
	 * 
	 * @return
	 */
	String type();
}
