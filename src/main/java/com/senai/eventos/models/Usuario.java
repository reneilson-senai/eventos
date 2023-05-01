package com.senai.eventos.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.senai.eventos.serializers.UsuarioDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;
    @Enumerated(EnumType.STRING)
    private Papel papel = Papel.ROLE_USER;
    @Embedded
    private FileInfo foto;

    @OneToMany(mappedBy = "organizador")
    private List<Evento> eventosOrganizando = new ArrayList<Evento>();

    public Usuario(UsuarioDTO dto){
        this.nome = dto.nome();
        this.email = dto.email();
        this.papel = dto.papel();
    }

    public Usuario(String nome, String email, String senha){
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.papel = Papel.ROLE_USER;
    }

    public Usuario(String nome, String email, String senha, Papel papel){
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.papel = papel;
    }

    public void addEvento(Evento evento){
        evento.setOrganizador(this);
        this.eventosOrganizando.add(evento);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getPapel().toString()));
    }
    
    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
