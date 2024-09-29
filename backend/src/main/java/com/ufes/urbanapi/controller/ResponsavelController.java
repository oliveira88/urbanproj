package com.ufes.urbanapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ufes.urbanapi.model.Responsavel;
import com.ufes.urbanapi.service.ResponsavelService;

@RestController
@RequestMapping("/api/responsavel")
@CrossOrigin
public class ResponsavelController {
  @Autowired
  private ResponsavelService responsavelService;

  @GetMapping
  public ResponseEntity<List<Responsavel>> findAll() {
    List<Responsavel> responsaveis = responsavelService.findAll();
    return ResponseEntity.ok(responsaveis);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Responsavel> findById(@PathVariable Long id) {
    Optional<Responsavel> responsavel = responsavelService.findById(id);
    return responsavel.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping
  public ResponseEntity<Responsavel> create(@RequestBody Responsavel responsavel) {
    Responsavel savedResponsavel = responsavelService.save(responsavel);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedResponsavel);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Responsavel> update(@PathVariable Long id, @RequestBody Responsavel responsavel) {
    if (!responsavelService.findById(id).isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    responsavel.setId(id);
    Responsavel updatedResponsavel = responsavelService.save(responsavel);
    return ResponseEntity.ok(updatedResponsavel);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!responsavelService.findById(id).isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    responsavelService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
