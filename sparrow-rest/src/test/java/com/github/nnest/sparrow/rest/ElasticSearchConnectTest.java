/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ElasticSearchConnectTest {
	@Test
	public void test() throws IOException {
		RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200, "http")).build();

		Response response = restClient.performRequest("GET", "/twitter/tweet/1?pretty");
		System.out.println(EntityUtils.toString(response.getEntity()));
		restClient.close();
	}
}
