package com.tecnouam.backend.auth;

import com.tecnouam.backend.entity.Usuario;
import com.tecnouam.backend.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String adminPass = System.getenv("DEMO_ADMIN_PASSWORD");
        String userPass = System.getenv("DEMO_USER_PASSWORD");

        if (adminPass == null || adminPass.isBlank() || userPass == null || userPass.isBlank()) {
            log.warn("Variables DEMO_ADMIN_PASSWORD o DEMO_USER_PASSWORD no configuradas en el entorno. Se omitió la siembra de usuarios demo.");
            return;
        }

        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario("admin", passwordEncoder.encode(adminPass), "ADMIN", true);
            usuarioRepository.save(admin);
            log.info("Usuario demo 'admin' inicializado exitosamente.");
        }

        if (!usuarioRepository.existsByUsername("operador")) {
            Usuario operador = new Usuario("operador", passwordEncoder.encode(userPass), "USER", true);
            usuarioRepository.save(operador);
            log.info("Usuario demo 'operador' inicializado exitosamente.");
        }
    }
}