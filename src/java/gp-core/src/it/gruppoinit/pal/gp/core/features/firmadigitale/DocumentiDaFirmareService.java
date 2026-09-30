package it.gruppoinit.pal.gp.core.features.firmadigitale;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.service.BaseService;

/**
 * 
 * @author
 */
public interface DocumentiDaFirmareService extends BaseService<DocumentiDaFirmare, PkId> {

    /**
     * @see DocumentiDaFirmareDAO#findAll(Integer, Integer)
     */
    public List<DocumentiDaFirmare> findAll(Integer firstResult, Integer maxResult);

    public void clear();

    public List<DocumentiDaFirmare> findDocumentiDaFirmare(Integer codiceresponsabile, int i, int maxResult);

    public List<DocumentiDaFirmare> findDocumentiDaFirmareDTOPerFirmatario(Integer codiceresponsabile, Integer firstResult, Integer maxResult);

    public List<DocumentiDaFirmare> findDocumentiDaFirmareDTOPerRichiedenteAndStato(Integer codiceresponsabile, Boolean isMessiDallutenteLoggato,
	    String flagDaFirmare, Integer firstResult, Integer maxResult);

    public Integer countDocumentiDaFirmareDTOPerRichiedenteAndStato(Integer codiceresponsabile, Boolean isMessiDallutenteLoggato,
	    String flagDaFirmare);

    public int countDocumentiDaFirmarePerFirmatario(Integer codiceresponsabile);

    public int countDocumentiDaFirmarePerRichiedenteAndStato(Integer codice, String flagDaFirmare);

    // public int countDocumentiDaFirmare();
    public List<DocumentiDaFirmare> findByIdOggetto(Integer codiceOggetto);

    public List<DocumentiDaFirmare> findByIdOggettoAndFirmatarioAndIstanza(Integer codiceOggetto, Integer codiceFirmatario, Integer codiceIstanza);

    void update(DocumentiDaFirmare entity, Boolean isFirmatario);

    void insert(DocumentiDaFirmare entity, Boolean isFirmatario);

    public boolean isFirmaCompleta(DocumentiDaFirmare doc);

    public boolean isFirmaRichiesta(DocumentiDaFirmare doc);

    public boolean isFirmaNegata(DocumentiDaFirmare doc);

    public int countDocumentiDafirmarePerOggettoIstanza(Integer codiceOggetto, Integer codiceIstanza);

    public int countDocumentiPerMovimentiAllegati(Integer codiceMovimentoAllegato, String stato);

    public int countDocumentiDafirmarePerOggetto(Integer codiceOggetto);

    public String findReportHTMLOggettoDaFirmare(Integer codiceOggetto);

    public void updateMutiplo(String flagDaFirmare, String annotazioniFirmatario, List<Integer> id_doc_da_firmare);

    public List<DocumentiDaFirmare> findByMovimentiallegati(Integer codiceMovimentiallegati);

    public List<DocumentiDaFirmare> findByMovimentiallegatiDaFirmare(Integer codiceMovimentiallegati);

    public void updateSingolo(DocumentiDaFirmare doc, String flagDaFirmare, String annotazioniFirmatario);

    /**
     * <pre>
     * 1. Converte il file con il codice passato in pdf
     * 2. Recupera tutti i documenti da firmare con il codice oggetto passato
     * 3. Sostituisce il codice oggetto dei record trovati la passo 2, con il nuovo codice oggetto de file pdf(passo 1) 
     * &#64;param codiceOggetto
     * 
     * </pre>
     */
    public void insertTrasformaInPdfDocumentiMessiAllaFirma(Integer codiceOggetto);

    /**
     * Segna il documento come firmato
     * 
     * @param codiceDocumento
     * @return
     */
    public DocumentiDaFirmare updateSegnaComeFirmato(Integer codiceDocumento);

    public Integer insertMultipli(String[] codMovAll, Responsabili firmatario, String annotazioniRichiedente);

    /**
     * Verifica se il codiceoggetto passato è tra i documenti ancora da firmare per quel responsabile
     * 
     * @param codiceOggetto
     * @param codiceFirmatario
     * @return documenti_da_firmare.id o null se non presente o comunque non da firmare
     */
    public List<Integer> findIdDocumentiDaFirmare(Integer codiceOggetto, Integer codiceFirmatario);
}
