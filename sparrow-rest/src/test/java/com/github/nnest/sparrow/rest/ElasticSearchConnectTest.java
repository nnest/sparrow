/**
 * 
 */
package com.github.nnest.sparrow.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.nnest.sparrow.DefaultElasticClient;
import com.github.nnest.sparrow.DefaultElasticCommandExecutorRepository;
import com.github.nnest.sparrow.DefaultElasticSettings;
import com.github.nnest.sparrow.ElasticClient;
import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandException;
import com.github.nnest.sparrow.ElasticCommandExecutorCreator;
import com.github.nnest.sparrow.ElasticCommandExecutorRepository;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticDocumentAnalyzer;
import com.github.nnest.sparrow.ElasticExecutorException;
import com.github.nnest.sparrow.ElasticHost;
import com.github.nnest.sparrow.annotation.AnnotatedElasticDocumentAnalyzer;
import com.github.nnest.sparrow.command.document.Create;
import com.github.nnest.sparrow.command.document.Exist;
import com.github.nnest.sparrow.command.document.ExistResultData;
import com.github.nnest.sparrow.command.document.Get;
import com.github.nnest.sparrow.command.document.GetResultData;
import com.github.nnest.sparrow.command.document.Index;
import com.github.nnest.sparrow.command.document.IndexResultData;
import com.github.nnest.sparrow.command.document.IndexResultType;
import com.github.nnest.sparrow.command.indices.DropIndex;
import com.google.common.collect.Lists;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ElasticSearchConnectTest {
	private static String autoCreatedId = null;

	@Test
	public void test000DropIndex() {
		try {
			ElasticClient client = createClient();
			client.execute(new DropIndex("twitter"));
		} catch (Exception e) {
			// ignore exception, delete it anyway
		}
	}

	@Test
	public void test001Index() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		TwitterTweet tt = new TwitterTweet();
		tt.setId("1");
		tt.setUser("1st User");
		tt.setPostDate("2017-01-08T14:12:12");
		tt.setMessage("Message from 1st user");

		ElasticCommand cmd = new Index(tt);
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		IndexResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertEquals(IndexResultType.CREATED, data.getResultType());
		TwitterTweet rtt = data.getDocument();
		assertTrue(rtt == tt);
	}

	@Test
	public void test002IndexNoId() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		TwitterTweet tt = new TwitterTweet();
		tt.setUser("2nd User");
		tt.setPostDate("2017-01-08T14:12:12");
		tt.setMessage("Message from 2nd user");

		ElasticCommand cmd = new Index(tt);
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		IndexResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertEquals(IndexResultType.CREATED, data.getResultType());
		TwitterTweet rtt = data.getDocument();
		assertTrue(rtt == tt);
		assertNotNull(rtt.getId());

		autoCreatedId = rtt.getId();
	}

	@Test
	public void test003Create() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		TwitterTweet tt = new TwitterTweet();
		tt.setId("3");
		tt.setUser("3rd User");
		tt.setPostDate("2017-01-08T14:12:12");
		tt.setMessage("Message from 3rd user");

		ElasticCommand cmd = new Create(tt);
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		IndexResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertEquals(IndexResultType.CREATED, data.getResultType());
		TwitterTweet rtt = data.getDocument();
		assertTrue(rtt == tt);
	}

	@Test
	public void test004CreateNoId() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		TwitterTweet tt = new TwitterTweet();
		tt.setUser("4th User");
		tt.setPostDate("2017-01-08T14:12:12");
		tt.setMessage("Message from 4th user");

		ElasticCommand cmd = new Create(tt);
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		IndexResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertEquals(IndexResultType.CREATED, data.getResultType());
		TwitterTweet rtt = data.getDocument();
		assertTrue(rtt == tt);
		assertNotNull(rtt.getId());
	}

	@Test(expected = ElasticCommandException.class)
	public void test005CreateFail() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		TwitterTweet tt = new TwitterTweet();
		tt.setId("3");
		tt.setUser("3rd User");
		tt.setPostDate("2017-01-08T14:12:12");
		tt.setMessage("Message from 3rd user");

		ElasticCommand cmd = new Create(tt);
		client.execute(cmd);
	}

	@Test
	public void test006Update1stUser() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		TwitterTweet tt = new TwitterTweet();
		tt.setId("1");
		tt.setUser("1st User Changed");
		tt.setPostDate("2017-01-08T14:12:12");
		tt.setMessage("Message from 1st user changed");

		ElasticCommand cmd = new Index(tt);
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		IndexResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertEquals(IndexResultType.UPDATED, data.getResultType());
		TwitterTweet rtt = data.getDocument();
		assertTrue(rtt == tt);
	}

	@Test
	public void test007Update2ndUser() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		TwitterTweet tt = new TwitterTweet();
		tt.setId(autoCreatedId);
		tt.setUser("2nd User Changed");
		tt.setPostDate("2017-01-08T14:12:12");
		tt.setMessage("Message from 2nd user changed");

		ElasticCommand cmd = new Index(tt);
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		IndexResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertEquals(IndexResultType.UPDATED, data.getResultType());
		TwitterTweet rtt = data.getDocument();
		assertTrue(rtt == tt);
	}

	@Test
	public void test008Get1stUser() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		ElasticCommand cmd = new Get(TwitterTweet.class, "1");
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		GetResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertTrue(TwitterTweet.class == data.getDocument().getClass());
		TwitterTweet tt = data.getDocument();
		assertEquals("1", tt.getId());
		assertEquals("1st User Changed", tt.getUser());
		assertEquals("2017-01-08T14:12:12", tt.getPostDate());
		assertEquals("Message from 1st user changed", tt.getMessage());
	}

	@Test
	public void test009Exists1stUser() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		ElasticCommand cmd = new Exist(TwitterTweet.class, "1");
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		ExistResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertTrue(data.isFound());
	}

	@Test
	public void test010ExistsUserFailed() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		ElasticCommand cmd = new Exist(TwitterTweet.class, "0");
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		ExistResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertFalse(data.isFound());
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
