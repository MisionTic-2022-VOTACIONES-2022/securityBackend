package com.misiontic2022.grupo11.securityBackend.repositories;

import com.misiontic2022.grupo11.securityBackend.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
