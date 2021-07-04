package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Curatore;
import com.example.demo.repository.CuratoreRepository;

@Service
public class CuratoreService {

	@Autowired
	private CuratoreRepository curatoreRepository;
	
	@Transactional
	public Curatore inserisci(Curatore curatore) {
		return curatoreRepository.save(curatore);
	}
	
	@Transactional
	public Curatore getById(Long matricola) {
		return curatoreRepository.findById(matricola).orElse(null);
	}
	
	@Transactional
	public List<Curatore> tutti(){
		return (List<Curatore>) curatoreRepository.findAll();
	}
	
	@Transactional
	public void elimina(Long id){
		this.curatoreRepository.deleteById(id);
	}
}
