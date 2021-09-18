package com.reesecake.simple_hsk.security.auth;

import com.google.common.collect.Lists;
import com.reesecake.simple_hsk.security.auth.Admin.ApplicationAdmin;
import com.reesecake.simple_hsk.security.auth.Student.ApplicationStudent;
import com.reesecake.simple_hsk.security.auth.Teacher.ApplicationTeacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.reesecake.simple_hsk.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {

        ApplicationTeacher mrJazz = new ApplicationTeacher(
                "mrJazz",
                passwordEncoder.encode("password"),
                TEACHER.getGrantedAuthorities(),
                true,
                true,
                true,
                true,
                null
        );
        ApplicationStudent littleGao = new ApplicationStudent(
                "littleGao",
                passwordEncoder.encode("password"),
                STUDENT.getGrantedAuthorities(),
                true,
                true,
                true,
                true,
                mrJazz
        );
        Set<ApplicationStudent> students = Stream.of(littleGao).collect(Collectors.toCollection(HashSet::new));
        mrJazz.setStudents(students);

        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationAdmin(
                        "nathanmohapatra",
                        passwordEncoder.encode("password"),
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationAdmin(
                        "bob",
                        passwordEncoder.encode("password"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true,
                        true
                )
        );
        applicationUsers.add(mrJazz);

        return (applicationUsers);
    }

}
