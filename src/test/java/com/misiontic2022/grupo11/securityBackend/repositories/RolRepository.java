package com.misiontic2022.grupo11.securityBackend.repositories;

import com.misiontic2022.grupo11.securityBackend.models.Rol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends CrudRepository<Rol, Integer> {
}
