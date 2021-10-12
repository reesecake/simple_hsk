package com.reesecake.simple_hsk.security;

import com.reesecake.simple_hsk.security.alternative.AppUser;
import com.reesecake.simple_hsk.security.alternative.SpringDataJpaUserDetailsService;
//import com.reesecake.simple_hsk.security.auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize and @PostAuthorize annotations enabled
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final PasswordEncoder passwordEncoder;
//    private final ApplicationUserService applicationUserService;
    @Autowired
    private SpringDataJpaUserDetailsService userDetailsService;

//    @Autowired
//    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
//                                     ApplicationUserService applicationUserService) {
//        this.passwordEncoder = passwordEncoder;
//        this.applicationUserService = applicationUserService;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/built/**", "/css/**", "/assets/**").permitAll()
                    .antMatchers("/", "/about", "/hsk/*", "/vocab-lists", "/quiz").permitAll()
//                    .antMatchers("/api/**").hasRole(ADMIN.name())
                    .antMatchers(HttpMethod.GET, "/api/vocabs/**").permitAll()
//                    .antMatchers(HttpMethod.POST, "/api/vocabs/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/vocabs/search").permitAll()
                    .antMatchers("/api/**").hasRole("ADMIN")
                    // TODO: Permission-based authentication?
                    .anyRequest().authenticated()
                    .and()
                // Form-based authentication
                .formLogin()
                    // Custom login page
                    .loginPage("/auth/login").permitAll()
                    .defaultSuccessUrl("/", true)
                    .and()
                .rememberMe() // Defaults to 2 weeks
                    // Custom expiration time
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7))
                    .key("uniqueAndSecret")
                    .and()
                .logout()
                    .logoutUrl("/auth/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/auth/login")
                    .and()
                .csrf().disable(); // Enabling breaks login
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
//                .authenticationProvider(daoAuthenticationProvider())
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(AppUser.PASSWORD_ENCODER);
    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder);
//        provider.setUserDetailsService(applicationUserService);
//        return provider;
//    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(HttpMethod.POST, "/**");
//    }

}
