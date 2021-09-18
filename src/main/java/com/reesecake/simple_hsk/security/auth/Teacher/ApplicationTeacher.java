package com.reesecake.simple_hsk.security.auth.Teacher;

import com.reesecake.simple_hsk.security.auth.ApplicationUser;
import com.reesecake.simple_hsk.security.auth.Student.ApplicationStudent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
public class ApplicationTeacher extends ApplicationUser {

    @OneToMany
    private Set<ApplicationStudent> students;

    public ApplicationTeacher() {}

    public ApplicationTeacher(String username,
                              String password,
                              Set<SimpleGrantedAuthority> grantedAuthorities,
                              boolean isAccountNonExpired,
                              boolean isAccountNonLocked,
                              boolean isCredentialsNonExpired,
                              boolean isEnabled,
                              Set<ApplicationStudent> students) {
        super(username, password, grantedAuthorities, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled);
        this.students = students;
    }

}
