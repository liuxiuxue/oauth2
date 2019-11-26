package com.liuxiuxue.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	

	@GetMapping("/me")
	public ResponseEntity<?> getUser(Principal principal){
		if (principal == null)
			return null;
		if (principal instanceof AnonymousAuthenticationToken)
			return null;
		return new ResponseEntity<Principal>(principal, HttpStatus.OK);
	}
}
