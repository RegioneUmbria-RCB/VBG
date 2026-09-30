package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.SoftwareByCodiceOggettoReaderService;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("compositeSoftwareByCodiceOggettoReader")
public class CompositeSoftwareByCodiceOggettoReaderServiceImpl implements SoftwareByCodiceOggettoReaderService {

    private DocumentiistanzaService documentiistanzaServiceImpl;
    private MovimentiallegatiService movimentiallegatiServiceImpl;

    @Autowired
    public CompositeSoftwareByCodiceOggettoReaderServiceImpl(DocumentiistanzaService documentiistanzaServiceImpl,
	    MovimentiallegatiService movimentiallegatiServiceImpl) {

	this.documentiistanzaServiceImpl = documentiistanzaServiceImpl;
	this.movimentiallegatiServiceImpl = movimentiallegatiServiceImpl;
    }

    @Override
    public Set<String> findSoftwareByCodiceOggetto(Integer codiceOggetto) {

	Set<String> findSoftwareByCodiceOggettoResult = documentiistanzaServiceImpl.findSoftwareByCodiceOggetto(codiceOggetto);
	findSoftwareByCodiceOggettoResult.addAll(movimentiallegatiServiceImpl.findSoftwareByCodiceOggetto(codiceOggetto));
	return findSoftwareByCodiceOggettoResult;
    }
}
