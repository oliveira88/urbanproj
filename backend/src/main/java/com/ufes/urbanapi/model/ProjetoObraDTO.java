package com.ufes.urbanapi.model;

import java.util.Date;

import lombok.Data;

@Data
public class ProjetoObraDTO {
  private Long id;
  private Long responsavel;
  private Long status;
  private String nome;
  private String descricao;
  private String bairro;
  private Date dataInicio;
  private Date dataFim;
}
