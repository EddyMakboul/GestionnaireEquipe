package gestionnaire.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Permet la sécurisation et ainsi les actions qu'il peut effectuer
 * Utilisation faite par le directeur (Admin)
 */

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST","PUT","DELETE")
                .allowedHeaders("*")
                .allowedOriginPatterns("*");
    }
}
