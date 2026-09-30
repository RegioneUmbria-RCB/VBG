package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;

import java.util.List;

public interface RegistrazioniCausaliDAO extends BaseDAO<RegistrazioniCausali, PkId> {

    /**
     * metodo che recupera tutte le RegistrazioniCausali che contengono nella descrizione la stringa passata come
     * argomento e con le proprietà abilitato=true
     * 
     * @param descrizione
     * @return
     */
    public List<RegistrazioniCausali> findByDescrizione(String descrizione);

    /**
     * metodo che recupera tutte le RegistrazioniCausali con proprietà abilitato=true
     * 
     * @return
     */
    public List<RegistrazioniCausali> findByAbilitato();

    /**
     * metodo che recupera tutte le RegistrazioniCausali che contengono nella descrizione la stringa passata come
     * argomento e con le proprietà abilitato=true, richedePosteggio=true
     */
    public List<RegistrazioniCausali> findByDescrizioneMercati(String descrizione);

    /**
     * metodo che recupera tutte le RegistrazioniCausali che contengono nella descrizione la stringa passata come
     * argomento e con proprietà abilitato=true, richedePosteggio=true, nonPrevedeIncassi=false,
     * soloImportiNegativi=false
     * 
     * @param descrizione
     * @return
     */
    public List<RegistrazioniCausali> findByDescrizioneEscluseRiduzioni(String descrizione);

    /**
     * metodo che recupera tutte le RegistrazioniCausali che contengono nella descrizione la stringa passata come
     * argomento e con proprietà abilitato=true, richedePosteggio=true, nonPrevedeIncassi=true, soloImportiNegativi=true
     * 
     * @param descrizione
     * @return
     */
    public List<RegistrazioniCausali> findByDescrizioneSoloRiduzioni(String descrizione);
}
