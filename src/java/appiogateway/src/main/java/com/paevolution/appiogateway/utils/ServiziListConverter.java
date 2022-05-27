package com.paevolution.appiogateway.utils;

import java.util.List;

import org.modelmapper.AbstractConverter;

import com.paevolution.appiogateway.core.domain.Servizi;

public class ServiziListConverter extends AbstractConverter<List<Servizi>, List<String>> {

    @Override
    protected List<String> convert(List<Servizi> servizis) {

	// return
	// servizis.stream().map(Servizi::getTipoConnettore).collect(Collectors.toList());
	return null;
    }
}
