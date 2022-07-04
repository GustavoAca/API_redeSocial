package com.generation.enlace.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.enlace.model.TemaModel;
import com.generation.enlace.repository.TemaRepository;

@RestController
@RequestMapping("/temas")
@CrossOrigin("*")
public class TemaController {

	@Autowired
	private TemaRepository repository;
	
	@GetMapping
	public ResponseEntity<List<TemaModel>> getAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{temaId}")
	public ResponseEntity<TemaModel> getById(@PathVariable Long temaId) {
		return repository.findById(temaId).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<TemaModel>> getByTipo(@PathVariable String descricao) {
		return ResponseEntity.ok(repository.findAllByTagContainingIgnoreCase(descricao));
	}
	
	@PostMapping
	public ResponseEntity<TemaModel> post(@RequestBody TemaModel tema) {
		 return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tema));
	 }
	
	@PutMapping
	public ResponseEntity<TemaModel> put(@RequestBody TemaModel tema) {
		return repository.findById(tema.getTemaId())
				.map(resp -> ResponseEntity.status(HttpStatus.OK)
						.body(repository.save(tema))).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{temaId}")
	public void delete(@PathVariable Long temaId) {
		repository.deleteById(temaId);
	}

}
