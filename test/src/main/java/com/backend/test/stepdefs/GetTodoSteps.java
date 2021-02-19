package com.backend.test.stepdefs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.backend.test.CucumberSpringContextConfiguration;
import com.backend.test.utils.MyUtils;

import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import io.cucumber.java8.En;

public class GetTodoSteps extends CucumberSpringContextConfiguration implements En {
	private String GQL_REQ_PATH = "classpath:graphql/request/%s.graphql";
	private String GQL_RES_PATH = "classpath:graphql/response/%s.json";
	
	@Autowired
	private MyUtils util;
	
	@Autowired
	private GraphQLWebClient gqlClient;
	
	private GraphQLRequest gqlReq;
	private GraphQLResponse gqlRes;
	
	private String filename;

	@SuppressWarnings("unchecked")
	public GetTodoSteps() {
		
		Given("User wants to fetch all todos", () -> {
		    // Write code here that turns the phrase above into concrete actions
		    filename = "getTodos";
		    System.out.println("Given Completed");
		});
		
		When("User fetches all todos", () -> {
		    // Write code here that turns the phrase above into concrete actions
		    String str = util.fileToString(String.format(GQL_REQ_PATH, filename));
		    System.out.println(str);
		    gqlReq = GraphQLRequest.builder().query(str).build();
		    gqlRes = gqlClient.post(gqlReq).block();
		    System.out.println("When completed");
		});
		
		Then("All Todos are displayed", () -> {
		    // Write code here that turns the phrase above into concrete actions
			List<Object> actualResponse = (ArrayList<Object>) gqlRes.get("todos", Object.class);
			List<Object> expectedResponse = (ArrayList<Object>) util.jsonFileToType(String.format(GQL_RES_PATH, filename), Object.class);
			System.out.println(actualResponse);
			System.out.println(expectedResponse);
			// Check if length of output is same
			assertEquals(actualResponse.size(), expectedResponse.size());
			// If length is correct then iterate over each todo in the list
			for(int i = 0; i< expectedResponse.size(); i++) {
				// Actual output is casted to a map
				Object a = actualResponse.get(i);
				Map<String, Object> aMap = (Map<String, Object>) a;
				// Expected output is casted to a map
				Object b = expectedResponse.get(i);
				Map<String, Object> bMap = (Map<String, Object>) b;
				
				Iterator<Map.Entry<String, Object>> expItr = bMap.entrySet().iterator();
				while(expItr.hasNext()) {
					Map.Entry<String, Object> expEntry = expItr.next();
					assertEquals(expEntry.getValue(), aMap.get(expEntry.getKey()));
				}
			}
		    System.out.println("Then Completed");
		});
	}

}
