package com.tecnouam.backend.auth;


public record LoginResponseDTO(
        String token,
        String type,
        String username,
        String role
) {}