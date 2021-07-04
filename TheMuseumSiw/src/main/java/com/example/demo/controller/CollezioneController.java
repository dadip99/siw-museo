package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Collezione;
import com.example.demo.model.Curatore;
import com.example.demo.service.CollezioneService;
import com.example.demo.service.CuratoreService;
import com.example.demo.validator.CollezioneValidator;

@Controller
public class CollezioneController {

	@Autowired
	CollezioneService collezioneService;
	
	@Autowired
	CuratoreService curatoreService;
	
	@Autowired
	CollezioneValidator collezioneValidator;
	
	@RequestMapping(value= "/collezioni", method = RequestMethod.GET)
    public String getCollezioniAll(Model model) {
    	model.addAttribute("collezioni", collezioneService.tutti());
        return "collezioniAll.html";
    }
    
    @RequestMapping(value= "/collezioni/{id}", method = RequestMethod.GET)
    public String getCollezione(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("collezione", collezioneService.getById(id));
    	model.addAttribute("opere", collezioneService.getById(id).getOpere());
    	model.addAttribute("curatore", collezioneService.getById(id).getCuratore());	
        return "collezione.html";
    }
	
    @RequestMapping(value= "/admin/newCollezione", method = RequestMethod.GET)
    public String getFormCuratoreCollezione(Model model) {
		model.addAttribute("collezione", new Collezione());
    	model.addAttribute("curatori", curatoreService.tutti());	
        return "formCollezione";
    }
      
    
	@RequestMapping(value="/admin/newCollezione", method = RequestMethod.POST)
	public String getCollezione(@ModelAttribute("collezione")Collezione collezione,
							@RequestParam(required=false,name="curatore")Long idC,
							Model model, BindingResult bindingResult) {
		this.collezioneValidator.validate(collezione, bindingResult);
		if (!bindingResult.hasErrors() && idC!=null) {
			Curatore curatore=this.curatoreService.getById(idC);
			collezione.setCuratore(curatore);
			this.collezioneService.inserisci(collezione);
			model.addAttribute("curatore", curatore);
			model.addAttribute("collezione", collezione);
			return "collezione";
		}
		model.addAttribute("curatori", curatoreService.tutti());
		return "formCollezione";
	}
    
	@RequestMapping(value="/admin/deleteCollezione", method = RequestMethod.GET)
    public String deleteCollezione(Model model) {
    	model.addAttribute("collezioni", collezioneService.tutti());
		return "collezioniCancella";
    }
	@RequestMapping(value="/admin/deleteCollezione", method = RequestMethod.POST)
	public String deleteDoneCollezione(Model model, 
			@RequestParam(required=false,name="collezioneDaCancellare")Long id) {
		if (id==null) {
			model.addAttribute("collezioni", collezioneService.tutti());
			return "collezioniCancella";
		}
		collezioneService.elimina(id);
    	model.addAttribute("collezioni", collezioneService.tutti());
		return "collezioniAll";
	}
	
}
