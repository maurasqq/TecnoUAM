package com.tecnouam.backend.auth;

import com.tecnouam.backend.entity.Usuario;
import com.tecnouam.backend.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        Usuario usuario = authService.authenticate(request);
        String token = jwtService.generateToken(usuario);

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                "Bearer",
                usuario.getUsername(),
                usuario.getRol()
        );

        return ResponseEntity.ok(response);
    }
}