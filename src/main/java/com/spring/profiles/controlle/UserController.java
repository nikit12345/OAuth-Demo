package com.spring.profiles.controlle;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.profiles.entity.User;
import com.spring.profiles.repository.UserRepo;
import com.spring.profiles.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@Slf4j
public class UserController {
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	

	private final UserRepo userRepo;
	
	public UserController(UserRepo repo) {
		this.userRepo= repo;
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Integer id){
		
		try {
			User u = userService.getUserById(id);
		
		if(u==null) {
			log.info("user with id is not present");
		}
		return ResponseEntity.notFound().build();

		}
       catch(Exception ex){
			log.info("user with given id is not present");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
	}
	
	@GetMapping("/getAllUsers")
	public List<User> getUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping("/save")
	public ResponseEntity<User> saveUsers(@RequestBody User u1){
		User u = userService.saveUsers(u1);
		return new ResponseEntity<>(u,HttpStatus.OK );
	}
	
	@PutMapping("{id}/saveData")
	public ResponseEntity<User> updateUsersById(@PathVariable Integer id, @RequestBody User users){
		User uu = userService.updateUserById(id, users);
		return new ResponseEntity<>(uu,HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deletee(@PathVariable Integer id) {
        Optional<User> optionalUser = userRepo.findById(id);

        if (optionalUser.isPresent()) {
            userRepo.deleteById(id);
            return ResponseEntity.ok("User with ID " + id + " was deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("User with ID " + id + " not found.");
        }
    }
	
	@GetMapping("/{id}/getById")
	public ResponseEntity<User> getUSerById(@PathVariable Integer id) {
		Optional<User> u = userRepo.findById(id);
		if(u.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(u.get(), HttpStatus.OK);
	}
}
