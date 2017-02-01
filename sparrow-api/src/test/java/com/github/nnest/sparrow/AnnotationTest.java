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
		validator.validate(NoAnnotatedDocument.class);
	}

	@Test
	public void test002NoId() {
		thrown.expect(this.createExceptionMatcher(ElasticErrorCodes.ERR_ID_NOT_FOUND));

		AnnotatedElasticDocumentValidator validator = createValidator();
		validator.validate(AnnotatedDocumentNoId.class);
	}

	@Test
	public void test003DuplicatedId() {
		thrown.expect(this.createExceptionMatcher(ElasticErrorCodes.ERR_DUPLICATED_ID));

		AnnotatedElasticDocumentValidator validator = createValidator();
		validator.validate(AnnotatedDocumentDuplicatedId.class);
	}

	@Test
	public void test004DuplicatedIdOnFields() {
		thrown.expect(this.createExceptionMatcher(ElasticErrorCodes.ERR_DUPLICATED_ID));

		AnnotatedElasticDocumentValidator validator = createValidator();
		validator.validate(AnnotatedDocumentDuplicatedIdOnFields.class);
	}

	@Test
	public void test005InvalidIdAssignOnField() {
		thrown.expect(this.createExceptionMatcher(ElasticErrorCodes.ERR_ILLEGAL_ID_ASSIGN));

		AnnotatedElasticDocumentValidator validator = createValidator();
		validator.validate(AnnotatedDocumentInvalidIdAssignOnField.class);
	}

	@Test
	public void test006InvalidIdAssignOnGetter() {
		thrown.expect(this.createExceptionMatcher(ElasticErrorCodes.ERR_ILLEGAL_ID_ASSIGN));

		AnnotatedElasticDocumentValidator validator = createValidator();
		validator.validate(AnnotatedDocumentInvalidIdAssignOnGetter.class);
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
				description.appendText(String.format("Error code should be [%1$s]", code));
			}
		};
	}
}
