package com.generation.enlace.controller;

import java.util.List;
import java.util.Optional;

import com.generation.enlace.service.UsuarioService;
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
import com.generation.enlace.model.UsuarioModel;
import com.generation.enlace.model.UsuarioLoginModel;
import com.generation.enlace.repository.UsuarioRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/login")
	public ResponseEntity<UsuarioLoginModel> login(@RequestBody Optional<UsuarioLoginModel> user) {
		return usuarioService.autenticarUsuario(user).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastro")
	public ResponseEntity<UsuarioModel> postUsuario(@Valid @RequestBody UsuarioModel usuario) {
		return usuarioService.cadastrarUsuario(usuario)
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	}

	@GetMapping
	public ResponseEntity<List<UsuarioModel>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{usuarioId}")
	public ResponseEntity<UsuarioModel> getById(@PathVariable Long usuarioId) {
		return repository.findById(usuarioId).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/usuarios/{usuario}")
	public ResponseEntity<Optional<UsuarioModel>> getByNome(@PathVariable String usuario) {
		return ResponseEntity.ok(repository.findByUsuarioEmail(usuario));
	}

	@PostMapping
	public ResponseEntity<UsuarioModel> post(@RequestBody UsuarioModel usuario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
	}
	
	@PutMapping("/editar")
	public ResponseEntity<UsuarioModel> put(@RequestBody UsuarioModel usuario) {
		return usuarioService.atualizarUsuario(usuario)
				.map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@DeleteMapping("/{usuarioId}")
	public void delete(@PathVariable Long usuarioId) {
		repository.deleteById(usuarioId);
	}
}