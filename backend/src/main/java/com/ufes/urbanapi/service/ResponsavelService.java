package com.ufes.urbanapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ufes.urbanapi.model.Responsavel;
import com.ufes.urbanapi.repository.ResponsavelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResponsavelService {
  private final ResponsavelRepository responsavelRepository;

  public List<Responsavel> findAll() {
    return responsavelRepository.findAll();
  }

  public Optional<Responsavel> findById(Long id) {
    return responsavelRepository.findById(id);
  }

  public Responsavel save(Responsavel responsavel) {
    return responsavelRepository.save(responsavel);
  }

  public void delete(Long id) {
    responsavelRepository.deleteById(id);
  }
}
