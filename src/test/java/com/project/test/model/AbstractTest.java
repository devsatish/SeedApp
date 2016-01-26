package com.project.test.model;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import com.project.Application;
import com.project.model.Theme;
import com.project.model.User;
import com.project.model.repo.UserRepo;
import com.project.test.runner.OrderedRunner;

@WebAppConfiguration
@ActiveProfiles({"test"})
@RunWith(OrderedRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public abstract class AbstractTest {
	
	protected final static Long   TEST_USER_ID = 1L;
	protected final static String TEST_USER_NAME = "testUser";
	protected final static String TEST_USER_PASSWORD = "testPassword";
	protected final static String TEST_USER_ROLE = "testRole";

	protected static User testUser;
	
	protected static Theme testSettings;

	@Autowired
	protected UserRepo userRepo;
		
	protected User createTestUser() {
		testUser = new User(TEST_USER_NAME, TEST_USER_PASSWORD, TEST_USER_ROLE);
		testUser.setId(TEST_USER_ID);
		return testUser;
	}

	public abstract void testDeleteAll();
	
}
