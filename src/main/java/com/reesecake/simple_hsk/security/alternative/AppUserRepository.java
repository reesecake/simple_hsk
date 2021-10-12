package com.reesecake.simple_hsk.security.alternative;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AppUserRepository extends PagingAndSortingRepository<AppUser, Long> {

    AppUser findAdminByName(String name);
}
