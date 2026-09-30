package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Quesiti;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.web.SessionDetails;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author Luca Proietti
 */
public interface QuesitiService extends BaseService<Quesiti, PkId> {

    /**
     * @see QuesitiDAO#findByFilter(Set<Software> softwareList)
     */
    public List<Quesiti> findByFilter(Set<Software> softwareList);

    /**
     * Esegue l'aggiornamento dei dati ed invia l'email a chi ha creato il Quesito nel frontoffice nel caso che il campo
     * email sia stato compilato.
     * 
     * @param entity
     * @param sessionDetails
     */
    public void update(Quesiti entity, SessionDetails sessionDetails);

    /**
     * Esegue l'aggiornamento dei dati e crea una Faq
     * 
     * @param entity
     */
    public void insertFaq(Quesiti entity);
}
