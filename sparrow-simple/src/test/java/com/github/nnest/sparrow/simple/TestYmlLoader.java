/**
 * 
 */
package com.github.nnest.sparrow.simple;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class TestYmlLoader {
	@Test
	public void test001() {
		SimpleCommandTemplateContext context = new SimpleCommandTemplateContext(
				"/Users/brad.wu/Documents/GitHub/bradwoo8621/sparrow/sparrow-simple/src/test/resources/test-yml-loader.yml");
		context.loadTemplates();

		CommandTemplate template = context.find("index").get();
		assertEquals("index", template.getName());
		assertEquals("http://localhost:9200/twitter/tweet/100", template.getUrl());

		template = context.find("index1").get();
		assertEquals("index1", template.getName());
		assertEquals("http://localhost:9200/twitter/tweet/101", template.getUrl());
	}
}
