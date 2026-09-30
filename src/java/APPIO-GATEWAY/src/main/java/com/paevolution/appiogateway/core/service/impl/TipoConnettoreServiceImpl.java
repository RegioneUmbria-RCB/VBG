package com.paevolution.appiogateway.core.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paevolution.appiogateway.core.domain.TipoConnettore;
import com.paevolution.appiogateway.core.repository.TipoConnettoreRepository;
import com.paevolution.appiogateway.core.service.TipoConnettoreService;

@Service
public class TipoConnettoreServiceImpl implements TipoConnettoreService {

    private TipoConnettoreRepository tipoConnettoreRepository;

    @Autowired
    public void setTipoConnettoreRepository(TipoConnettoreRepository tipoConnettoreRepository) {

	this.tipoConnettoreRepository = tipoConnettoreRepository;
    }

    @Override
    public Optional<TipoConnettore> findByNome(String nome) {

	return this.tipoConnettoreRepository.findByNome(nome);
    }
}
