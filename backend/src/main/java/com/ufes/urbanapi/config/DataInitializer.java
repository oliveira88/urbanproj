package com.ufes.urbanapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ufes.urbanapi.model.Responsavel;
import com.ufes.urbanapi.model.Status;
import com.ufes.urbanapi.repository.ObraRepository;
import com.ufes.urbanapi.repository.ResponsavelRepository;
import com.ufes.urbanapi.repository.StatusRepository;

@Configuration
public class DataInitializer {
  @Bean
  CommandLineRunner initDatabase(ObraRepository obraRepository, StatusRepository statusRepository,
      ResponsavelRepository responsavelRepository) {
    return args -> {
      obraRepository.deleteAll();
      statusRepository.deleteAll();
      responsavelRepository.deleteAll();

      var responsavel = new Responsavel();
      responsavel.setNome("João");
      responsavel.setEmail("joao@email.com");
      responsavel.setCargo("Analista");

      var responsavel2 = new Responsavel();
      responsavel2.setNome("Maria");
      responsavel2.setEmail("maria@email.com");
      responsavel2.setCargo("Gerente");

      var pendente = new Status();
      var andamento = new Status();
      var concluido = new Status();
      pendente.setEstado("Pendente");
      andamento.setEstado("Em andamento");
      concluido.setEstado("Concluído");

      responsavelRepository.save(responsavel);
      responsavelRepository.save(responsavel2);
      statusRepository.save(pendente);
      statusRepository.save(andamento);
      statusRepository.save(concluido);
    };
  }
}
