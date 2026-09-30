package it.gruppoinit.pal.gp.core.documentihelper;

import it.gruppoinit.pal.gp.core.service.SoftwareByCodiceOggettoReaderService;

import java.util.HashSet;
import java.util.Set;

public class SoftwareByCodiceOggettoReaderFake implements SoftwareByCodiceOggettoReaderService {

    public Set<String> findSoftwareByCodiceOggettoResult = new HashSet<String>();

    @Override
    public Set<String> findSoftwareByCodiceOggetto(Integer codiceOggetto) {

	return findSoftwareByCodiceOggettoResult;
    }
}
