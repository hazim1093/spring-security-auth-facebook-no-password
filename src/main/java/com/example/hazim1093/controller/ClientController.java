package com.example.hazim1093.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hazim on 4/26/16
 */
@Controller
public class ClientController {

	@RequestMapping("/")
	public String index(){
		return "client.html";
	}
}
