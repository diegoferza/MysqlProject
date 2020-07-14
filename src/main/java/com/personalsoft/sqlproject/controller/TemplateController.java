package com.personalsoft.sqlproject.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class TemplateController {
	
	@RequestMapping("/index")
	public String getIndex(Model model) {
		model.addAttribute("mensaje","Mensaje desde el controlador");
	    return "index";
	  }	
	
}
