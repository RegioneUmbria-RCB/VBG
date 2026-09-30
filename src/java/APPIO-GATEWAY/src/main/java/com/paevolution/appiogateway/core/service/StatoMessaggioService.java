package com.paevolution.appiogateway.core.service;

import java.util.Optional;

import com.paevolution.appiogateway.core.domain.StatoMessaggio;
import com.paevolution.appiogateway.utils.StatiMessaggioEnum;

public interface StatoMessaggioService {

    public Optional<StatoMessaggio> findByNome(StatiMessaggioEnum statiMessaggioEnum);
}
