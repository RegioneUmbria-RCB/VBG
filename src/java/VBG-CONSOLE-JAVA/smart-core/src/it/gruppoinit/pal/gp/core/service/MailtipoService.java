package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MailtipoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ContestiMailTipoEnum;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.init.sigepro.rte.types.DettaglioPraticaType;

import java.util.List;

public interface MailtipoService extends BaseService<Mailtipo, PkId> {

    /**
     * @see MailtipoDAO#findAll(Integer, Integer)
     */
    public List<Mailtipo> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see MailtipoDAO#findByFilter(Mailtipo filter)
     */
    public List<Mailtipo> findByFilter(Mailtipo filter);

    /**
     * Metodo che restituisce la lista di mailtipo filtrate per il software TT e per il software corrente.
     * 
     * @return
     */
    public List<Mailtipo> findAllBySoftwareAndTT(ContestiMailTipoEnum contestiMailTipoEnum);

    /**
     * Torna "Protocollo dell'istanza numero "
     * 
     * @return
     */
    public String getOggettoProtocollazioneDefault();

    /**
     * Torna "Fascicolo dell'istanza numero "
     * 
     * @return
     */
    public String getOggettoFascicolazioneDefault();

    public Mailtipo eseguiSostituzioniFrontend(int codicemailtipo, DettaglioPraticaType dettaglioPratica);
}
