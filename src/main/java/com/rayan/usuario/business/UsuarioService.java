package com.rayan.usuario.business;

import com.rayan.usuario.business.converter.UsuarioConverter;
import com.rayan.usuario.business.dto.EnderecoDTO;
import com.rayan.usuario.business.dto.TelefoneDTO;
import com.rayan.usuario.business.dto.UsuarioDTO;
import com.rayan.usuario.infrastructure.entify.Endereco;
import com.rayan.usuario.infrastructure.entify.Telefone;
import com.rayan.usuario.infrastructure.entify.Usuario;
import com.rayan.usuario.infrastructure.exceptions.ConflictException;
import com.rayan.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.rayan.usuario.infrastructure.repository.EnderecoRepository;
import com.rayan.usuario.infrastructure.repository.TelefoneRepository;
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
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    public UsuarioDTO buscarUsuarioEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email).orElseThrow(() ->
                    new ResolutionException("Email não encontrado!!" + email))
            );
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("E-mail não encontrado!!" + email);
        }
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
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado!"));

        // mescla os dados recebidos na requisição DTO com os dados do banco
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        // salva os dados do usuário convertido e depois pega o retorno e converteu para usuárioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {
        // buscando endereço pelo id
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("ID do Endereço não encontrado" + idEndereco));

        // atualizando endereço do usuario passando dto e entity
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        // salvando endereço e convertendo para dto
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizarTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {

        // buscando telefone pelo ID
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("ID do Telefone não encontrado" + idTelefone));

        // atualizar telefone do usuario passando dto e entity
        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);

        // salvando o telefone atualizado e convertendo para DTO
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastrarEndereco(String token, EnderecoDTO dto){
        // extrair email do token para obter informações do usuario
        String email = jwtUtil.extrairEmailDoToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResolutionException("Email não encontrado!!" + email));
        // transformamos esses dados DTO em Entity
        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());

        // salvamos esses dados entity no banco de dados e retornamos ele convertido em DTO
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);
    }

    public TelefoneDTO cadastrarTelefone(String token, TelefoneDTO dto){
        // extrair email do token para obter informações do usuario
        String email = jwtUtil.extrairEmailDoToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResolutionException("Email não encontrado!!" + email));

        // transformamos esses dados DTO em Entity
        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());

        // salvamos esses dados entity no banco de dados e retornamos ele convertido em DTO
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
