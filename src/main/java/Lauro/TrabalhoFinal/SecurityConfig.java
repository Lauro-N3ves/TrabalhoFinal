package Lauro.TrabalhoFinal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Desabilitar CSRF para simplificar
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()  // Permitir todas as requisições sem autenticação
                )
                .httpBasic().disable();  // Desabilitar autenticação básica

        return http.build();
    }
}
