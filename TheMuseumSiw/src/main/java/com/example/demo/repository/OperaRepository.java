package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Opera;

public interface OperaRepository extends CrudRepository<Opera, Long>{

	@Query(nativeQuery=true, value="SELECT *  FROM opera ORDER BY random() LIMIT 3")
	public List<Opera> getRandomOpera();
}
