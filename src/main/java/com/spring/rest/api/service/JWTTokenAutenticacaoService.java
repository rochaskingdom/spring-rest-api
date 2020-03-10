package com.spring.rest.api.service;

import com.spring.rest.api.ApplicationContextLoad;
import com.spring.rest.api.model.Usuario;
import com.spring.rest.api.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@Component
public class JWTTokenAutenticacaoService {

    //     Tempo de validade do Token - 2 dias
    private static final long EXPIRATION_TIME = 172800000;

    //     Uma senha unica para compor a autenticacao e ajudar na seguranca
    private static final String SECRET = "SenhaDificil";

    //     Prefixo padrao de TOKEN
    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    //     Gerando TOKEN de autenticacao e adicionando ao cabecalho e resposta HTTP
    public void addAuthentication(HttpServletResponse response, String username) throws Exception {

        // Montagem do TOKEN
        String JWT = Jwts.builder() // Chama o gerador de TOKEN
                .setSubject(username) // Adiciona o ususario
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Tempo de expiracao
                .signWith(SignatureAlgorithm.ES256, SECRET).compact(); // Compactacao e algoritmo de geracao de senha

//        Junta o TOKEN com o prefixo
        String token = TOKEN_PREFIX + " " + JWT; // Bearer fdfk4646$T#gSG56sdgd4sgfewe#$%%44

//        Adiciona no cabecalho HTTP
        response.addHeader(HEADER_STRING, token); // Authorization: Bearer fdfk4646$T#gSG56sdgd4sgfewe#$%%44

//        Escreve TOKEN como resposta no corpo HTTP
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }

    //    Retorna o usuario validado com TOKEN ou caso nao seja valido retorna null
    public Authentication getAuthentication(HttpServletRequest request) {

//        Pega o TOKEN enviado no cabecalho HTTP
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
//            Faz a validacao do TOKEN do usuario na requisicao
            String user = Jwts.parser().setSigningKey(SECRET) // Bearer fdfk4646$T#gSG56sdgd4sgfewe#$%%44
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")) // fdfk4646$T#gSG56sdgd4sgfewe#$%%44
                    .getBody().getSubject(); // Joao Silva
            if (user != null) {

                Usuario usuario = ApplicationContextLoad.getApplicationContext()
                        .getBean(UsuarioRepository.class).findUserByLogin(user);

                if (usuario != null) {

                    return new UsernamePasswordAuthenticationToken
                            (usuario.getLogin(), usuario.getSenha(), usuario.getAuthorities());
                }
            }
        }
        return null; // Nao autorizado
    }
}
