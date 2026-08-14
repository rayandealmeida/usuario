package com.rayan.usuario.business;

import com.rayan.usuario.business.converter.UsuarioConverter;
import com.rayan.usuario.business.dto.UsuarioDTO;
import com.rayan.usuario.infrastructure.entify.Usuario;
import com.rayan.usuario.infrastructure.exceptions.ConflictException;
import com.rayan.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.rayan.usuario.infrastructure.repository.UsuarioRepository;
import com.rayan.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }


    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado! " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado!!" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResolutionException("Email não encontrado!!" + email));
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        // busca o email do usuário através do token
        String email = jwtUtil.extrairEmailDoToken(token.substring(7));

        //criptografia de senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        // busca os dados do usuário no banco
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado!"));
        // mescla os dados recebidos na requisição DTO com os dados do banco
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
        // salva os dados do usuário convertido e depois pega o retorno e converteu para usuárioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }
}
