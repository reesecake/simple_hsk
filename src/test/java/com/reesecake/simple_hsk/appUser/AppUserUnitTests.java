package com.reesecake.simple_hsk.appUser;

import com.reesecake.simple_hsk.security.alternative.AppUser;
import com.reesecake.simple_hsk.security.alternative.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class AppUserUnitTests {

    @Autowired
    AppUserRepository appUserRepository;

    @BeforeEach
    void clearRepo() {
        appUserRepository.deleteAll();
    }

    @Test
    void shouldSetUsername() {
        AppUser appUser = new AppUser("testusername", "testusername@simplehsk.com", "password", "ROLE_STUDENT");

        appUser.setUsername("newusername");

        Assertions.assertEquals("newusername", appUser.getUsername());
    }

    @Test
    void shouldSetEmail() {
        AppUser appUser = new AppUser("testusername", "testusername@simplehsk.com", "password", "ROLE_STUDENT");

        appUser.setEmail("newEmail@simplehsk.com");

        Assertions.assertEquals("newEmail@simplehsk.com", appUser.getEmail());
    }

    @Test
    void shouldSetPassword() {
        AppUser appUser = new AppUser("testusername", "testusername@simplehsk.com", "password", "ROLE_STUDENT");

        appUser.setPassword("newPassword");

        Assertions.assertEquals("newPassword", appUser.getPassword());
    }

    @Test
    void shouldSetRoles() {
        AppUser appUser = new AppUser("testusername", "testusername@simplehsk.com", "password", "ROLE_STUDENT");

        String[] newRoles = new String[]{"ROLE_TEACHER"};

        appUser.setRoles(newRoles);

        String[] expectedRoles = new String[]{"ROLE_TEACHER"};

        Assertions.assertArrayEquals(expectedRoles, appUser.getRoles());
    }
}
