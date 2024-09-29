package com.ufes.urbanapi.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "projetoObra")
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoObra {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "idResponsavel")
  private Responsavel responsavel;

  @ManyToOne
  @JoinColumn(name = "status")
  private Status status;

  private String nome;
  private String descricao;
  private String bairro;

  @Temporal(TemporalType.DATE)
  private Date dataInicio;

  @Temporal(TemporalType.DATE)
  private Date dataFim;

  public ProjetoObra(ProjetoObraDTO dto, Responsavel responsavel, Status status) {
    this.nome = dto.getNome();
    this.descricao = dto.getDescricao();
    this.bairro = dto.getBairro();
    this.dataInicio = dto.getDataInicio();
    this.dataFim = dto.getDataFim();
    this.responsavel = responsavel;
    this.status = status;
  }
}
