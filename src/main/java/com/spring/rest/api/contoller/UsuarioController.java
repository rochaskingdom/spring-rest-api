package com.spring.rest.api.contoller;

import com.spring.rest.api.model.Usuario;
import com.spring.rest.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController /* Arquitetura REST */
@RequestMapping(value = "/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired /* de fosse CDI seria @Inject*/
    private UsuarioRepository usuarioRepository;


    /* Serviço RESTful */
    @GetMapping(value = "/{id}/codigovenda/{venda}", produces = "application/json")
    public ResponseEntity<Usuario> relatorio(@PathVariable(value = "id") Long id
            , @PathVariable(value = "venda") Long venda) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        /*o retorno seria um relatorio*/
        return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
    }


    /* Serviço RESTful */
    @GetMapping(value = "/{id}", produces = "application/json")
    @Cacheable("cacheuser")
    public ResponseEntity<Usuario> init(@PathVariable(value = "id") Long id) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String delete(@PathVariable("id") Long id) {

        usuarioRepository.deleteById(id);

        return "ok";
    }


    @DeleteMapping(value = "/{id}/venda", produces = "application/text")
    public String deletevenda(@PathVariable("id") Long id) {

        usuarioRepository.deleteById(id);

        return "ok";
    }


    /*Vamos supor que o carregamento de usuário seja um processo lento
     * e queremos controlar ele com cache para agilizar o processo*/
    @GetMapping(value = "/", produces = "application/json")
    @CachePut("cacheusuarios")
    public ResponseEntity<List<Usuario>> usuario() throws InterruptedException {

        List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();

        return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
    }

//    END-POINT consulta de usuario por nome
    @GetMapping(value = "/usuarioPorNome/{nome}", produces = "application/json")
    @CachePut("cacheusuarios")
    public ResponseEntity<List<Usuario>> usuarioPorNome(@PathVariable("nome") String nome) throws InterruptedException {

        List<Usuario> list = (List<Usuario>) usuarioRepository.findUserByNome(nome);

        return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
    }


    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid Usuario usuario) {

        for (int pos = 0; pos < usuario.getTelefones().size(); pos++) {
            usuario.getTelefones().get(pos).setUsuario(usuario);
        }

        String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(senhacriptografada);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);

    }


    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {

        /*outras rotinas antes de atualizar*/

        for (int pos = 0; pos < usuario.getTelefones().size(); pos++) {
            usuario.getTelefones().get(pos).setUsuario(usuario);
        }

        Usuario userTemporario = usuarioRepository.findUserByLogin(usuario.getLogin());


        if (!userTemporario.getSenha().equals(usuario.getSenha())) { /*Senhas diferentes*/
            String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
            usuario.setSenha(senhacriptografada);
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);

    }


    @PutMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
    public ResponseEntity updateVenda(@PathVariable Long iduser,
                                      @PathVariable Long idvenda) {
        /*outras rotinas antes de atualizar*/

        //Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity("Venda atualzada", HttpStatus.OK);

    }


    @PostMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
    public ResponseEntity cadastrarvenda(@PathVariable Long iduser,
                                         @PathVariable Long idvenda) {

        /*Aqui seria o processo de venda*/
        //Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new ResponseEntity("id user :" + iduser + " idvenda :" + idvenda, HttpStatus.OK);

    }


}
