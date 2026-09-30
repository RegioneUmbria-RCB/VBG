package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AnagrafestoricoDAO;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Date;
import java.util.List;

public interface AnagrafestoricoService extends BaseService<Anagrafestorico, PkId> {

    /**
     * @see AnagrafestoricoDAO#findStoricoId(Anagrafe entity, Date data)
     */
    public Anagrafestorico findStoricoId(Anagrafe entity, Date data);

    /**
     * @see AnagrafestoricoDAO#findUltimoAnagrafestoricoByAnagrafe(Anagrafe entity)
     */
    public Anagrafestorico findUltimoAnagrafestoricoByAnagrafe(Anagrafe entity);

    /**
     * @see AnagrafestoricoDAO# findStoricoByAnagrafe(Anagrafe findStoricoByAnagrafe(Anagrafe entity));
     */
    public List<Anagrafestorico> findStoricoByAnagrafe(Anagrafe entity);

    /**
     * @see AnagrafestoricoDAO#ricalcoloStoricoAnagrafiche(Anagrafestorico anagrafeStorico)
     */
    public void ricalcoloStoricoAnagrafiche(Anagrafestorico anagrafeStorico);
}
