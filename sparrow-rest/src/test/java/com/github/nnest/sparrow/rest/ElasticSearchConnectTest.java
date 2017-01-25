/**
 * 
 */
package com.github.nnest.sparrow.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

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
import com.github.nnest.sparrow.command.document.Query;
import com.github.nnest.sparrow.command.document.Update;
import com.github.nnest.sparrow.command.document.UpdateByScript;
import com.github.nnest.sparrow.command.document.UpdateResultData;
import com.github.nnest.sparrow.command.document.query.attrs.ExampleTextConjunction;
import com.github.nnest.sparrow.command.document.query.attrs.ParseFeatureFlag;
import com.github.nnest.sparrow.command.document.query.attrs.RegexpFlag;
import com.github.nnest.sparrow.command.document.query.attrs.ScoreMode;
import com.github.nnest.sparrow.command.document.query.attrs.ZeroTermsQuery;
import com.github.nnest.sparrow.command.document.query.attrs.fuzzy.Fuzziness;
import com.github.nnest.sparrow.command.document.query.attrs.fuzzy.ValuedFuzziness;
import com.github.nnest.sparrow.command.document.query.attrs.rewrite.ConstantRewrite;
import com.github.nnest.sparrow.command.document.query.attrs.rewrite.TopTermsRewrite;
import com.github.nnest.sparrow.command.document.query.attrs.rewrite.TopTermsRewrite.DefaultTopTermsRewriteType;
import com.github.nnest.sparrow.command.document.query.attrs.score.NestedScoreFunction;
import com.github.nnest.sparrow.command.document.query.attrs.score.RandomScoreFunction;
import com.github.nnest.sparrow.command.document.query.attrs.score.WeightFunction;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.CombinationMinimumShouldMatch;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MultipleCombinationMinimumShouldMatch;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.NumericMinimumShouldMatch;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.PercentageMinimumShouldMatch;
import com.github.nnest.sparrow.command.document.query.compound.Bool;
import com.github.nnest.sparrow.command.document.query.compound.Boosting;
import com.github.nnest.sparrow.command.document.query.compound.ConstantScore;
import com.github.nnest.sparrow.command.document.query.compound.DisMax;
import com.github.nnest.sparrow.command.document.query.compound.FunctionScore;
import com.github.nnest.sparrow.command.document.query.fulltext.CommonTerms;
import com.github.nnest.sparrow.command.document.query.fulltext.Match;
import com.github.nnest.sparrow.command.document.query.fulltext.MatchPhrase;
import com.github.nnest.sparrow.command.document.query.fulltext.MatchPhrasePrefix;
import com.github.nnest.sparrow.command.document.query.fulltext.MultiMatchBestFields;
import com.github.nnest.sparrow.command.document.query.fulltext.MultiMatchCrossFields;
import com.github.nnest.sparrow.command.document.query.fulltext.MultiMatchMostFields;
import com.github.nnest.sparrow.command.document.query.fulltext.MultiMatchPhrase;
import com.github.nnest.sparrow.command.document.query.fulltext.MultiMatchPhrasePrefix;
import com.github.nnest.sparrow.command.document.query.fulltext.QueryString;
import com.github.nnest.sparrow.command.document.query.fulltext.SimpleQueryString;
import com.github.nnest.sparrow.command.document.query.term.Prefix;
import com.github.nnest.sparrow.command.document.query.term.Range;
import com.github.nnest.sparrow.command.document.query.term.Regexp;
import com.github.nnest.sparrow.command.document.query.term.Term;
import com.github.nnest.sparrow.command.document.query.term.TermLevelQueryExist;
import com.github.nnest.sparrow.command.document.query.term.Terms;
import com.github.nnest.sparrow.command.document.query.term.TermsLookupExternal;
import com.github.nnest.sparrow.command.document.query.term.TermsLookupExternal.ExternalDocumentTerm;
import com.github.nnest.sparrow.command.document.query.term.Wildcard;
import com.github.nnest.sparrow.command.indices.DropIndex;
import com.github.nnest.sparrow.command.script.PainlessElasticScript;
import com.github.nnest.sparrow.rest.command.document.MultiGetResponse;
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

		cmd = new Get(TwitterTweet.class, idOf2ndUser);
		result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		GetResultData getResult = result.getResultData();
		assertTrue(getResult.isSuccessful());
		assertTrue(TwitterTweet.class == getResult.getDocument().getClass());
		tt = getResult.getDocument();
		assertEquals(idOf2ndUser, tt.getId());
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

	@Test(expected = ElasticExecutorException.class)
	public void test004Create4thUserNoId() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();
		TwitterTweet tt = new TwitterTweet();
		tt.setUser("4th User");
		tt.setPostDate("2017-01-08T14:12:12");
		tt.setMessage("Message from 4th user");

		ElasticCommand cmd = new Create(tt);
		client.execute(cmd);
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

		ElasticCommand cmd = new MultiGet().withCommand(TwitterTweet.class, "3", "4", "5");
		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		MultiGetResponse response = result.getResultData();
		assertEquals(3, response.getInnerCommandCount());
		assertEquals(2, response.getSuccessCount());
		assertEquals(1, response.getFailCount());
		assertTrue(response.isPartialSuccessful());

		assertEquals("3", ((TwitterTweet) response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test021Match() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new Match("user") //
						.with(ExampleTextConjunction.OR) //
						.with(Fuzziness.AUTO) //
						.with(new MultipleCombinationMinimumShouldMatch(
								new CombinationMinimumShouldMatch(1, PercentageMinimumShouldMatch.valueOf(1)),
								new CombinationMinimumShouldMatch(2, NumericMinimumShouldMatch.valueOf(2)))) //
						.with(ConstantRewrite.CONSTANT_SCORE) //
						.with(ZeroTermsQuery.ALL) //
						.withAnalyzerName("standard") //
						.withBoost(new BigDecimal("1.2")) //
						.withCutoffFrequency(new BigDecimal("0.001")) //
						.withFieldName("message") //
						.withLenient(Boolean.TRUE) //
						.withMaxExpansions(50) //
						.withPrefixLength(2) //
						.withSlop(1) //
						.withTranspositions(Boolean.TRUE) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void test022MatchPhrase() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new MatchPhrase("user") //
						.withAnalyzerName("standard") //
						.withBoost(new BigDecimal("1.2")) //
						.withFieldName("message") //
						.withSlop(1) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test023MatchPhrasePrefix() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new MatchPhrasePrefix("user") //
						.withAnalyzerName("standard") //
						.withBoost(new BigDecimal("1.2")) //
						.withFieldName("message") //
						.withSlop(1) //
						.withMaxExpansions(50) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test024CommonTerms() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new CommonTerms("user") //
						.withAnalyzerName("standard") //
						.withBoost(new BigDecimal("1.2")) //
						.withCutoffFrequency(new BigDecimal("0.001")) //
						.withDisableCoord(Boolean.TRUE) //
						.withFieldName("message") //
						.withHigh(ExampleTextConjunction.OR) //
						.withHigh(NumericMinimumShouldMatch.valueOf(1)) //
						.withLow(ExampleTextConjunction.OR) //
						.withLow(PercentageMinimumShouldMatch.valueOf(1)) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test025QueryString() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new QueryString("user") //
						.with(ExampleTextConjunction.OR)//
						.with(Fuzziness.AUTO) //
						.with(NumericMinimumShouldMatch.valueOf(1)) //
						.with(ConstantRewrite.CONSTANT_SCORE_BOOLEAN) //
						.withAllFields(Boolean.FALSE) //
						.withAllowLeadingWildcard(Boolean.TRUE) //
						.withAnalyzerName("standard") //
						.withAnalyzeWildcard(Boolean.TRUE) //
						.withAutoGeneratePhraseQueries(Boolean.TRUE) //
						.withBoost(new BigDecimal("1.2")) //
						.withEnablePositionIncrement(Boolean.TRUE) //
						.withFieldNames("message") //
						.withFuzzyMaxExpansions(50) //
						.withFuzzyPrefixLength(1) //
						.withLenient(Boolean.TRUE) //
						// this not supported since elastic search issue
						// .withMaxDeterminizedStates(10000) //
						.withPhraseSlop(0) //
						.withQuoteFieldSuffix(".*") //
						.withSplitOnWhitespace(Boolean.FALSE) //
						.withTimeZone("-08:00") //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test026SimpleQueryString() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new SimpleQueryString("user") //
						.with(ExampleTextConjunction.OR)//
						.with(NumericMinimumShouldMatch.valueOf(1)) //
						.withAllFields(Boolean.FALSE) //
						.withAnalyzerName("standard") //
						.withAnalyzeWildcard(Boolean.TRUE) //
						.withBoost(new BigDecimal("1.2")) //
						.withFieldNames("message") //
						.withFlags(ParseFeatureFlag.AND, ParseFeatureFlag.OR, ParseFeatureFlag.PREFIX) //
						.withLenient(Boolean.TRUE) //
						.withQuoteFieldSuffix(".*") //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test027BestFields() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new MultiMatchBestFields("user") //
						.with(ExampleTextConjunction.OR)//
						.with(ValuedFuzziness.valueOf(1)) //
						.with(NumericMinimumShouldMatch.valueOf(1)) //
						.with(ConstantRewrite.SCORING_BOOLEAN) //
						.with(ZeroTermsQuery.NONE) //
						.withAnalyzerName("standard") //
						.withBoost(new BigDecimal("1.2")) //
						.withCutoffFrequency(new BigDecimal("0.001")) //
						.withFieldNames("message") //
						.withLenient(Boolean.TRUE) //
						.withMaxExpansions(50) //
						.withPrefixLength(1) //
						.withSlop(1) //
						.withTieBreaker(new BigDecimal("0.3")) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test028MostFields() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new MultiMatchMostFields("user") //
						.with(ExampleTextConjunction.OR)//
						.with(ValuedFuzziness.valueOf(1)) //
						.with(NumericMinimumShouldMatch.valueOf(1)) //
						.with(ConstantRewrite.SCORING_BOOLEAN) //
						.with(ZeroTermsQuery.NONE) //
						.withAnalyzerName("standard") //
						.withBoost(new BigDecimal("1.2")) //
						.withCutoffFrequency(new BigDecimal("0.001")) //
						.withFieldNames("message") //
						.withLenient(Boolean.TRUE) //
						.withMaxExpansions(50) //
						.withPrefixLength(1) //
						.withSlop(1) //
						.withTieBreaker(new BigDecimal("0.3")) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test029CrossFields() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new MultiMatchCrossFields("user") //
						.with(ExampleTextConjunction.OR)//
						.with(NumericMinimumShouldMatch.valueOf(1)) //
						.with(new TopTermsRewrite(DefaultTopTermsRewriteType.BLENDED_FREQUENCY, 10)) //
						.with(ZeroTermsQuery.NONE) //
						.withAnalyzerName("standard") //
						.withBoost(new BigDecimal("1.2")) //
						.withCutoffFrequency(new BigDecimal("0.001")) //
						.withFieldNames("message") //
						.withLenient(Boolean.TRUE) //
						.withMaxExpansions(50) //
						.withPrefixLength(1) //
						.withSlop(1) //
						.withTieBreaker(new BigDecimal("0.3")) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test030MultiPhrase() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new MultiMatchPhrase("user") //
						.with(ExampleTextConjunction.OR)//
						.with(NumericMinimumShouldMatch.valueOf(1)) //
						.with(new TopTermsRewrite(DefaultTopTermsRewriteType.BOOST, 10)) //
						.with(ZeroTermsQuery.NONE) //
						.withAnalyzerName("standard") //
						.withBoost(new BigDecimal("1.2")) //
						.withCutoffFrequency(new BigDecimal("0.001")) //
						.withFieldNames("message") //
						.withLenient(Boolean.TRUE) //
						.withMaxExpansions(50) //
						.withPrefixLength(1) //
						.withSlop(1) //
						.withTieBreaker(new BigDecimal("0.3")) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test031MultiPhrasePrefix() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new MultiMatchPhrasePrefix("user") //
						.with(ExampleTextConjunction.OR)//
						.with(NumericMinimumShouldMatch.valueOf(1)) //
						.with(new TopTermsRewrite(DefaultTopTermsRewriteType.DEFAULT, 10)) //
						.with(ZeroTermsQuery.NONE) //
						.withAnalyzerName("standard") //
						.withBoost(new BigDecimal("1.2")) //
						.withCutoffFrequency(new BigDecimal("0.001")) //
						.withFieldNames("message") //
						.withLenient(Boolean.TRUE) //
						.withMaxExpansions(50) //
						.withPrefixLength(1) //
						.withSlop(1) //
						.withTieBreaker(new BigDecimal("0.3")) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test032Term() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new Term("message") //
						.withBoost(new BigDecimal("1.2")) //
						.withExampleText("user") //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test033Terms() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new Terms("message") //
						.withBoost(new BigDecimal("1.2")) //
						.withExampleTexts("user") //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test034TermsLookupExternal() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new TermsLookupExternal("message") //
						.withBoost(new BigDecimal("1.2")) //
						.withTerms(new ExternalDocumentTerm("twitter", "tweet", "1", "followers")) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test035Range() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new Range("message") //
						.withBoost(new BigDecimal("1.2")) //
						.withMax("100000") //
						.withMin("1000") //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test036Exist() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new TermLevelQueryExist("*ser") //
						.withBoost(new BigDecimal("1.2")) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test037Prefix() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new Prefix("user") //
						.withBoost(new BigDecimal("1.2")) //
						.with(ConstantRewrite.CONSTANT_SCORE) //
						.withExampleText("2") //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test038Regexp() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new Regexp("user") //
						.withBoost(new BigDecimal("1.2")) //
						.with(ConstantRewrite.CONSTANT_SCORE) //
						.withExampleText("2.*") //
						.withMaxDeterminizedStates(20000) //
						.withFlags(RegexpFlag.INTERSECTION, RegexpFlag.COMPLEMENT, RegexpFlag.EMPTY) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test039Wildcard() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new Wildcard("user") //
						.withBoost(new BigDecimal("1.2")) //
						.with(ConstantRewrite.CONSTANT_SCORE) //
						.withExampleText("2.*") //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test040ConstantScore() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new ConstantScore( //
						new Term("message") //
								.withExampleText("user") //
				).withBoost(new BigDecimal("1.2")) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test041Boosting() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new Boosting() //
						.withNegative(new Term("message") //
								.withExampleText("user") //
						) //
						.withPositive(new Term("message") //
								.withExampleText("user") //
						) //
						.withBoost(new BigDecimal("1.2")) //
						.withNegativeBoost(new BigDecimal("1.3")) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test042Bool() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new Bool() //
						.withMust(new Term("message") //
								.withExampleText("user") //
						) //
						.withShould(new Term("message") //
								.withExampleText("user") //
						) //
						.withMustNot(new Term("message") //
								.withExampleText("user") //
						) //
						.withFilter(new Term("message") //
								.withExampleText("user") //
						) //
						.withBoost(new BigDecimal("1.2")) //
						.with(NumericMinimumShouldMatch.valueOf(1)) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test043DisMax() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new DisMax(new Term("message") //
						.withExampleText("user")) //
								.withBoost(new BigDecimal("1.2")) //
								.withTieBreaker(new BigDecimal("0.3")) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
	}

	@Test
	public void test044FunctionScore() throws ElasticCommandException, ElasticExecutorException {
		ElasticClient client = createClient();

		ElasticCommand cmd = new Query( //
				new FunctionScore() //
						.with(new Term("message") //
								.withExampleText("user") //
						) //
						.withBoost(new BigDecimal("1.2")) //
						.withBoostMode(ScoreMode.AVG) //
						// .withFunction(WeightFunction.valueOf(new
						// BigDecimal("1"))) //
						// .withFunction(new RandomScoreFunction()) //
						// .withFunction(new ScriptScoreFunction( //
						// new PainlessElasticScript() //
						// .withScript("_score * 1.5") //
						// .withParam("p1", "5")) //
						// ) //
						// .withFunction(new
						// DecayFunction(DecayFunctionType.GAUSS, "message") //
						// .withDecay(new BigDecimal("0.33")) //
						// .withOffset("0") //
						// .withOrigin("11") //
						// .withScale("2")) //
						.withFunctions(new NestedScoreFunction( //
								new Term("message").withExampleText("user"), //
								new RandomScoreFunction(), //
								WeightFunction.valueOf(new BigDecimal("1"))))
						.withMaxBoost(new BigDecimal("10")) //
						.withMinScore(new BigDecimal("2")) //
						.withScoreMode(ScoreMode.SUM) //
		).withScope(TwitterTweet.class).withHit(TwitterTweet.class);

		ElasticCommandResult result = client.execute(cmd);
		assertTrue(result.getCommand() == cmd);

		// MultiGetResponse response = result.getResultData();
		// assertEquals(3, response.getInnerCommandCount());
		// assertEquals(2, response.getSuccessCount());
		// assertEquals(1, response.getFailCount());
		// assertTrue(response.isPartialSuccessful());
		//
		// assertEquals("3", ((TwitterTweet)
		// response.getInnerResponses().get(0).getDocument()).getId());
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
