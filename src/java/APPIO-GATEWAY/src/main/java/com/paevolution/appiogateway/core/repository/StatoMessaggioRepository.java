package com.paevolution.appiogateway.core.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paevolution.appiogateway.core.domain.StatoMessaggio;

@Repository
public interface StatoMessaggioRepository extends CrudRepository<StatoMessaggio, Long> {

    Optional<StatoMessaggio> findByNome(String stato);
}
