package com.paevolution.appiogateway.core.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paevolution.appiogateway.core.domain.StatoMessaggio;
import com.paevolution.appiogateway.core.repository.StatoMessaggioRepository;
import com.paevolution.appiogateway.core.service.StatoMessaggioService;
import com.paevolution.appiogateway.utils.StatiMessaggioEnum;

@Service
public class StatoMessaggioServiceImpl implements StatoMessaggioService {

    private StatoMessaggioRepository statoMessaggioRepository;

    @Autowired
    public void setStatoMessaggioRepository(StatoMessaggioRepository statoMessaggioRepository) {

	this.statoMessaggioRepository = statoMessaggioRepository;
    }

    @Override
    public Optional<StatoMessaggio> findByNome(StatiMessaggioEnum statiMessaggioEnum) {

	return this.statoMessaggioRepository.findByNome(statiMessaggioEnum.name());
    }
}
