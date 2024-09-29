package com.ufes.urbanapi.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufes.urbanapi.model.User;
import com.ufes.urbanapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;;
  private final PasswordEncoder passwordEncoder;

  public User validarLogin(String email, String senha) throws Exception {
    Optional<User> usuario = userRepository.findByEmail(email);
    if (usuario.isPresent() && passwordEncoder.matches(senha, usuario.get().getSenha()) && usuario.get().getAtivo()) {
      return usuario.get();
    } else {
      throw new Exception("Email ou senha inválidos, ou conta inativa.");
    }
  }
}
