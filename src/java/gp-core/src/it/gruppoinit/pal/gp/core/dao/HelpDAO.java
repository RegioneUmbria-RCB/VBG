package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Help;
import it.gruppoinit.pal.gp.core.domain.HelpId;

public interface HelpDAO extends BaseDAO<Help, HelpId> {

    /**
     * <pre>
     * recupera l'help  secondo la logica: 
     * 
     * 1- param:software =  null : prima controlla se è presente per il software corrente e poi per TT
     * 2- param:software != null : controlla se è presente solo per il software passato
     * 
     * @param contentType
     *            la pagina per la quale visualizzare l'help
     * @param software se diverso da null specifica il software per cui cercare l'help             
     * @return
     * 
     * </pre>
     */
    public Help findByContentAndSoftwares(String contentType, String software);
}
