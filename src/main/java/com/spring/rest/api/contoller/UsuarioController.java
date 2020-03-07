package com.spring.rest.api.contoller;

import com.spring.rest.api.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //Arquitetura REST
@RequestMapping(value = "/usuario")
public class UsuarioController {

    //Servico RESTfull
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> init(@RequestParam(value = "nome", required = true,
            defaultValue = "Nome nao informado") String nome, @RequestParam("salario") Long salario) {

        System.out.printf("Parametro sendo recebido " + nome);

        return new ResponseEntity("Ola Usuario REST Spring Boot seu nome e: " + nome
                + " Salario e " + salario, HttpStatus.OK);
    }
}
