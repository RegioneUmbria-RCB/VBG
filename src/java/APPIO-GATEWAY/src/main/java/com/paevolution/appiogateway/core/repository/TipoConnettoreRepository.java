package com.paevolution.appiogateway.core.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paevolution.appiogateway.core.domain.TipoConnettore;

@Repository
public interface TipoConnettoreRepository extends CrudRepository<TipoConnettore, Long> {

    Optional<TipoConnettore> findByNome(String name);
}
