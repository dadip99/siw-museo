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

import com.example.demo.model.Artista;
import com.example.demo.service.ArtistaService;
import com.example.demo.validator.ArtistaValidator;

@Controller
public class ArtistaController {

	@Autowired
	ArtistaService artistaService;
	
	@Autowired
	ArtistaValidator artistaValidator;
	
	@RequestMapping(value= "/artisti", method = RequestMethod.GET)
    public String getArtisti(Model model) {
    	model.addAttribute("artisti", artistaService.tutti());
        return "artistiAll.html";
    }
    
    @RequestMapping(value= "/artisti/{id}", method = RequestMethod.GET)
    public String getArtista(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("artista", artistaService.getById(id));
    	model.addAttribute("opere", artistaService.getById(id).getOpere());
        return "artista.html";
    }
	
	@RequestMapping(value="/admin/newArtista", method = RequestMethod.GET)
    public String newArtista(Model model) {
		model.addAttribute("artista", new Artista());
		return "formArtista";
    }
	@RequestMapping(value="/admin/newArtista", method = RequestMethod.POST)
	public String getArtista(@ModelAttribute("artista") Artista artista, 
			Model model, BindingResult bindingResult) {
		this.artistaValidator.validate(artista, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.artistaService.inserisci(artista);
			model.addAttribute("artista", artista);
			return "artista";
		}
		return "formArtista";
	}
	
	
	
	
	@RequestMapping(value="/admin/deleteArtista", method = RequestMethod.GET)
    public String deleteArtista(Model model) {

    	model.addAttribute("artisti", artistaService.tutti());
		return "artistiCancella";
    }
	@RequestMapping(value="/admin/deleteArtista", method = RequestMethod.POST)
	public String deleteDoneArtista(Model model, @RequestParam(required=false,name="artistaDaCancellare")Long id) {
		if (id==null) {
			model.addAttribute("artisti", artistaService.tutti());
			return "artistiCancella";
		}
		artistaService.elimina(id);
    	model.addAttribute("artisti", artistaService.tutti());
		return "artistiAll";
	}
	
}
