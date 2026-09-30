package com.paevolution.appiogateway.core.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paevolution.appiogateway.core.domain.Servizi;
import com.paevolution.appiogateway.core.domain.TipoConnettore;

@Repository
public interface ServiziRepository extends CrudRepository<Servizi, Long> {

    Iterable<Servizi> findAll();

    // Optional<Servizi> findByIdcomuneAndCodicecomuneAndSoftware(String idcomune, String codicecomune, String software);

    Optional<Servizi> findByIdServizio(String idServizio);

    Iterable<Servizi> findByTipoConnettore(TipoConnettore tipoConnettore);
}
