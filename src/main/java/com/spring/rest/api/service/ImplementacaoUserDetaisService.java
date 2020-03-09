package com.spring.rest.api.service;

import com.spring.rest.api.model.Usuario;
import com.spring.rest.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ImplementacaoUserDetaisService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Consulta no banco o usuario

        Usuario usuario = usuarioRepository.findUserByLogin(username);

        if (usuario == null) {
            throw  new UsernameNotFoundException("Usuarrio nao foi encontrado");
        }

        return new User(usuario.getLogin(),
                usuario.getPassword(),
                usuario.getAuthorities());
        
    }
}
