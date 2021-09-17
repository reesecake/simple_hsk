package com.reesecake.simple_hsk.security.alternative;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AdminRepository extends Repository<Admin, Long> {

    Admin save(Admin admin);

    Admin findAdminByName(String name);
}
