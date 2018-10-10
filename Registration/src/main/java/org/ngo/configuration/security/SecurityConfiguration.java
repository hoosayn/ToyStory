package org.ngo.configuration.security;

import org.ngo.configuration.security.handler.RestAuthenticationEntryPoint;
import org.ngo.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableAutoConfiguration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    public SecurityConfiguration(RestAuthenticationEntryPoint restAuthenticationEntryPoint){
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
//        //To let threads spawned by the secure thread also assume the same security identity
//        //See http://docs.spring.io/spring-security/site/docs/3.0.x/reference/technical-overview.html
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint)
//                .and().csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("**/registration/**").permitAll()
//                .antMatchers("/user/profile").authenticated();

        http.csrf().ignoringAntMatchers("/login");

        http.authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/**/*")
                .denyAll();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void  configure(WebSecurity web){
        web.ignoring().antMatchers("/**/*.{js,html,css}");
    }

}
