package com.spring.profiles.service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.profiles.entity.User;
import com.spring.profiles.repository.UserRepo;

@Component
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RedisTemplate<String, User> templete;
	
	
	
	private static final String PRODUCT_KEY_PREFIX ="product";
			
    private static final Duration CACHE_TTL = Duration.ofHours(1);

    public User getUserById(Integer id) {
    	String redisKey = PRODUCT_KEY_PREFIX+id;
    	
    	User u = templete.opsForValue().get(redisKey);
    	if(u!=null) {
    		System.out.println("cache hit for id "+id);
    		return u;
    	}
    	System.out.println("cache miss for id "+id);
    	u = userRepo.findById(id).get();
    	if(u !=null) {
    		System.out.println("storing id "+id+" in cache with TTL");
    		templete.opsForValue().set(redisKey, u, CACHE_TTL);
    	}
    	return u;
    }
	
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}
	
	public User saveUsers(User user) {
		return userRepo.save(user);
	}
	
	public User updateUserById(Integer id, User user) {
		User originalUser = userRepo.findById(id).get();
		originalUser.setName(user.getName());
		originalUser.setAge(user.getAge());
		//originalUser.setId(user.getId());
		
		return userRepo.save(originalUser);
	}
	

	public void deleteUserById(Integer id) {
		Optional<User> uu = userRepo.findById(id);
		if(uu.isPresent()) {
			userRepo.deleteById(id);
		}else {
			System.out.println("USer with given id is not present");
		}
	}
	

	

}
