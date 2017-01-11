/**
 * 
 */
package com.github.nnest.sparrow.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * versioning, optimistic lock
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ElasticVersioning {
}
