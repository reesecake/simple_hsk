//package com.reesecake.simple_hsk.security.auth.Student;
//
//import com.reesecake.simple_hsk.security.auth.ApplicationUser;
//import com.reesecake.simple_hsk.security.auth.Teacher.ApplicationTeacher;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import javax.persistence.Entity;
//import javax.persistence.ManyToOne;
//import java.util.Set;
//
//@Getter
//@Setter
//public class ApplicationStudent extends ApplicationUser {
//
//    @ManyToOne
//    private ApplicationTeacher teachers;
//
//    public ApplicationStudent() {}
//
//    public ApplicationStudent(String username,
//                              String password,
//                              Set<SimpleGrantedAuthority> grantedAuthorities,
//                              boolean isAccountNonExpired,
//                              boolean isAccountNonLocked,
//                              boolean isCredentialsNonExpired,
//                              boolean isEnabled,
//                              ApplicationTeacher teachers) {
//        super(username, password, grantedAuthorities, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled);
//        this.teachers = teachers;
//    }
//
//}
