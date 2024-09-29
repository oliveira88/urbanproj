package com.ufes.urbanapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufes.urbanapi.model.ProjetoObra;
import com.ufes.urbanapi.service.ObraService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/obras")
@RequiredArgsConstructor
public class ObraController {

  private final ObraService obraService;

  @GetMapping
  public List<ProjetoObra> getAll() {
    return obraService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjetoObra> getById(@PathVariable Long id) {
    return obraService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<ProjetoObra> create(@RequestBody ProjetoObra projetoObra) {
    if (projetoObra.getResponsavel() == null || projetoObra.getResponsavel().getId() == null) {
      return ResponseEntity.badRequest().build();
    }
    ProjetoObra savedProjetoObra = obraService.save(projetoObra);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedProjetoObra);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    obraService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
