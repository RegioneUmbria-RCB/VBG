/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Oggettiinfo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author lucap
 * 
 */
public interface OggettiinfoService extends BaseService<Oggettiinfo, PkId> {

    public List<Oggettiinfo> findByDescrizioneAndTipologia(String descrizione, Integer tipologia);

    /**
     * Metodo per l'inserimento di un Oggettoinfo. Viene settato come id dell'oggettoinfo il codiceoggetto dell'oggetto
     * collegato.
     * 
     * @param oggettiinfo
     */
    public void insertOggettiLibreria(Oggettiinfo oggettiinfo, byte[] filecontent, String fileName);

    /**
     * Metodo per l'aggiornamento di un Oggettoinfo. Viene aggiornato l'id dell'oggettoinfo su tutte le tabelle che sono
     * collegate allo stesso oggetto.
     * 
     * @param oggettiinfo
     */
    public void updateOggettiLibreria(Oggettiinfo oggettiinfo, byte[] filecontent, String fileName);
}
