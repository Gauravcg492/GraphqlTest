package com.backend.test;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/resources/features/login.feature", glue = {"com.backend.test.stepdefs"}, plugin = {"pretty", "json:target/test.json"})
public class CucumberTest {

}
