/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatidLetture;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Date;
import java.util.List;

public interface MercatidLettureDAO extends BaseDAO<MercatidLetture, PkId> {

    /**
     * Metodo di ricerca
     * 
     * @param entity
     * @return
     */
    public List<MercatidLetture> findByFilter(MercatidLetture entity);

    /**
     * Metodo per ricercare le date lettura in mercatidLettura. Le date sono raggruppate.
     * 
     * @param mercatiUso
     *            deve essere settato il mercato.
     * @return
     */
    public List<Date> findDataLettura(MercatiUso mercatiUso);

    /**
     * Metodo per determinare le letture che hanno l'importo maggiore di zero
     * 
     * @return
     */
    public List<MercatidLetture> findByLettureConImporto(MercatidLetture mercatidLetture);

    /**
     * Metodo per determinare l'ultima lettura finale per un determinato posteggio
     * 
     * @return
     */
    public MercatidLetture findUltimaLetturaFinaleByPosteggio(MercatiD mercatiD);
}
