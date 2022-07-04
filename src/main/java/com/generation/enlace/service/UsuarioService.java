package com.generation.enlace.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.enlace.model.UsuarioLoginModel;
import com.generation.enlace.model.UsuarioModel;
import com.generation.enlace.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository repository;

	public Optional<UsuarioModel> cadastrarUsuario(UsuarioModel usuario) {

		if (repository.findByUsuarioEmail(usuario.getUsuarioEmail()).isPresent()) {
			return Optional.empty();
		}

		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		return Optional.of(repository.save(usuario));
	}

	public Optional<UsuarioModel> atualizarUsuario(UsuarioModel usuario) {

		if (repository.findById(usuario.getUsuarioId()).isPresent()) {
			usuario.setSenha((criptografarSenha(usuario.getSenha())));
			return Optional.of(repository.save(usuario));
		}
		return Optional.empty();
	}

	public Optional<UsuarioLoginModel> autenticarUsuario(Optional<UsuarioLoginModel> usuarioLogin) {
		Optional<UsuarioModel> usuario = repository.findByUsuarioEmail(usuarioLogin.get().getUsuarioEmail());

		if (usuario.isPresent()) {
			if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
				usuarioLogin.get().setUsuarioId(usuario.get().getUsuarioId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setImagemUrl(usuario.get().getImagemUrl());
				usuarioLogin.get().setTipo(usuario.get().getTipo());
				usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuarioEmail(), usuarioLogin.get().getSenha()));
				usuarioLogin.get().setSenha(usuario.get().getSenha());

				return usuarioLogin;
			}
		}

		return Optional.empty();
	}

	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	};

	private boolean compararSenhas(String senhaDigitada, String senhaCadastrada) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(senhaDigitada, senhaCadastrada);
	}

	private String gerarBasicToken(String usuario, String senha) {

		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);
	}
}
