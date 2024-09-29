package com.ufes.urbanapi.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "perfil")
@Data
public class Perfil {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;
  private String descricao;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "perfil_permissao", joinColumns = @JoinColumn(name = "idPerfil"), inverseJoinColumns = @JoinColumn(name = "idPermissao"))
  private Set<Permissao> permissoes = new HashSet<>();
}
