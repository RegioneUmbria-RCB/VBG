package it.gruppoinit.pal.gp.core.features.protocollazione.logic.fake;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;

public class FakeOggettiMetadatiService implements OggettiMetadatiService {

    private boolean isFirmatoDigitalmente;

    public FakeOggettiMetadatiService(boolean isFirmatoDigitalmente) {

	this.isFirmatoDigitalmente = isFirmatoDigitalmente;
    }

    @Override
    public void insert(OggettiMetadati entity) {

    }

    @Override
    public void update(OggettiMetadati entity) {

    }

    @Override
    public void delete(OggettiMetadati entity) {

    }

    @Override
    public List<OggettiMetadati> findAll(Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public OggettiMetadati findById(OggettiMetadatiId id) {

	return null;
    }

    @Override
    public OggettiMetadati bindDomainObject(OggettiMetadati entity, Class<?> idClass, String idPath) {

	return null;
    }

    @Override
    public OggettiMetadatiId newIdFromSequencetable(OggettiMetadati entity) {

	return null;
    }

    @Override
    public void deleteByOggetto(Integer codiceOggetto) {

    }

    @Override
    public void insertMetadatiPerOggetto(Integer codiceOggetto, List<MetadatiBean> metadati) {

    }

    @Override
    public List<OggettiMetadati> findByOggetto(Integer codiceOggetto) {

	return null;
    }

    @Override
    public void insertInNewTransaction(Integer codiceOggetto, String chiave, String valore) {

    }

    @Override
    public void updateInNewTransaction(Integer codiceOggetto, String chiave, String valore) {

    }

    @Override
    public Integer findByChiaveEValore(String chiave, String valore) {

	return null;
    }

    @Override
    public List<OggettiMetadati> findByOggetto(Integer codiceOggetto, String chiave) {

	return null;
    }

    @Override
    public String calcolaMd5(Integer codiceOggetto) {

	return null;
    }

    @Override
    public boolean isOggettoFirmatoDigitalmente(Integer codiceOggetto) {

	return this.isFirmatoDigitalmente;
    }

    @Override
    public String getMessaggioModificaMetadato(Integer codiceoggetto, String metadato, String valore) {

	return null;
    }

    @Override
    public String getUIDFromCodiceOggetto(Integer codiceOggetto) {

	return null;
    }

    @Override
    public void rimuoviConservazioneSospesa(Integer codiceOggetto) {

    }
}
