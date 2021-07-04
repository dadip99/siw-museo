package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Opera;
import com.example.demo.repository.OperaRepository;

@Service
public class OperaService {

	@Autowired
	private OperaRepository operaRepository;
	
	@Transactional
	public Opera inserisci(Opera opera) {
		return operaRepository.save(opera);
	}
	
	@Transactional
	public Opera getById(Long id) {
		return operaRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public List<Opera> tutti(){
		return (List<Opera>) operaRepository.findAll();
	}
	
	@Transactional
	public void elimina(Long id){
		this.operaRepository.deleteById(id);
	}
	
	@Transactional
	public List<Opera> getRandomOpere(){
		return operaRepository.getRandomOpera();
	}
}
