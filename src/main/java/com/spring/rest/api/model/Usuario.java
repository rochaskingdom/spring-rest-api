package com.spring.rest.api.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario implements UserDetails {

    public Usuario() {

    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;

    private String senha;

    private String nome;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Telefone> telefones = new ArrayList<Telefone>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_role",
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"usuario_id", "role_id"},
                    name = "unique_role_user"),
                    joinColumns = @JoinColumn(
                            name = "usuario_id",
                            referencedColumnName = "id",
                            table = "usuario",
                            unique = false,
                            foreignKey = @ForeignKey(
                                    name = "usuario_fk",
                                    value = ConstraintMode.CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id",
                    table = "role",
                    unique = false,
                    updatable = false,
                    foreignKey = @ForeignKey(
                            name = "role_fk",
                            value = ConstraintMode.CONSTRAINT))
    )

    private List<Role> roles; // Os papeis ou acessos

    public Usuario(List<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Sao os acessos do usuario ROLE_ADMIN OU ROLE_VISITANTE
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
