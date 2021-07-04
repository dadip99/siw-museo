package com.example.demo.controller;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Opera;
import com.example.demo.service.OperaService;


@Controller
public class MainController {

	@Autowired
	OperaService operaService;	
	
    @RequestMapping(value= {"/","/home"}, method = RequestMethod.GET)
    public String getHome(Model model) {
    	List<Opera> opere= operaService.getRandomOpere();
    	
    	
    	for(int i=0;i<opere.size();i++) {
    		Opera o=opere.get(i);
    		model.addAttribute("opera"+(i+1), o);   	
        	byte[] bytes = ArrayUtils.toPrimitive(o.getImage());
        	model.addAttribute("image"+(i+1), Base64Utils.encodeToString(bytes));
    	}
    	
        return "home";
    }
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public String getHomeAdmin(Model model) {
		return "admin_home";
    }
    
}
