package com.ufes.urbanapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ufes.urbanapi.model.ProjetoObra;
import com.ufes.urbanapi.repository.ObraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObraService {
  private final ObraRepository obraRepository;

  public List<ProjetoObra> findAll() {
    return obraRepository.findAll();
  }

  public Optional<ProjetoObra> findById(Long id) {
    return obraRepository.findById(id);
  }

  public ProjetoObra save(ProjetoObra projetoObra) {
    return obraRepository.save(projetoObra);
  }

  public void delete(Long id) {
    obraRepository.deleteById(id);
  }
}
