package org.agaray.elecciones.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnonymousController {
	
	@GetMapping("/")
	public String home(
			ModelMap m
			) {
		m.put("view", "/home/index");
		return "/_t/frame";
	}

}
