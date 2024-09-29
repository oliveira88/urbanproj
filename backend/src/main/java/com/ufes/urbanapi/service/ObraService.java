package com.ufes.urbanapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ufes.urbanapi.exception.ResourceNotFoundException;
import com.ufes.urbanapi.model.ProjetoObra;
import com.ufes.urbanapi.model.ProjetoObraDTO;
import com.ufes.urbanapi.model.Responsavel;
import com.ufes.urbanapi.model.Status;
import com.ufes.urbanapi.repository.ObraRepository;
import com.ufes.urbanapi.repository.StatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObraService {
  private final ObraRepository obraRepository;
  private final ResponsavelService responsavelService;
  private final StatusRepository statusRepository;

  public List<ProjetoObra> findAll() {
    return obraRepository.findAll();
  }

  public Optional<ProjetoObra> findById(Long id) {
    return obraRepository.findById(id);
  }

  public ProjetoObra save(ProjetoObraDTO projetoObra) {
    Responsavel responsavel = responsavelService.findById(projetoObra.getResponsavel())
        .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado"));
    Status status = statusRepository.findById(projetoObra.getStatus())
        .orElseThrow(() -> new ResourceNotFoundException("Status não encontrado"));

    var projetoObraSave = new ProjetoObra(projetoObra, responsavel, status);
    return obraRepository.save(projetoObraSave);
  }

  public ProjetoObra update(ProjetoObra projetoObra, ProjetoObraDTO projetoObraDTO) {
    Responsavel responsavel = responsavelService.findById(projetoObraDTO.getResponsavel())
        .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado"));
    Status status = statusRepository.findById(projetoObraDTO.getStatus())
        .orElseThrow(() -> new ResourceNotFoundException("Status não encontrado"));

    var projetoObraSave = new ProjetoObra(projetoObraDTO, responsavel, status);
    projetoObraSave.setId(projetoObra.getId());
    return obraRepository.save(projetoObraSave);
  }

  public void delete(Long id) {
    obraRepository.deleteById(id);
  }

  public List<Status> findAllStatus() {
    return statusRepository.findAll();
  }
}
