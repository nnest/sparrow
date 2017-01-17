/**
 * 
 */
package com.github.nnest.sparrow.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import com.github.nnest.sparrow.command.document.Delete;
import com.github.nnest.sparrow.command.document.DeleteResultData;
import com.github.nnest.sparrow.command.document.ElasticDocumentIncorrectVersionException;
import com.github.nnest.sparrow.command.document.ElasticDocumentNotFoundException;
import com.github.nnest.sparrow.command.document.Exist;
import com.github.nnest.sparrow.command.document.ExistResultData;
import com.github.nnest.sparrow.command.document.Get;
import com.github.nnest.sparrow.command.document.GetResultData;
import com.github.nnest.sparrow.command.document.Index;
import com.github.nnest.sparrow.command.document.IndexResultData;
import com.github.nnest.sparrow.command.document.IndexResultType;
import com.github.nnest.sparrow.command.document.MultiGet;
import com.github.nnest.sparrow.command.document.Update;
import com.github.nnest.sparrow.command.document.UpdateByScript;
import com.github.nnest.sparrow.command.document.UpdateResultData;
import com.github.nnest.sparrow.command.indices.DropIndex;
import com.github.nnest.sparrow.command.script.PainlessElasticScript;
import com.github.nnest.sparrow.rest.command.document.UpdateResponse;
import com.google.common.collect.Lists;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ElasticSearchConnectTest {
	private static String idOf2ndUser = null;

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
	public void test001Index1stUser() throws ElasticCommandException, ElasticExecutorException {
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
	public void test002Index2ndUserNoId() throws ElasticCommandException, ElasticExecutorException {
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

		idOf2ndUser = rtt.getId();
	}

	@Test
	public void test003Create3rdUser() throws ElasticCommandException, ElasticExecutorException {
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
	public void test004Create4thUserNoId() throws ElasticCommandException, ElasticExecutorException {
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

	@Test(expected = ElasticDocumentIncorrectVersionException.class)
	public void test005Create3rdUserFail() throws ElasticCommandException, ElasticExecutorException {
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
	public void test006IndexUpdate1stUser() throws ElasticCommandException, ElasticExecutorException {
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
	public void test007IndexUpdate2ndUser() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		TwitterTweet tt = new TwitterTweet();
		tt.setId(idOf2ndUser);
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

	@Test
	public void test011Get1stUserWithIncludes() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		ElasticCommand cmd = new Get(TwitterTweet.class, "1").withIncludes("id", "user");
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		GetResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertTrue(TwitterTweet.class == data.getDocument().getClass());
		TwitterTweet tt = data.getDocument();
		assertEquals("1", tt.getId());
		assertEquals("1st User Changed", tt.getUser());
		assertNull(tt.getPostDate());
		assertNull(tt.getMessage());
	}

	@Test
	public void test012Get1stUserWithExcludes() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		ElasticCommand cmd = new Get(TwitterTweet.class, "1").withExcludes("postDate", "message");
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		GetResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertTrue(TwitterTweet.class == data.getDocument().getClass());
		TwitterTweet tt = data.getDocument();
		assertEquals("1", tt.getId());
		assertEquals("1st User Changed", tt.getUser());
		assertNull(tt.getPostDate());
		assertNull(tt.getMessage());
	}

	@Test
	public void test013Delete1stUser() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		ElasticCommand cmd = new Delete(TwitterTweet.class, "1");
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		DeleteResultData data = result.getResultData();
		assertTrue(data.isSuccessful());

		cmd = new Exist(TwitterTweet.class, "1");
		result = client.execute(cmd);
		ExistResultData existResult = result.getResultData();
		assertTrue(existResult.isSuccessful());
		assertFalse(existResult.isFound());
	}

	@Test(expected = ElasticDocumentNotFoundException.class)
	public void test014Delete1stUserFailed() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		ElasticCommand cmd = new Delete(TwitterTweet.class, "1");
		client.execute(cmd);
	}

	@Test
	public void test015Update2ndUser() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		TwitterTweet tt = new TwitterTweet();
		tt.setId(idOf2ndUser);
		tt.setUser("2nd User Changed");
		tt.setPostDate("2017-01-08T14:12:12");
		tt.setMessage("Message from 2nd user changed");

		ElasticCommand cmd = new Update(tt).withDetectNoopChanged(false);
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);
		UpdateResultData updateResult = result.getResultData();
		assertTrue(updateResult.isSuccessful());
		assertFalse(updateResult.isNoopChanged());

		// get again for verification
		cmd = new Get(TwitterTweet.class, idOf2ndUser);
		result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		GetResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertTrue(TwitterTweet.class == data.getDocument().getClass());
		tt = data.getDocument();
		assertEquals(idOf2ndUser, tt.getId());
		assertEquals("2nd User Changed", tt.getUser());
		assertEquals("2017-01-08T14:12:12", tt.getPostDate());
		assertEquals("Message from 2nd user changed", tt.getMessage());

		// test data not changed, detect noop changed
		cmd = new Update(tt).withDetectNoopChanged(true);
		result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);
		updateResult = result.getResultData();
		assertTrue(updateResult.isSuccessful());
		assertTrue(updateResult.isNoopChanged());

		// test data changed, not detect noop changed
		cmd = new Update(tt).withDetectNoopChanged(false);
		result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);
		updateResult = result.getResultData();
		assertTrue(updateResult.isSuccessful());
		assertFalse(updateResult.isNoopChanged());

		// test data changed, detect noop changed
		tt.setPostDate("2017-01-08T23:12:12");
		cmd = new Update(tt).withDetectNoopChanged(true);
		result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);
		updateResult = result.getResultData();
		assertTrue(updateResult.isSuccessful());
		assertFalse(updateResult.isNoopChanged());
	}

	@Test
	public void test016Update5thUser() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		TwitterTweet tt = new TwitterTweet();
		tt.setId("5");
		tt.setUser("5th User");
		tt.setPostDate("2017-01-08T14:12:12");
		tt.setMessage("Message from 5th user");

		ElasticCommand cmd = new Update(tt).withAsUpsert(true);
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);
		UpdateResponse updateResult = result.getResultData();
		assertTrue(updateResult.isSuccessful());
		assertFalse(updateResult.isNoopChanged());
		assertEquals("created", updateResult.getResult());

		// get again for verification
		cmd = new Get(TwitterTweet.class, "5");
		result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		GetResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertTrue(TwitterTweet.class == data.getDocument().getClass());
		tt = data.getDocument();
		assertEquals("5", tt.getId());
		assertEquals("5th User", tt.getUser());
		assertEquals("2017-01-08T14:12:12", tt.getPostDate());
		assertEquals("Message from 5th user", tt.getMessage());

		// update again, and detect noop changed
		cmd = new Update(tt).withAsUpsert(true).withDetectNoopChanged(true);
		result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);
		updateResult = result.getResultData();
		assertTrue(updateResult.isSuccessful());
		assertTrue(updateResult.isNoopChanged());
		assertEquals("noop", updateResult.getResult());
	}

	@Test
	public void test017UpdateByScript5thUser() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		PainlessElasticScript script = new PainlessElasticScript()
				.withScript("ctx._source.postDate = \"2017-01-08T20:12:12\"");
		ElasticCommand cmd = new UpdateByScript(TwitterTweet.class, "5").withScript(script);
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);
		UpdateResponse updateResult = result.getResultData();
		assertTrue(updateResult.isSuccessful());
		assertFalse(updateResult.isNoopChanged());
		assertEquals("updated", updateResult.getResult());

		// get again for verification
		cmd = new Get(TwitterTweet.class, "5");
		result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		GetResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertTrue(TwitterTweet.class == data.getDocument().getClass());
		TwitterTweet tt = data.getDocument();
		assertEquals("5", tt.getId());
		assertEquals("5th User", tt.getUser());
		assertEquals("2017-01-08T20:12:12", tt.getPostDate());
		assertEquals("Message from 5th user", tt.getMessage());
	}

	@Test
	public void test018UpdateByScriptAndParamsMap5thUser() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		PainlessElasticScript script = new PainlessElasticScript().withScript("ctx._source.postDate = params.post")
				.withParam("post", "2017-01-08T20:12:12");
		ElasticCommand cmd = new UpdateByScript(TwitterTweet.class, "5").withScript(script);
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);
		UpdateResponse updateResult = result.getResultData();
		assertTrue(updateResult.isSuccessful());
		assertFalse(updateResult.isNoopChanged());
		assertEquals("updated", updateResult.getResult());

		// get again for verification
		cmd = new Get(TwitterTweet.class, "5");
		result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		GetResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertTrue(TwitterTweet.class == data.getDocument().getClass());
		TwitterTweet tt = data.getDocument();
		assertEquals("5", tt.getId());
		assertEquals("5th User", tt.getUser());
		assertEquals("2017-01-08T20:12:12", tt.getPostDate());
		assertEquals("Message from 5th user", tt.getMessage());
	}

	@Test
	public void test019UpdateByScriptAndDocument6thUser() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		TwitterTweet tt = new TwitterTweet();
		tt.setId("6");
		tt.setUser("6th User");
		tt.setPostDate("2017-01-08T14:12:12");
		tt.setMessage("Message from 6th user");

		// actually the script not run
		PainlessElasticScript script = new PainlessElasticScript().withScript("ctx._source.postDate = params.post")
				.withParam("post", "2017-01-08T20:12:12");
		ElasticCommand cmd = new UpdateByScript(tt).withScript(script);
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);
		UpdateResponse updateResult = result.getResultData();
		assertTrue(updateResult.isSuccessful());
		assertFalse(updateResult.isNoopChanged());
		assertEquals("created", updateResult.getResult());

		// get again for verification
		cmd = new Get(TwitterTweet.class, "6");
		result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		GetResultData data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertTrue(TwitterTweet.class == data.getDocument().getClass());
		tt = data.getDocument();
		assertEquals("6", tt.getId());
		assertEquals("6th User", tt.getUser());
		assertEquals("2017-01-08T14:12:12", tt.getPostDate());
		assertEquals("Message from 6th user", tt.getMessage());

		// actually document is useless, since document already exists, script
		// run
		cmd = new UpdateByScript(tt).withScript(script).withAsUpsert(true);
		result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);
		updateResult = result.getResultData();
		assertTrue(updateResult.isSuccessful());
		assertFalse(updateResult.isNoopChanged());
		assertEquals("updated", updateResult.getResult());

		// get again for verification
		cmd = new Get(TwitterTweet.class, "6");
		result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		data = result.getResultData();
		assertTrue(data.isSuccessful());
		assertTrue(TwitterTweet.class == data.getDocument().getClass());
		tt = data.getDocument();
		assertEquals("6", tt.getId());
		assertEquals("6th User", tt.getUser());
		assertEquals("2017-01-08T20:12:12", tt.getPostDate());
		assertEquals("Message from 6th user", tt.getMessage());
	}

	@Test
	public void test020MultiGet() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new MultiGet().withCommand(new Get(TwitterTweet.class, "3"));
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);
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
