package com.project.test.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import com.project.model.User;
import com.project.test.annotation.Order;

public class UserTest extends AbstractTest {
	
	@Test
	@Order(1)
	public void testCreateUser() {
		createTestUser();
		User user = userRepo.save(new User(TEST_USER_NAME, TEST_USER_PASSWORD, TEST_USER_ROLE));
		assertEquals("User was not created!", 1, userRepo.count());
		assertThat(user.getUsername(), is(testUser.getUsername()));
	}

	@Test(expected = DataIntegrityViolationException.class)
	@Order(2)
	public void testDuplicateUser() {
		userRepo.save(new User(TEST_USER_NAME, TEST_USER_PASSWORD, TEST_USER_ROLE));
	}
	
	@Test
	@Order(3)
	public void testCascadeDelete() {
		userRepo.delete(userRepo.findByUsername(TEST_USER_NAME));
		assertEquals("The user was not deleted!", 0, userRepo.count());
	}
	
	@Test
	@Order(4)
	@Override
	public void testDeleteAll() {
		userRepo.deleteAll();
	}
		
}
