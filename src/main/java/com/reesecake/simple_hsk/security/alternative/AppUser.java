package com.reesecake.simple_hsk.security.alternative;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class AppUser {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private @Id @GeneratedValue Long id;
    private String name;
    private @JsonIgnore
    String password;

    private String[] roles;

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    protected AppUser() {}

    public AppUser(String name, String password, String... roles) {

        this.name = name;
        this.setPassword(password);
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(id, appUser.id) &&
                Objects.equals(name, appUser.name) &&
                Objects.equals(password, appUser.password) &&
                Arrays.equals(roles, appUser.roles);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, name, password);
        result = 31 * result + Arrays.hashCode(roles);
        return result;
    }
}
