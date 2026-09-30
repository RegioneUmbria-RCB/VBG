package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogico;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestata;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.service.helper.ZipLogicoLinkHelper;

/**
 * 
 * @author
 */
public interface MovimentiZipLogicoService {

    public Integer contaDocumenti(Integer codiceMovimento);

    public List<MovimentiZipLogico> findAll(Integer firstResult, Integer maxResult);

    public void insertZipLogico(Integer codiceMovimento, DocumentiHelper documentiHelper) throws Exception;

    public Set<MovimentiZipLogico> findByMovimento(Integer codiceMovimento);

    public List<MovimentiZipLogicoDTO> findMovimentiZipLogicoDTOByMovimento(Integer codicemovimento);

    public Boolean isDocumentoPresenteInZipLogico(Integer codiceZipLogico, Integer codiceMovimento, Integer codiceDocumento, String associationPath);

    public Boolean isDocumentoPresenteInZipLogico(Integer codiceDocumento, String associationPath);

    public Boolean isZipLogicoExistInMovimento(Integer codicemovimento);

    public Set<MovimentiZipLogico> findMovimentiZipLogicoByMovimento(Integer codicemovimento);

    public DocumentiHelper findDocumentiZipLogicoToDisplay(Integer codicemovimento);

    public void deleteDettagli(Integer codicemovimento, DocumentiHelper documentiHelper) throws Exception;

    public void deleteDettaglio(MovimentiZipLogico entity);

    public void insertDettaglio(MovimentiZipLogico entity);

    public void updateDettaglio(MovimentiZipLogico entity);

    public MovimentiZipLogico findById(PkId id);

    public MovimentiZipLogicoTestataHelper findByCodiceMovimento(Integer codiceMovimento);

    /**
     * La funzionalità elimina tutti i record di MOVIMENTI_ZIP_LOGICO_TESTATA e MOVIMENTI_ZIP_LOGICO per il
     * codicemovimento passato
     * 
     * @param codiceMovimento
     */
    public void eliminaZipLogicoByCodiceMovimento(Integer codiceMovimento);

    /**
     * La funzionalità crea le testate mancanti e viene utilizzata in fase di setup
     */
    public void upgrCreaTestate();

    /**
     * La funzionalità genera uno zip logico prendendo gli id degli allegati dagli allegati del movimento e impostando
     * il GUID_COLLEGATO se presente
     * 
     * @param codiceMovimento
     * @param movimentiallegatis
     * @param guidOrigine
     */
    public void generaZipLogicoDaZipLogicoCollegato(Integer codiceMovimento, Set<Movimentiallegati> movimentiallegatis, String guidOrigine);

    /**
     * Non è modificabile se protocollato o inviato con STC
     * 
     * @return
     */
    public boolean checkIsModificabile(Integer codiceMovimento);

    public MovimentiZipLogicoTestata findTestataByCodiceMovimento(Integer codiceMovimento);

    public void updateDocAllegatoTestata(Integer codiceMovimento, Integer codiceOggettoDocAllegato);

    /**
     * aggiunge all'oggetto documentiHelper i documenti registrati in uno zip logico
     * 
     * @param codiceMovimento
     * @param documentiHelper
     * @return
     */
    public DocumentiHelper manageDocumentiZipLogico(Integer codiceMovimento, DocumentiHelper documentiHelper);

    public String insertOrGetSHA256(Integer codiceoggetto);

    public Boolean isZipLogicoDocumentoAllegato(Integer codicemovimento);

    public ZipLogicoLinkHelper creaLinkZipLogico(Integer codiceMovimento);

    /**
     * Ritorna i riferimenti di protocollazione della riga di zip logico
     * 
     * @param id
     * @return
     */
    public NumeroDataProtocolloZipLogico getNumeroDataProtocolloZipLogico(MovimentiZipLogicoDTO movimentiZipLogicoDTO);
}
