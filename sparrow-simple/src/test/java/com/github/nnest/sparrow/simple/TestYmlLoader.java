/**
 * 
 */
package com.github.nnest.sparrow.simple;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.nnest.sparrow.simple.TwitterTweet.Topic;
import com.github.nnest.sparrow.simple.context.SimpleCommandTemplateContext;
import com.github.nnest.sparrow.simple.exec.AbstractJacksonCommandExecutionHandler;
import com.github.nnest.sparrow.simple.exec.DefaultCommandExecutor;
import com.github.nnest.sparrow.simple.exec.NoopCommandExecutionHandler;
import com.github.nnest.sparrow.simple.template.Document;
import com.github.nnest.sparrow.simple.token.AbstractTokens;
import com.github.nnest.sparrow.simple.token.Token;

import ognl.Ognl;
import ognl.OgnlException;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestYmlLoader {
	@Test
	public void test000() {
		String test = "x${abc${a}ux${}ix${b.c}";
		List<Token> tokens = AbstractTokens.parse(test);
		for (Token token : tokens) {
			System.out.println(token);
		}
	}

	@Test
	public void test001() {
		SimpleCommandTemplateContext context = new SimpleCommandTemplateContext("/test-yml-loader.yml");
		context.loadTemplates();

		CommandTemplate template = context.find("index-static").get();
		assertEquals("index-static", template.getName());
		assertEquals("/twitter/tweet/100", template.getEndpoint());

		template = context.find("index-dynamic").get();
		assertEquals("index-dynamic", template.getName());
		assertEquals("/${index}/${type}/${document.id}", template.getEndpoint());
	}

	@Test
	public void test002IndexStatic() {
		SimpleCommandTemplateContext context = new SimpleCommandTemplateContext("/test-yml-loader.yml");
		context.loadTemplates();

		DefaultCommandExecutor executor = new DefaultCommandExecutor();
		executor.setTemplateContext(context);
		executor.setRestClientBuilder(RestClient.builder(new HttpHost("localhost", 9200)));

		executor.execute("index-static", null, new NoopCommandExecutionHandler());
	}

	@Test
	public void test003IndexDynamic() {
		SimpleCommandTemplateContext context = new SimpleCommandTemplateContext("/test-yml-loader.yml");
		context.loadTemplates();

		DefaultCommandExecutor executor = new DefaultCommandExecutor();
		executor.setTemplateContext(context);
		executor.setRestClientBuilder(RestClient.builder(new HttpHost("localhost", 9200)));

		Document doc = new Document();
		doc.setIndex("twitter");
		doc.setType("tweet");
		TwitterTweet tt = new TwitterTweet();
		tt.setId(101l);
		tt.setUser("gavin");
		tt.setPostDate(new Date());
		tt.setMessage("Message from Gavin");
		Topic topic = new Topic();
		topic.setId("998");
		topic.setLiked(Boolean.TRUE);
		// tt.setTopic(Lists.newArrayList(topic));
		doc.setDocument(tt);
		executor.execute("index-dynamic", doc, new NoopCommandExecutionHandler());
	}

	@Test
	public void test004Result() {
		SimpleCommandTemplateContext context = new SimpleCommandTemplateContext("/test-yml-loader.yml");
		context.loadTemplates();

		DefaultCommandExecutor executor = new DefaultCommandExecutor();
		executor.setTemplateContext(context);
		executor.setRestClientBuilder(RestClient.builder(new HttpHost("localhost", 9200)));

		Document doc = new Document();
		doc.setIndex("twitter");
		doc.setType("tweet");
		TwitterTweet tt = new TwitterTweet();
		tt.setId(101l);
		tt.setUser("gavin");
		tt.setPostDate(new Date());
		tt.setMessage("Message from Gavin");
		doc.setDocument(tt);
		executor.execute("index-dynamic", doc, new AbstractJacksonCommandExecutionHandler() {
			/**
			 * (non-Javadoc)
			 * 
			 * @see com.github.nnest.sparrow.simple.CommandExecutionHandler#onFailure(java.lang.Exception)
			 */
			@Override
			public void onFailure(Exception exception) {
				exception.printStackTrace();
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see com.github.nnest.sparrow.simple.exec.AbstractJacksonCommandExecutionHandler#doOnSuccessNoContent()
			 */
			@Override
			protected void doOnSuccessNoContent() {
				// never occurred
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see com.github.nnest.sparrow.simple.exec.AbstractJacksonCommandExecutionHandler#doOnSuccess(java.lang.Object)
			 */
			@Override
			protected void doOnSuccess(Object response) {
				System.out.println(response);
				try {
					System.out.println(Ognl.getValue("_shards", response));
				} catch (OgnlException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
