package com.example.demo.controller;

import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Artista;
import com.example.demo.model.Collezione;
import com.example.demo.model.Opera;
import com.example.demo.service.ArtistaService;
import com.example.demo.service.CollezioneService;
import com.example.demo.service.CuratoreService;
import com.example.demo.service.OperaService;
import com.example.demo.validator.OperaValidator;

@Controller
public class OperaController {

	@Autowired
	ArtistaService artistaService;
	
	@Autowired
	CuratoreService curatoreService;
	
	@Autowired
	CollezioneService collezioneService;

	@Autowired
	OperaService operaService;	
	
	@Autowired
	OperaValidator operaValidator;
	
    @RequestMapping(value= "/opere", method = RequestMethod.GET)
    public String getOpereAll(Model model) {
        model.addAttribute("opere", operaService.tutti());
    	return "opereAll.html";
    }
  
    @RequestMapping(value= "/opere/{id}", method = RequestMethod.GET)
    public String getOpera(@PathVariable("id") Long id, Model model) {
    	Opera opera=operaService.getById(id);
    	model.addAttribute("opera", opera);
    	
    	byte[] bytes = ArrayUtils.toPrimitive(opera.getImage());
    	model.addAttribute("image", Base64Utils.encodeToString(bytes));
    	
    	model.addAttribute("collezione", operaService.getById(id).getCollezione());
    	model.addAttribute("artista", operaService.getById(id).getArtista());
        return "opera";
    }
    
    @RequestMapping(value= "/admin/newOpera", method = RequestMethod.GET)
    public String newOpera(Model model) {
        model.addAttribute("opera", new Opera());
        model.addAttribute("collezioni", collezioneService.tutti());
    	model.addAttribute("artisti", artistaService.tutti());
    	return "formOpera";
    }
    
    @RequestMapping(value= "/admin/newOpera", method = RequestMethod.POST)
	public String getNewOpera(@ModelAttribute("opera")Opera opera,
							@RequestParam(required=false,name="collezione")Long idC,
							@RequestParam(required=false,name="artista")Long idA,
							@RequestParam(required=false,name="foto")MultipartFile foto,
							Model model, BindingResult bindingResult) {
		this.operaValidator.validate(opera, bindingResult);
		if (!bindingResult.hasErrors() && idC!=null && idA!=null) {
			Artista artista= this.artistaService.getById(idA);
			Collezione collezione= this.collezioneService.getById(idC);
			opera.setArtista(artista);
			opera.setCollezione(collezione);
			
			try {
			    Byte[] byteObjects = new Byte[foto.getBytes().length];
			    int i = 0;
			    for (byte b : foto.getBytes()){
			        byteObjects[i++] = b;
			    }
			    opera.setImage(byteObjects);
			} catch (IOException e) {
			    e.printStackTrace();
			}
			
			this.operaService.inserisci(opera);
			byte[] bytes = ArrayUtils.toPrimitive(opera.getImage());
	    	model.addAttribute("image", Base64Utils.encodeToString(bytes));
			model.addAttribute("artista", artista);
			model.addAttribute("collezione", collezione);
			return "opera";
		}
		model.addAttribute("collezioni", collezioneService.tutti());
    	model.addAttribute("artisti", artistaService.tutti());
    	return "formOpera";
	}
    
	@RequestMapping(value="/admin/deleteOpera", method = RequestMethod.GET)
    public String deleteOpera(Model model) {
    	model.addAttribute("opere", operaService.tutti());
		return "opereCancella";
    }
	@RequestMapping(value="/admin/deleteOpera", method = RequestMethod.POST)
	public String deleteDoneOpera(Model model, 
			@RequestParam(required=false,name="operaDaCancellare")Long id) {
		if (id==null) {
			model.addAttribute("opere", operaService.tutti());
			return "opereCancella";
		}
		operaService.elimina(id);
    	model.addAttribute("opere", operaService.tutti());
		return "opereAll";
	}
}
