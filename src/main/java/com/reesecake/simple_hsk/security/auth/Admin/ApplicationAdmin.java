//package com.reesecake.simple_hsk.security.auth.Admin;
//
//import com.reesecake.simple_hsk.security.auth.ApplicationUser;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import javax.persistence.Entity;
//import java.util.Set;
//
//@Getter
//@Setter
//public class ApplicationAdmin extends ApplicationUser {
//
//    private boolean isAdmin;
//
//    public ApplicationAdmin() {}
//
//    public ApplicationAdmin(String username,
//                            String password,
//                            Set<SimpleGrantedAuthority> grantedAuthorities,
//                            boolean isAccountNonExpired,
//                            boolean isAccountNonLocked,
//                            boolean isCredentialsNonExpired,
//                            boolean isEnabled,
//                            boolean isAdmin) {
//        super(username, password, grantedAuthorities, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled);
//        this.isAdmin = isAdmin;
//    }
//
//}
