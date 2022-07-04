package com.generation.enlace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.enlace.model.TemaModel;

public interface TemaRepository extends JpaRepository<TemaModel, Long> {
public List<TemaModel> findAllByTagContainingIgnoreCase(String tag);
}
