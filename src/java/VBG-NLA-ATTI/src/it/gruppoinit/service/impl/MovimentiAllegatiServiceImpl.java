package it.gruppoinit.service.impl;

import it.gruppoinit.service.MovimentiAllegatiService;

import org.apache.commons.lang.NotImplementedException;

public class MovimentiAllegatiServiceImpl implements MovimentiAllegatiService {

    @Override
    public void insertCodiceOggetto(Integer codiceOggetto, Integer codice, String idcomune) {

	// Non è stata implementatato perchè è da decidere se il riferimento all'oggetto deve essere messo su MOVIMENTI_ATTI o MOVIMENTI_ALLEGATI
	throw new NotImplementedException("Metodo non implementato");
    }
}
