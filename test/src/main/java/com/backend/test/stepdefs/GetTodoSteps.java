package com.backend.test.stepdefs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.backend.test.CucumberSpringContextConfiguration;
import com.backend.test.models.InsertTodoInput;
import com.backend.test.utils.Constants;
import com.backend.test.utils.MyUtils;

import graphql.kickstart.spring.webclient.boot.GraphQLError;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import io.cucumber.java8.En;

public class GetTodoSteps extends CucumberSpringContextConfiguration implements En {
	
	@Autowired
	private MyUtils util;
	
	@Autowired
	private GraphQLWebClient gqlClient;
	
	private GraphQLRequest gqlReq;
	private GraphQLResponse gqlRes;
	
	private String filename;

	@SuppressWarnings("unchecked")
	public GetTodoSteps() {
		
		// Sceneario: Adding a Todo
		Given("User wants to add a todo", () -> {
		    // Write code here that turns the phrase above into concrete actions
			filename = "putTodo";
			System.out.println("Given Completed");
		});
		
		When("User adds todo {string} , {string} and {string}", (String title, String description, String category) -> {
		    // Write code here that turns the phrase above into concrete actions
			// Build insert input
			InsertTodoInput input = new InsertTodoInput(title, description, category);
			System.out.println(input);
			Map<String, Object> mapInput = new HashMap<>();
			mapInput.put("input", input);
			gqlReq = GraphQLRequest.builder().resource(String.format(Constants.GQL_REQ_PATH, filename)).variables(mapInput).build();
			gqlRes = gqlClient.post(gqlReq).block();
			System.out.println("When Completed");
		});
		
		Then("New Todo {string} is displayed", (String title) -> {
		    // Write code here that turns the phrase above into concrete actions
			List<GraphQLError> errors = gqlRes.getErrors();
			for(GraphQLError err: errors) {
				System.out.println(err);
			}
			Object actualResponse = gqlRes.get("insertTodo", Object.class);
			System.out.println(actualResponse);
			Map<String, Object> actRes = (Map<String, Object>) actualResponse;
			assertEquals(title, actRes.get("title"));
			System.out.println("Then Completed");
		});
		
		// Scenario: Fetch All Todos
		Given("User wants to fetch all todos", () -> {
		    // Write code here that turns the phrase above into concrete actions
		    filename = "getTodos";
		    System.out.println("Given Completed");
		});
		
		When("User fetches all todos", () -> {
		    // Write code here that turns the phrase above into concrete actions
		    String str = String.format(Constants.GQL_REQ_PATH, filename);
		    System.out.println(str);
		    gqlReq = GraphQLRequest.builder().resource(str).build();
		    gqlRes = gqlClient.post(gqlReq).block();
		    System.out.println("When completed");
		});
		
		Then("All Todos are displayed", () -> {
		    // Write code here that turns the phrase above into concrete actions
			List<Object> actualResponse = (ArrayList<Object>) gqlRes.get("todos", Object.class);
			List<Object> expectedResponse = (ArrayList<Object>) util.jsonFileToType(String.format(Constants.GQL_RES_PATH, filename), Object.class);
			System.out.println(actualResponse);
			System.out.println(expectedResponse);
			// Check if length of output is same
			assertEquals(expectedResponse.size(), actualResponse.size());
			// If length is correct then iterate over each todo in the list
			for(int i = 0; i< expectedResponse.size(); i++) {
				// Actual output is casted to a map
				Object a = actualResponse.get(i);
				Map<String, Object> aMap = (Map<String, Object>) a;
				// Expected output is casted to a map
				Object b = expectedResponse.get(i);
				Map<String, Object> bMap = (Map<String, Object>) b;
				
				assertEquals(bMap.get("title"), aMap.get("title"));
			}
		    System.out.println("Then Completed");
		});
	}

}
