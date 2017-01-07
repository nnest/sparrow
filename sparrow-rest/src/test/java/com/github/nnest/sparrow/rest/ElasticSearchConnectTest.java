/**
 * 
 */
package com.github.nnest.sparrow.rest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.nnest.sparrow.DefaultElasticClient;
import com.github.nnest.sparrow.DefaultElasticCommandExecutorRepository;
import com.github.nnest.sparrow.DefaultElasticSettings;
import com.github.nnest.sparrow.ElasticClient;
import com.github.nnest.sparrow.ElasticCommandException;
import com.github.nnest.sparrow.ElasticCommandExecutorCreator;
import com.github.nnest.sparrow.ElasticCommandExecutorRepository;
import com.github.nnest.sparrow.ElasticDocumentAnalyzer;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.ElasticHost;
import com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer;
import com.google.common.collect.Lists;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ElasticSearchConnectTest {
	@Test
	public void test() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		TwitterTweet tt = new TwitterTweet();
		tt.setId("1");
		tt.setUser("kimchy");
		tt.setPostDate("2009-11-15T14:12:12");
		tt.setMessage("trying out Elasticsearch");

		client.index(tt);
	}

	private ElasticClient createClient() {
		DefaultElasticSettings settings = new DefaultElasticSettings();
		settings.setHosts(Lists.newArrayList(new ElasticHost("localhost")));
		ElasticCommandExecutorCreator creator = new RestElasticCommandExecutorCreator();
		ElasticCommandExecutorRepository repository = new DefaultElasticCommandExecutorRepository(settings, creator);
		ElasticDocumentAnalyzer analyzer = new AnnotatedElasticDocumentAnalyzer();
		ElasticClient client = new DefaultElasticClient(repository, analyzer);
		return client;
	}
}
