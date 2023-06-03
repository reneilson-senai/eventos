package com.senai.eventos.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.senai.eventos.models.TokenSenha;
import com.senai.eventos.models.Usuario;
import com.senai.eventos.repositories.TokenSenhaRepository;
import com.senai.eventos.repositories.UsuarioRepository;
import com.senai.eventos.serializers.TokenSenhaDTO;

@Service
public class TokenSenhaService {
    @Autowired
    private TokenSenhaRepository repository;

    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String createTokenSenha(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var usuario = (Usuario) auth.getPrincipal();
        var token = UUID.randomUUID().toString();
        var expiracao = LocalDateTime.now().plusDays(1);
        var tokenSenha = new TokenSenha(null, usuario, expiracao, token);
        return tokenSenha.getToken();
    }

    public boolean mudarSenha(TokenSenhaDTO dto) {
        // Valida token
        var opt = repository.findByToken(dto.token());
        if(!opt.isPresent()){
            return false;
        }
        var tokenSenha = opt.get();
        
        // Verifica expiração
        if (tokenSenha.getDataExpiracao().compareTo(LocalDateTime.now()) < 0) {
          repository.delete(tokenSenha);
          return false;
        }

        // Valida senha
        var usuario = tokenSenha.getUsuario();
        var senha = bCryptPasswordEncoder.encode(dto.senhaAntiga());
        if(usuario.getSenha() != senha){
            return false;
        }

        // Atualiza com nova senha
        var novaSenha = bCryptPasswordEncoder.encode(dto.novaSenha());
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);

        return true;
      }
}
