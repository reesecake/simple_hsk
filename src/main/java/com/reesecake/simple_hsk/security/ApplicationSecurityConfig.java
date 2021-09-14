package com.reesecake.simple_hsk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.concurrent.TimeUnit;

import static com.reesecake.simple_hsk.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize and @PostAuthorize
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    // Permitted to all
                    .antMatchers("/built/**", "/css/**", "/assets/**").permitAll()
                    .antMatchers("/", "/about", "/hsk/*", "/vocab-lists", "/quiz").permitAll()
                    // Role-based authentication
//                    .antMatchers("/api/**").hasRole(ADMIN.name())
                    .antMatchers(HttpMethod.GET, "/api/vocabs/**").permitAll()
//                    .antMatchers(HttpMethod.POST, "/api/vocabs/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/vocabs/search").permitAll()
                    // TODO: Permission-based authentication?
                    .anyRequest().authenticated()
                    .and()
                // Form-based authentication
                .formLogin()
                    // Custom login page
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/", true)
                    .and()
                .rememberMe() // Defaults to 2 weeks
                    // Custom expiration time
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7))
                    .key("uniqueAndSecret")
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login")
                    .and()
                .csrf().disable(); // Enabling breaks login
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails reeseLamUser = User.builder()
                .username("reeselam")
                .password(passwordEncoder.encode("password"))
                //.roles(ADMIN.name()) // ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails nathanMohapatraUser = User.builder()
                .username("nathanmohapatra")
                .password(passwordEncoder.encode("password"))
                //.roles(STUDENT.name()) // ROLE_STUDENT
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                reeseLamUser,
                nathanMohapatraUser
        );
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(HttpMethod.POST, "/**");
//    }

}
