package com.rayan.usuario.infrastructure.security;



import com.rayan.usuario.infrastructure.entify.Usuario;
import com.rayan.usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Repositório para acessar dados de usuário no banco de dados
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Implementação do metodo para carregar detalhes do usuário pelo e-mail
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("EMAIL RECEBIDO NO USERDETAILS: [" + email + "]");
        // Busca o usuário no banco de dados pelo e-mail
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        System.out.println("EMAIL ENCONTRADO NO BANCO: [" + usuario.getEmail() + "]");
        // Cria e retorna um objeto UserDetails com base no usuário encontrado
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail()) // Define o nome de usuário como o e-mail
                .password(usuario.getSenha()) // Define a senha do usuário
                .build(); // Constrói o objeto UserDetails
    }
}
