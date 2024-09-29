package com.ufes.urbanapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufes.urbanapi.model.ProjetoObra;

@Repository
public interface ObraRepository extends JpaRepository<ProjetoObra, Long> {
  List<ProjetoObra> findByBairro(String bairro);
}
