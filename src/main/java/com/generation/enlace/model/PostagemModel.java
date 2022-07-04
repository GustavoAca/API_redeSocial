package com.generation.enlace.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "postagens")
public class PostagemModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="postagem_id")
	public long postagemId;
	
	@NotNull
	public String conteudo;

	public String imagem;

	public int abracei;
	
	@NotNull
	@CreatedDate
	@Column(name = "criado_em", updatable=false)
	public Instant criadoEm = Instant.now();
	
	@NotNull
	@LastModifiedDate
	@Column(name = "atualizado_em")
	public Instant atualizadoEm = Instant.now();

	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private UsuarioModel usuario;

	@ManyToOne
	@JsonIgnoreProperties("postagens")
	private TemaModel tema;

	public long getPostagemId() {
		return postagemId;
	}
	public void setPostagemId(long postagemId) {
		this.postagemId = postagemId;
	}
	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public int getAbracei() {
		return abracei;
	}

	public void setAbracei(int abracei) {
		this.abracei = abracei;
	}

	public Instant getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Instant criadoEm) {
		this.criadoEm = criadoEm;
	}

	public Instant getAtualizadoEm() {
		return atualizadoEm;
	}

	public void setAtualizadoEm(Instant atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}

	public UsuarioModel getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModel usuario) {
		this.usuario = usuario;
	}

	public TemaModel getTema() {
		return tema;
	}

	public void setTema(TemaModel tema) {
		this.tema = tema;
	}
	

}
