package com.ufes.urbanapi.controller;

import java.util.List;

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

import com.ufes.urbanapi.model.ProjetoObra;
import com.ufes.urbanapi.model.ProjetoObraDTO;
import com.ufes.urbanapi.model.Status;
import com.ufes.urbanapi.service.ObraService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/obras")
@RequiredArgsConstructor
@CrossOrigin
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
  public ResponseEntity<?> create(@RequestBody ProjetoObraDTO projetoObra) {
    if (projetoObra.getResponsavel() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null");
    }

    ProjetoObra savedProjetoObra = obraService.save(projetoObra);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedProjetoObra);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProjetoObra> update(@PathVariable Long id, @RequestBody ProjetoObraDTO projetoObraDTO) {
    var projetoObra = obraService.findById(id);
    if (!projetoObra.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    ProjetoObra updatedProjeto = obraService.update(projetoObra.get(), projetoObraDTO);
    return ResponseEntity.ok(updatedProjeto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    obraService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/status")
  public List<Status> getAllStatus() {
    return obraService.findAllStatus();
  }
}
