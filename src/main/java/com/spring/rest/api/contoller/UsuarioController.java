package com.spring.rest.api.contoller;

import com.spring.rest.api.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController //Arquitetura REST
@RequestMapping(value = "/usuario")
public class UsuarioController {

    //Servico RESTfull
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> init() {

        Usuario usuario = new Usuario();
        usuario.setId(50L);
        usuario.setLogin("vinicius@gmail.com");
        usuario.setNome("Vinicius Rocha");
        usuario.setSenha("13235");

        Usuario usuario2 = new Usuario();
        usuario2.setId(8L);
        usuario2.setLogin("dsd@gmail.com");
        usuario2.setNome("Vinicius sdsd");
        usuario2.setSenha("sdsd");

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        usuarios.add(usuario2);


        return new ResponseEntity(usuarios, HttpStatus.OK);
    }
}
