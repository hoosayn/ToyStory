/*
package org.ngo.tszuul.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {

    Logger LOGGER = LoggerFactory.getLogger(CorsConfiguration.class);

//    @Value("${allowed-origins}") String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        LOGGER.info("allowed orgins [] ", allowedOrigins);
        registry.addMapping("/**")
//                .allowedOrigins(allowedOrigins.split(","))
                .allowedOrigins("*")
                .allowedMethods("POST","GET");
    }
}
*/
