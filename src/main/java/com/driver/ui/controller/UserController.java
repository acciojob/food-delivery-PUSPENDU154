package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.UserResponse;
import com.driver.service.impl.UserServiceImpl;
import com.driver.shared.dto.OrderDto;
import com.driver.shared.dto.UserDto;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
		UserDto userDto = userService.getUserByUserId(id);
		UserResponse userResponse = UserResponse.builder().userId(userDto.getUserId()).firstName(userDto.getFirstName())
				.lastName(userDto.getLastName()).email(userDto.getEmail()).build();
		return userResponse;
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{

		UserDto userDto = userService.createUser(UserDto.builder().firstName(userDetails.getFirstName()).
				lastName(userDetails.getLastName()).email(userDetails.getEmail()).build());
		UserResponse userResponse = UserResponse.builder().firstName(userDto.getFirstName()).userId(userDto.getUserId())
				.lastName(userDto.getLastName()).email(userDto.getEmail()).build();



		return userResponse ;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = userService.createUser(UserDto.builder().firstName(userDetails.getFirstName()).
				lastName(userDetails.getLastName()).email(userDetails.getEmail()).build());
		userDto = userService.updateUser(id,userDto);

		UserResponse userResponse = UserResponse.builder().firstName(userDto.getFirstName()).userId(userDto.getUserId())
				.lastName(userDto.getLastName()).email(userDto.getEmail()).build();
		return userResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{
		userService.deleteUser(id);
		return new OperationStatusModel();
	}
	
	@GetMapping()
	public List<UserResponse> getUsers(){

		List<UserDto> userDtos = userService.getUsers();
		List<UserResponse> userResponses = new ArrayList<>();

		for(UserDto user: userDtos){
			UserResponse userResponse = UserResponse.builder().userId(user.getUserId()).firstName(user.getFirstName())
					.lastName(user.getLastName()).email(user.getEmail()).build();
			userResponses.add(userResponse);
		}
		return userResponses;
	}
	
}
