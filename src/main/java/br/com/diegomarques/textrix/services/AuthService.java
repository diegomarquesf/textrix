package br.com.diegomarques.textrix.services;

import br.com.diegomarques.textrix.domains.dtos.AuthRequest;
import br.com.diegomarques.textrix.domains.dtos.AuthResponse;
import br.com.diegomarques.textrix.repositories.UsuarioRepository;
import br.com.diegomarques.textrix.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       UsuarioRepository usuarioRepository,
                       JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse authenticate(AuthRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.senha()
                )
        );

        var usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado para o e-mail informado!"));

        String token = jwtUtil.generateToken(usuario);
        System.out.println("TOKEN GERADO: " + token);

        return new AuthResponse(token, usuario.getEmail(), usuario.getRoles());
    }
}
