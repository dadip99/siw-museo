package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Curatore;
import com.example.demo.service.CuratoreService;
import com.example.demo.validator.CuratoreValidator;

@Controller
public class CuratoreController {

	@Autowired
	CuratoreService curatoreService;
	
	@Autowired
	CuratoreValidator curatoreValidator;
	
	@RequestMapping(value= "/admin/curatori", method = RequestMethod.GET)
    public String getCuratoriAll(Model model) {
    	model.addAttribute("curatori", curatoreService.tutti());
        return "curatoriAll.html";
    }
    

    
    @RequestMapping(value= "/admin/newCuratore", method = RequestMethod.GET)
    public String getFormCuratoreCollezione(Model model) {
		model.addAttribute("curatore", new Curatore());
        return "formCuratore";
    }
      
	@RequestMapping(value="/admin/newCuratore", method = RequestMethod.POST)
	public String getCuratore(@ModelAttribute("curatore")Curatore curatore, 
								Model model, BindingResult bindingResult) {
		this.curatoreValidator.validate(curatore, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.curatoreService.inserisci(curatore);
			model.addAttribute("curatori", this.curatoreService.tutti());
			return "curatoriAll";
		}
		return "formCuratore";
	}
	
	@RequestMapping(value="/admin/deleteCuratore", method = RequestMethod.GET)
    public String deleteCuratore(Model model) {
    	model.addAttribute("curatori", curatoreService.tutti());
		return "curatoriCancella";
    }
	@RequestMapping(value="/admin/deleteCuratore", method = RequestMethod.POST)
	public String deleteDoneCuratore(Model model, 
			@RequestParam(required=false,name="curatoreDaCancellare")Long matricola) {
		if (matricola==null) {
			model.addAttribute("curatori", curatoreService.tutti());
			return "curatoriCancella";
		}
		curatoreService.elimina(matricola);
    	model.addAttribute("curatori", curatoreService.tutti());
		return "curatoriAll";
	}
}
