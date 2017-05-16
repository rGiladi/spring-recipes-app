package com.roy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.roy.controllers.RecipesController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipesTest {

	@Test
	public void contextLoads() throws Exception {
	}

}
