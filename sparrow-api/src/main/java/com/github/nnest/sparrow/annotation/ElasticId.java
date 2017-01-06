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
 * annotate a field or method as id of elastic document.<br>
 * method must be standard java bean format getter or setter.<br>
 * type of this id must be {@linkplain Integer}, {@linkplain Long} or
 * {@linkplain String}. according to elastic rule, value of this id can be null.
 * then elastic will auto generate a UUID as id, in such scenario, type of this
 * id must be {@linkplain String}, then {@linkplain ElasticClient} can put the
 * auto generated UUID back into field.<br>
 * getter or setter is not necessary, if not exists, client visit value of id
 * via reflection.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface ElasticId {
}
