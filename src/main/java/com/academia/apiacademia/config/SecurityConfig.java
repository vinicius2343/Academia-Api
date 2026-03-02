package com.academia.apiacademia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuração de segurança da aplicação
 * Define beans de segurança reutilizáveis
 */
@Configuration
public class SecurityConfig {

    /**
     * Bean para codificação de senha usando BCrypt
     * BCrypt é um algoritmo seguro e adaptativo para hash de senhas
     * @return PasswordEncoder configurado com BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

