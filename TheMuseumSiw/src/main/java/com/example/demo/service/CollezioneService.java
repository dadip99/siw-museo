package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Collezione;
import com.example.demo.repository.CollezioneRepository;

@Service
public class CollezioneService {

	@Autowired
	private CollezioneRepository collezioneRepository;
	
	@Transactional
	public Collezione inserisci(Collezione collezione) {
		return collezioneRepository.save(collezione);
	}
	
	@Transactional
	public Collezione getById(Long id) {
		return collezioneRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public List<Collezione> tutti(){
		return (List<Collezione>) collezioneRepository.findAll();
	}
	
	@Transactional
	public void elimina(Long id){
		this.collezioneRepository.deleteById(id);
	}
}
