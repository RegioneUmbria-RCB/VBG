/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AmministrazioniHelper;

import java.util.List;

/**
 * @author francescop
 * @author gianpaolot
 */
public interface AmministrazioniDAO extends BaseDAO<Amministrazioni, PkId> {

    public List<Amministrazioni> findByAmministrazione(String amministrazione, boolean tutteLeAmministrazioni, boolean includiDisabilitate,
	    Integer[] codiciAmministrazioniEscluse);

    // /**
    // * Metodo per ricercare tutte le amministrazione tranne quelle che hanno codice diverso dal valore memorizzato
    // nelle
    // * colonne (CODICELASTESSAAMMINISTRAZIONE , CODICETUTTEAMMINISTRAZIONI) della tabella CONFIGURAZIONE
    // *
    // * @return
    // */
    // public List<Amministrazioni> findByAmministrazioneFilter();
    //
    // public List<Amministrazioni> findAmministrazioniTipimov();
    /**
     * Metodo che ricerca le amministrazioni per descrizione
     */
    public List<Amministrazioni> findAmministrazioniByDescrizione(String amministrazione);

    /**
     * Il metodo deve restituire una lista di amministrazioni filtrate per descrizione; tra tutte le amministrazioni
     * saranno prese in considerazione solo quelle che hanno il campo unità organizzativa (prot_ua) o ruolo (prot_ruolo)
     * diverso da null
     * 
     * @param amministrazione
     * @param software
     * @param codiceComune
     * @return lista di amministrazioni
     */
    public List<Amministrazioni> findAmministrazioniByDescrizioneForProtocolloRegistri(String amministrazione, boolean includiDisabilitate,
	    String codiceComune, String software);

    public List<Amministrazioni> findByAmministrazioniInterne();

    /**
     * Il metodo controlla se per il comune in esame esiste almeno un'amministrazione interna configuarta,appena ne
     * trova una esce dalla ricerca e ritorna true altrimenti ritorna false
     * 
     * @return
     */
    public boolean isAmministrazioneInternaEsiste();

    /**
     * Il metedo ritorna una lista di amministrazioniHelper filtrate per idcomune e ordinate per descrizione (l'oggetto
     * amministrazion Helper contiente oltre all'oggetto Amministrazione altri parametri di aiuto per la
     * visulaizzazionedelle informazioni )
     * 
     * @param min
     * @param max
     * @return
     */
    public List<AmministrazioniHelper> findAllDTO(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
	    DAOOrderTypeEnum orderType);

    public Amministrazioni findAmministrazioniByCodiceancitel(String codiceancitel);

    public List<Amministrazioni> findAmministrazioniWithEmailByDescrizione(String amministrazione);
}
