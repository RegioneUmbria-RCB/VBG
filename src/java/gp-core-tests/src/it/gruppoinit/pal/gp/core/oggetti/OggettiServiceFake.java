package it.gruppoinit.pal.gp.core.oggetti;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.rest.client.DSSClientException;

public class OggettiServiceFake implements OggettiService {

    @Override
    public PkId newIdFromSequencetable(Oggetti entity) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Oggetti> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    public Oggetti findByIdResult = null;

    @Override
    public Oggetti findById(PkId id) {

	return findByIdResult;
    }

    @Override
    public Oggetti findByIdLazy(PkId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String findNomeById(PkId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String findNomeByCodice(Integer intId) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String findNomeByCodice(String strId) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void insert(Oggetti entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(Oggetti entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(Oggetti entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void deleteAll(List<Integer> objectsIdsToDelete) {

	// TODO Auto-generated method stub
    }

    @Override
    public Oggetti bindDomainObject(Oggetti entity, Class<?> idClass, String idPath) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getSharedFileLink(Integer codiceOggetto) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void resetObjectCached() {

	// TODO Auto-generated method stub
    }

    @Override
    public void updateFilesBloccaModifica(Integer[] codiceoggetto, Integer codiceResponsabile) throws SecurityException {

	// TODO Auto-generated method stub
    }

    @Override
    public void updateFilesRimuoviBloccoModifica(Integer[] codiceoggetto, Integer codiceResponsabile) throws SecurityException {

	// TODO Auto-generated method stub
    }

    @Override
    public void updateFileRimuoviBloccoModifica(Integer codiceoggetto, Integer codiceResponsabile) throws SecurityException {

	// TODO Auto-generated method stub
    }

    @Override
    public InputStream getOggettoAsInputStream(Integer codiceOggetto) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String insertOrGetUID(Integer codiceOggetto) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void evict(Oggetti entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void updateSbloccaOggetto(OggettiMetadati oggettiMetadati) {

	// TODO Auto-generated method stub
    }

    @Override
    public String creaSingoloLinkAllegati(Integer codiceOggetto) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer creaDocumentoConLinkOggetti(Letteretipo lettereTipo, Integer codiceIst, Integer codiceMov, String codiceTipoMov, String uuid) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public byte[] trasformRtfInPdf(Oggetti oggettoRtf) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public byte[] trasformInPdf(Oggetti oggetto, String estensione) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void aggiornaMetadatiCMIS(Integer codiceOggetto) {

	// TODO Auto-generated method stub
    }

    @Override
    public Oggetti insert(MultipartFile multipartFile) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Oggetti convertFileInPdf(Integer codiceOggetto) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Oggetti convertFileInPdfAndSostituisci(Integer codiceOggetto) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void updateOggettoFirmatoCAdES(Oggetti oggetti) {

	// TODO Auto-generated method stub
    }

    @Override
    public void updateOggettoFirmatoPAdES(Oggetti oggetti) {

	// TODO Auto-generated method stub
    }

    @Override
    public void aggiornaSenzaStoricizzare(Oggetti entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public Oggetti convertFileInPdf(Oggetti obj) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Oggetti convertFileInPdfAndSostituisci(Oggetti oggetti) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Oggetti verificaConvertiPdf(Oggetti oggetti) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String insertOrGetSHA256(Integer codiceOggetto) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean scriviSuAlfrescoOCMIS(boolean eCMIS, boolean eApiAlfresco, boolean eApiAlfrescoSolaLettura) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void processaMetadatiFirmaOggetto(Oggetti oggetto) throws DSSClientException {

	// TODO Auto-generated method stub
	
    }
}
