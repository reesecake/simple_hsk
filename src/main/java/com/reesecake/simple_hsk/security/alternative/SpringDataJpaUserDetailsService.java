package com.reesecake.simple_hsk.security.alternative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SpringDataJpaUserDetailsService implements UserDetailsService {

    private final AppUserRepository repository;

    @Autowired
    public SpringDataJpaUserDetailsService(AppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = this.repository.findUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(),
                AuthorityUtils.createAuthorityList(appUser.getRoles()));
    }
}
