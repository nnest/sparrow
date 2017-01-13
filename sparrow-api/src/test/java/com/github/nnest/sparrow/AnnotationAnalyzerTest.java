/**
 * 
 */
package com.github.nnest.sparrow;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer;
import com.github.nnest.sparrow.annotation.ElasticDocument;
import com.github.nnest.sparrow.annotation.ElasticField;
import com.github.nnest.sparrow.annotation.ElasticId;
import com.github.nnest.sparrow.annotation.ElasticIgnored;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class AnnotationAnalyzerTest {
	@Test
	public void test() {
		AnnotatedElasticDocumentAnalyzer analyzer = new AnnotatedElasticDocumentAnalyzer();
		ElasticDocumentDescriptor descriptor = analyzer.analysis(TestBeanA.class);
		assertEquals("testIndex", descriptor.getIndex());
		assertEquals("testType", descriptor.getType());
		assertEquals("id", descriptor.getIdField());

		Set<String> fields = descriptor.getFields();
		assertEquals(3, fields.size());
	}

	@ElasticDocument(index = "testIndex", type = "testType")
	public static class TestBeanA {
		@ElasticId
		private String id = null;

		private String name = null;

		private String code = null;

		private String testProp1 = null;

		private String testProp2 = null;

		@ElasticIgnored
		private String testProp3 = null;

		@ElasticField
		private String testProp4 = null;

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id
		 *            the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the code
		 */
		public String getCode() {
			return code;
		}

		/**
		 * @param code
		 *            the code to set
		 */
		public void setCode(String code) {
			this.code = code;
		}

		/**
		 * @return the testProp1
		 */
		protected String getTestProp1() {
			return testProp1;
		}

		/**
		 * @param testProp1
		 *            the testProp1 to set
		 */
		public void setTestProp1(String testProp1) {
			this.testProp1 = testProp1;
		}

		/**
		 * @return the testProp2
		 */
		public String getTestProp2() {
			return testProp2;
		}

		/**
		 * @return the testProp3
		 */
		public String getTestProp3() {
			return testProp3;
		}

		/**
		 * @param testProp3
		 *            the testProp3 to set
		 */
		public void setTestProp3(String testProp3) {
			this.testProp3 = testProp3;
		}
	}
}
