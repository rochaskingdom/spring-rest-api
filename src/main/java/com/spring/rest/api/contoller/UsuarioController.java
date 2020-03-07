package com.spring.rest.api.contoller;

import com.spring.rest.api.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> init() {
        return new ResponseEntity("Ola Usuario REST Spring Boot", HttpStatus.OK);
    }
}
