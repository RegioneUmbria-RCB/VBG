package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.SpuntistiMercatiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SpuntistiMercati;
import it.gruppoinit.pal.gp.core.domain.helper.SpuntistiMercatiDTO;
import it.gruppoinit.pal.gp.core.service.helper.SpuntistiMercatiHelper;

/**
 * 
 * @author
 */
public interface SpuntistiMercatiService extends BaseService<SpuntistiMercati, PkId> {

    /**
     * @see SpuntistiMercatiDAO#findAll(Integer, Integer)
     */
    public List<SpuntistiMercati> findAll(Integer firstResult, Integer maxResult);

    public List<SpuntistiMercati> findByAutorizzazioni(Integer codiceAutorizzazione);

    /**
     * <pre>
     * Verifica se esiste un record su spuntisti_mercato per i parametri passato
     * &#64;param codiceIstanza
     * &#64;param idautorizzazione
     * &#64;param codiceMercato
     * &#64;param codiceUso
     * &#64;param isAttivo
     * 		true	: attivo flgAttivo=true
     *  	false	: non attivo flgAttivo=false
     *          null 	: tutti
     * &#64;return
     * </pre>
     */
    public boolean existRecordPerIstanzaAutMercatoAndUso(Integer codiceIstanza, Integer idautorizzazione, Integer codiceMercato, Integer codiceUso,
	    Boolean isAttivo);

    public boolean existRecordPerIstanzaAutMercatoAndUsoAttivo(Integer codiceIstanza, Integer idautorizzazione, Integer codiceMercato,
	    Integer codiceUso);

    public SpuntistiMercati findAutMercatoAndUso(Integer idautorizzazione, Integer codiceMercato, Integer codiceUso);

    public List<SpuntistiMercatiHelper> findMercatiRichiestaSpuntaPerIstanza(Integer codiceIstanza, Integer codiceAurorizzazione);

    public void insertSpuntistiMercati(Integer codiceIstanza, Integer idautorizzazione);

    public List<SpuntistiMercati> findByMercatoEdUso(Integer codiceMercato, Integer codiceUso, Boolean isAttivi, Boolean isDataValidita);

    public Boolean exsitByMercato(Integer codiceMercato);

    public boolean existRecordPerAutMercatoAndUsoAttivo(Integer idAutorizzazione, Integer codiceMercato, Integer codiceUso);

    public List<SpuntistiMercati> findByIstanza(Integer codiceIstanza, Boolean isAttivi);

    /**
     * L'oggetto non contiene il valore <b>dataregistrazionepresenza</b> che indica l'ultima data per cui è stata
     * registrata una presenza non è possibile tirarla fuori da questa query in quanto abbiamo utilizzato una group by
     * (mettendo anche la mercatipresenze_t.dataregistrazione perdiamo il raggruppamento)
     * 
     * @param codiceMercato
     * @param codiceUso
     * @param giorniDiAssenza
     * @return
     */
    public List<SpuntistiMercatiDTO> findSpuntistaAssenteDa(Integer codiceMercato, Integer codiceUso, Integer giorniDiAssenza);

    /**
     * L'oggetto popola anche il valore <b>dataregistrazionepresenza</b> che indica l'ultima data per cui è stata
     * registrata una presenza( mercatipresenze_t.dataregistrazione) Il valore viene recuperato da una seconda query che
     * per ogni record di SpuntistiMercatiDTO recupera questo valore
     */
    public List<SpuntistiMercatiDTO> findSpuntistaAssenteDaWithDataUltimaPresenza(Integer codiceMercato, Integer codiceUso, Integer giorniDiAssenza);

    /**
     * Il metodo disabilita il record presnete su spuntisti mercati per uno spuntista che ha effettuato un determinato
     * numero di giorni di assegna ( giorni di assenza massimi specificati in mercatiConfigurazione)
     * 
     * @param codiceMercato
     * @param codiceuso
     * @param ggAssenzaPermssi
     */
    public void updateDisabilitaPerAssenza(Integer codiceMercato, Integer codiceuso, Integer ggAssenzaPermssi);

    public void disabilitaPerAssenza(Integer codiceMercato, Integer codiceuso, Integer ggAssenzaPermssi);

    /**
     * Disabilita tutti i record attivi sulla tabella spuntisti mercati per il mercato e giorno passati
     * 
     * @param codiceMercato
     * @param codiceuso
     */
    public void updatedisabilitaSpuntistaFieraPerTermine(Integer codiceMercato, Integer codiceuso);
}
