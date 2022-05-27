package com.paevolution.appiogateway.core.service;

import java.util.Optional;

import com.paevolution.appiogateway.core.domain.TipoConnettore;

public interface TipoConnettoreService {

    public Optional<TipoConnettore> findByNome(String nome);
}
