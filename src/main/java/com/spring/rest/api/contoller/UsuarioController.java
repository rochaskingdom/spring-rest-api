package com.spring.rest.api.contoller;

import com.spring.rest.api.model.Usuario;
import com.spring.rest.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //Arquitetura REST
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //  Servico RESTfull
    @GetMapping(value = "/{id}/codigovenda/{venda}", produces = "application/json")
    public ResponseEntity<Usuario> relatorio(@PathVariable(value = "id") Long id,
                                             @PathVariable(value = "venda") Long venda) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        // retorno seria um relatorio
        return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
    }

    // Servico RESTfull
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Usuario> listId(@PathVariable(value = "id") Long id) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String delete(@PathVariable("id") Long id) {

        usuarioRepository.deleteById(id);

        return "ok";
    }

    @DeleteMapping(value = "/{id}/venda/", produces = "application/text")
    public String deleteVenda(@PathVariable("id") Long id) {

        usuarioRepository.deleteById(id); // iria deletar toas as vendas do usuario

        return "ok";
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Usuario>> listUsuario() {

        List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();

        return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {

        usuario.getTelefones().forEach(t -> t.setUsuario(usuario));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.CREATED);
    }

    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {

        // outras rotinas antes de atualizar

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
    }

    @PostMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
    public ResponseEntity<Usuario> cadastrarVenda(@PathVariable Long iduser,
                                                  @PathVariable Long idvenda) {

        // Aqui seria o processo de venda
        // Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity("id user: " + iduser + "idvenda: " + idvenda, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
    public ResponseEntity updateVenda(@RequestBody Usuario usuario) {

        // outras rotinas antes de atualizar

        // Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity("Venda atualizada", HttpStatus.OK);
    }

}
