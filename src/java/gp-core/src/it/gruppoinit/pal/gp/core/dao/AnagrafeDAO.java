package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeRicercaBean;
import it.gruppoinit.pal.gp.core.dao.helper.RicercaAnagraficeCollegateEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;
import java.util.Set;

public interface AnagrafeDAO extends BaseDAO<Anagrafe, PkId> {

    /**
     * ricerca tutti i record in ANAGRAFE con una LIKE della stringa passata nel campo NOMINATIVO. se la stringa
     * contiene degli spazi allora utilizza la sottostringa dopo lo spazio per eseguire una LIKE nel campo NOME.
     * 
     * <pre>
     * se AnagrafeEnum.ALL allora torna tutti i record
     * se AnagrafeEnum.ACTIVE allora torna tutti i record dell'anagrafica che risultano attivi
     * se AnagrafeEnum.DISABLED allora torna tutti i record dell'anagrafica che risultano disabilitati
     * </pre>
     * 
     * @param nominativo
     * @param anagrafeEnum
     * @return
     */
    public List<Anagrafe> findByNominativo(String nominativo, AnagrafeEnum anagrafeEnum);

    /**
     * Restituisce le anagrafiche (filtrando per idcomune) ordinandole per il campo nominativo
     */
    public List<Anagrafe> findAll(Integer firstResult, Integer maxResult);

    public List<Anagrafe> findByDescrizione(String descrizione, String tipoAnagrafe, AnagrafeEnum statoAnagrafe, Integer firstResult,
	    Integer maxResult, boolean soloSeAutorizzazioniPresenti);

    public List<AnagrafeRicercaBean> findRichiedentiByIstanza(String descrizione, Integer codiceIstanza, String tipoAnagrafe);

    public List<Integer> findCodiciAnagrafe();

    /**
     * <pre>
     * Il metodo abilita o disabilità un' anagrafica.
     * <ol>
     * <li>Anagrafica abilitata, viene disabilitata</li>
     * <li>Anagrafica disabilitata, viene abilitata</li>
     * </ol>
     * @param codiceanagrafe
     * @param stato true:disabilitato, false: abilitato
     * </pre>
     */
    public void updateAbiltaOrDisabilita(Integer codiceanagrafe, Boolean stato);

    /**
     * Il metodo ritorna una serie di codiciFiscali partita iva delle anagrafiche collegate ad una anagrafica passata
     * come riferimento ricercando sui campi ISTANZE.CODICERICHIEDENTE, ISTANZE.CODICETITOLARELEGALE del modulo software
     * attivo
     * 
     * @param codiceAnagrafe
     * @param filtroAutorizzazioni
     * @return
     */
    public Set<String> findCfPivaAnagraficheCollegateDelleIstanze(Integer codiceAnagrafe, RicercaAnagraficeCollegateEnum filtroAutorizzazioni);
}
