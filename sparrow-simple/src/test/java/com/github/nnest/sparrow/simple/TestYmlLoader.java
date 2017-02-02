/**
 * 
 */
package com.github.nnest.sparrow.simple;

import static org.junit.Assert.assertEquals;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
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
		assertEquals("/twitter/tweet/100", template.getEndpoint());

		template = context.find("index1").get();
		assertEquals("index1", template.getName());
		assertEquals("/twitter/tweet/101", template.getEndpoint());
	}

	@Test
	public void test002() {
		SimpleCommandTemplateContext context = new SimpleCommandTemplateContext(
				"/Users/brad.wu/Documents/GitHub/bradwoo8621/sparrow/sparrow-simple/src/test/resources/test-yml-loader.yml");
		context.loadTemplates();

		DefaultCommandExecutor executor = new DefaultCommandExecutor();
		executor.setTemplateContext(context);
		executor.setRestClientBuilder(RestClient.builder(new HttpHost("localhost", 9200)));

		executor.execute("index", null, new NoopCommandExecutionHandler());
	}
}
