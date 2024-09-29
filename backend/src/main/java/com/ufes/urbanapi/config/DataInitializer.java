package com.ufes.urbanapi.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ufes.urbanapi.model.Perfil;
import com.ufes.urbanapi.model.Permissao;
import com.ufes.urbanapi.model.Responsavel;
import com.ufes.urbanapi.model.Status;
import com.ufes.urbanapi.model.User;
import com.ufes.urbanapi.repository.ObraRepository;
import com.ufes.urbanapi.repository.PerfilRepository;
import com.ufes.urbanapi.repository.PermissaoRepository;
import com.ufes.urbanapi.repository.ResponsavelRepository;
import com.ufes.urbanapi.repository.StatusRepository;
import com.ufes.urbanapi.repository.UserRepository;

@Configuration
public class DataInitializer {
  @Bean
  CommandLineRunner initDatabase(ObraRepository obraRepository, StatusRepository statusRepository,
      ResponsavelRepository responsavelRepository,
      UserRepository usuarioRepository,
      PerfilRepository perfilRepository,
      PermissaoRepository permissaoRepository,
      PasswordEncoder passwordEncoder) {
    return args -> {
      clearDatabase(obraRepository, statusRepository, responsavelRepository, usuarioRepository, perfilRepository,
          permissaoRepository);
      initializeUsersAndPermissions(usuarioRepository, perfilRepository, permissaoRepository, passwordEncoder);

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

  private void initializeUsersAndPermissions(UserRepository usuarioRepository, PerfilRepository perfilRepository,
      PermissaoRepository permissaoRepository, PasswordEncoder passwordEncoder) {
    Permissao criarPermissao = new Permissao();
    criarPermissao.setNome("CRIAR");
    criarPermissao.setDescricao("Permissão para criar registros");

    Permissao lerPermissao = new Permissao();
    lerPermissao.setNome("LER");
    lerPermissao.setDescricao("Permissão para ler registros");

    Permissao atualizarPermissao = new Permissao();
    atualizarPermissao.setNome("ATUALIZAR");
    atualizarPermissao.setDescricao("Permissão para atualizar registros");

    Permissao deletarPermissao = new Permissao();
    deletarPermissao.setNome("DELETAR");
    deletarPermissao.setDescricao("Permissão para deletar registros");
    permissaoRepository.save(criarPermissao);
    permissaoRepository.save(lerPermissao);
    permissaoRepository.save(atualizarPermissao);
    permissaoRepository.save(deletarPermissao);

    Perfil adminPerfil = new Perfil();
    adminPerfil.setNome("Administrador");
    adminPerfil.setDescricao("Perfil com todas as permissões de administração");
    adminPerfil.getPermissoes().add(criarPermissao);
    adminPerfil.getPermissoes().add(lerPermissao);
    adminPerfil.getPermissoes().add(atualizarPermissao);
    adminPerfil.getPermissoes().add(deletarPermissao);

    Perfil gestorPerfil = new Perfil();
    gestorPerfil.setNome("Gestor");
    gestorPerfil.setDescricao("Perfil de gestor com permissões básicas");
    gestorPerfil.getPermissoes().add(criarPermissao);
    gestorPerfil.getPermissoes().add(lerPermissao);
    gestorPerfil.getPermissoes().add(atualizarPermissao);
    gestorPerfil.getPermissoes().add(deletarPermissao);
    perfilRepository.save(adminPerfil);
    perfilRepository.save(gestorPerfil);

    User adminUsuario = new User();
    adminUsuario.setNome("Guilherme Oliveira");
    adminUsuario.setEmail("admin@email.com");
    adminUsuario.setSenha(passwordEncoder.encode("admin123"));
    adminUsuario.setDataCadastro(LocalDate.now());
    adminUsuario.setAtivo(true);
    adminUsuario.getPerfis().add(adminPerfil);

    User gestorUsuario = new User();
    gestorUsuario.setNome("Pedro Silva");
    gestorUsuario.setEmail("gestor@email.com");
    gestorUsuario.setSenha(passwordEncoder.encode("gestor123"));
    gestorUsuario.setDataCadastro(LocalDate.now());
    gestorUsuario.setAtivo(true);
    gestorUsuario.getPerfis().add(gestorPerfil);

    usuarioRepository.save(adminUsuario);
    usuarioRepository.save(gestorUsuario);
  }

  private void clearDatabase(ObraRepository obraRepository, StatusRepository statusRepository,
      ResponsavelRepository responsavelRepository, UserRepository usuarioRepository,
      PerfilRepository perfilRepository, PermissaoRepository permissaoRepository) {
    obraRepository.deleteAll();
    statusRepository.deleteAll();
    responsavelRepository.deleteAll();
    usuarioRepository.deleteAll();
    perfilRepository.deleteAll();
    permissaoRepository.deleteAll();
  }
}
