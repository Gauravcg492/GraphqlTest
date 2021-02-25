package com.backend.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.cucumber.spring.CucumberContextConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@CucumberContextConfiguration
public class CucumberSpringContextConfiguration {

}
