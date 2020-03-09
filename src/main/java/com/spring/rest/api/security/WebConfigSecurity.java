package com.spring.rest.api.security;


import com.spring.rest.api.service.ImplementacaoUserDetaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// Mpaeia URL, enderecos, autoriza ou bloqueia acesso a URL
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private ImplementacaoUserDetaisService implementacaoUserDetaisService;

    // Configura as solicitacoes de acesso por HTTP
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Ativando a protecao conta usuario que nao estao validados por TOKEN
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                // Ativando a permissao para acesso a pagina inicial do sistema EX: sistema.com.br/index*/
                .disable().authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()

                // URL de Logout - Redireciona apos o user deslogar do sistema
                .anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")

                // Mapeia URL de Logout e invalida o usuario
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        // Filtra as requisicoes de login para a autenticacao

        // Filtra demais requisicoes para verificar a presenca do TOKEN JWT no HEADER HTTP

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // Service que ira consultar o usuario no banco de dados
        auth.userDetailsService(implementacaoUserDetaisService)

                // Padrao de codificacao de senha
                .passwordEncoder(new BCryptPasswordEncoder());

    }
}

