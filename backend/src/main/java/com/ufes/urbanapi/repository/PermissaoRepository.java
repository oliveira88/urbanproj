package com.ufes.urbanapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufes.urbanapi.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}
