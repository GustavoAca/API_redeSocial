package com.generation.enlace.security;

import com.generation.enlace.model.UsuarioModel;
import com.generation.enlace.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Optional<UsuarioModel> usuarioAtual = usuarioRepository.findByUsuarioEmail(usuario);

        usuarioAtual.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return usuarioAtual.map(UserDetailsImpl::new).get();
    }
}
