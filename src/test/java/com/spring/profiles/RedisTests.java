package com.spring.profiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Test
	void testSendEmail() {
	    // Set the value in Redis
	    redisTemplate.opsForValue().set("email", "nikita@gmail.com");
	    
	    // Get the value from Redis using the correct key
	   // Object email = redisTemplate.opsForValue().get("email");
	    
	    // Print the retrieved value to ensure it's correct
	    //System.out.println(email);  // This should print "nikita@gmail.com"
	    
	    // Assertion can be added for testing purpose
	    //assertEquals("nikita@gmail.com", email);
	}

}
