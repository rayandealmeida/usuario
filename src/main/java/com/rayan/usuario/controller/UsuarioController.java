package com.rayan.usuario.controller;

import com.rayan.usuario.business.UsuarioService;
import com.rayan.usuario.business.dto.EnderecoDTO;
import com.rayan.usuario.business.dto.TelefoneDTO;
import com.rayan.usuario.business.dto.UsuarioDTO;
import com.rayan.usuario.infrastructure.entify.Telefone;
import com.rayan.usuario.infrastructure.entify.Usuario;
import com.rayan.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /*
     * Metodo Post para cadastrar um usuário
     *  */
    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }
    //

    /*
     * Metodo Post para login
     *  */
    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuarioDTO.getEmail(),
                        usuarioDTO.getSenha()
                )
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }
    //

    /*
     * Metodo Get buscar usuario por email
     *  */
    @GetMapping
    public ResponseEntity<UsuarioDTO> buscaUsuariopPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioEmail(email));
    }
    //

    /*
     * Metodo Delete para deletar um usuário por email
     *  */
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    /*
     * Metodo Post para atualizar dados usuario
     *  */
    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizarDadosUsuario(@RequestBody UsuarioDTO dto,
                                                            @RequestHeader("Authorization")String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }
    //

    /*
     * Metodo Post para atualizar endereço e telefone
     *  */
    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO dto,
                                                        @RequestParam("id")Long id){
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id,dto));
    }
    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO dto,
                                                        @RequestParam("id")Long id){
        return ResponseEntity.ok(usuarioService.atualizarTelefone(id,dto));
    }

    //

    /*
     * Metodo Post para cadastrar endereço e telefone
     *  */
    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> cadastrarEndereco(@RequestBody EnderecoDTO dto,
                                                        @RequestHeader("Authorization")String token){
        return ResponseEntity.ok(usuarioService.cadastrarEndereco(token,dto));
    }
    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> cadastrarTelefone(@RequestBody TelefoneDTO dto,
                                                         @RequestHeader("Authorization")String token){
        return ResponseEntity.ok(usuarioService.cadastrarTelefone(token,dto));
    }
    //

}
