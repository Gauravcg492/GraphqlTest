package com.backend.test.stepdefs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.backend.test.CucumberSpringContextConfiguration;
import com.backend.test.models.InsertUserInput;
import com.backend.test.utils.Constants;

import graphql.kickstart.spring.webclient.boot.GraphQLError;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import io.cucumber.java8.En;

public class LoginSteps extends CucumberSpringContextConfiguration implements En{
	
	private String filename;
	
	@Autowired
	private GraphQLWebClient gqlClient;
	
	private GraphQLRequest gqlReq;
	private GraphQLResponse gqlRes;
	
	@SuppressWarnings("unchecked")
	public LoginSteps() {
		Given("User wants to login", () -> {
		    // Write code here that turns the phrase above into concrete actions
			filename = "login";
			System.out.println("Given Completed");
		});
		
		When("User provides {string} and {string}", (String email, String password) -> {
		    // Write code here that turns the phrase above into concrete actions
			InsertUserInput input = new InsertUserInput(email, password);
			Map<String, Object> mapInput = new HashMap<>();
			mapInput.put("input", input);
			gqlReq = GraphQLRequest.builder().resource(String.format(Constants.GQL_REQ_PATH, filename)).variables(mapInput).build();
			gqlRes = gqlClient.post(gqlReq).block();
			System.out.println("When Completed");
		});
		
		Then("User receives {string} and token", (String username) -> {
		    // Write code here that turns the phrase above into concrete actions
			List<GraphQLError> errors = gqlRes.getErrors();
			for(GraphQLError err: errors) {
				System.out.println(err);
			}
			Object actualResponse = gqlRes.get("user", Object.class);
			System.out.println(actualResponse);
			Map<String, Object> actRes = (Map<String, Object>) actualResponse;
			assertEquals(username, actRes.get("email"));
			assertThat(actRes).containsKey("token");
			System.out.println("Then Completed");
		});
	}
	
	
}
