package com.example.hazim1093.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hazim on 4/26/16
 */
@RestController
@RequestMapping("api/v1")
public class Controller {

	/**
	 * REST call without any security
	 */
	@RequestMapping("/hello")
	public String hello(){
		return "Hello from the other side";
	}

}
