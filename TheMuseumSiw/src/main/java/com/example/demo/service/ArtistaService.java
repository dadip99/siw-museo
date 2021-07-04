package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Artista;
import com.example.demo.repository.ArtistaRepository;


@Service
public class ArtistaService {

	@Autowired
	private ArtistaRepository artistaRepository;
	
	@Transactional
	public Artista inserisci(Artista artista) {
		return artistaRepository.save(artista);
	}
	
	@Transactional
	public Artista getById(Long id) {
		return artistaRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public List<Artista> tutti(){
		return (List<Artista>) artistaRepository.findAll();
	}

	@Transactional
	public void elimina(Long id){
		this.artistaRepository.deleteById(id);
	}

		
	
}
