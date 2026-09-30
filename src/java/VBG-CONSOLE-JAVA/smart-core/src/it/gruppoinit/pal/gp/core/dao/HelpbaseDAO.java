package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Helpbase;
import it.gruppoinit.pal.gp.core.domain.HelpbaseId;

public interface HelpbaseDAO extends BaseDAO<Helpbase, HelpbaseId> {

    /**
     * <pre>
     * recupera l'help di base secondo la logica: 
     * 
     * 1- param:software =  null : prima controlla se è presente per il software corrente e poi per TT
     * 2- param:software != null : controlla se è presente solo per il software passato
     * 
     * @param contentType
     *            la pagina per la quale visualizzare l'help
     * @param software se diverso da null specifica il software per cui cercare l'help base            
     * @return
     * 
     * </pre>
     */
    public Helpbase findByContentAndSoftwares(String contentType, String software);
}
