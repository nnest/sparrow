/**
 * 
 */
package com.github.nnest.sparrow;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentValidator;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnotationTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void test001NoAnnotation() {
		AnnotatedElasticDocumentValidator validator = createValidator();
		NoAnnotatedDocument doc = new NoAnnotatedDocument();
		validator.validate(doc);
	}

	@Test
	public void test002NoId() {
		thrown.expect(this.createExceptionMatcher(ErrorCodes.ERR_ID_NOT_FOUND));

		AnnotatedElasticDocumentValidator validator = createValidator();
		AnnotatedDocumentNoId doc = new AnnotatedDocumentNoId();
		validator.validate(doc);
	}

	@Test
	public void test003DuplicatedId() {
		thrown.expect(this.createExceptionMatcher(ErrorCodes.ERR_DUPLICATED_ID));

		AnnotatedElasticDocumentValidator validator = createValidator();
		AnnotatedDocumentDuplicatedId doc = new AnnotatedDocumentDuplicatedId();
		validator.validate(doc);
	}

	private AnnotatedElasticDocumentValidator createValidator() {
		AnnotatedElasticDocumentValidator validator = new AnnotatedElasticDocumentValidator();
		return validator;
	}

	/**
	 * create exception matcher
	 * 
	 * @param code
	 * @return
	 */
	private Matcher<ElasticDocumentValidationException> createExceptionMatcher(final String code) {
		return new BaseMatcher<ElasticDocumentValidationException>() {
			/**
			 * (non-Javadoc)
			 * 
			 * @see org.hamcrest.Matcher#matches(java.lang.Object)
			 */
			@Override
			public boolean matches(Object item) {
				if (item == null || !(item instanceof ElasticDocumentValidationException)) {
					return false;
				}

				ElasticDocumentValidationException exception = (ElasticDocumentValidationException) item;
				return code.equals(exception.getCode());
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
			 */
			@Override
			public void describeTo(Description description) {
				description.appendText(String.format("Error code should be [%1s]", code));
			}
		};
	}
}
