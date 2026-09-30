/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Bandi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeFiere;

import java.util.List;

/**
 * @author fabrizioc
 * 
 */
public interface BandiDAO extends BaseDAO<Bandi, PkId> {

    /**
     * recupera tutti i bandi dell'intervento specificato.<br />
     * Se l'intervento ha mercato e uso collegati allora la lista conterrà al massimo un solo bando
     * 
     * @param alberoproc
     * @return
     */
    public List<Bandi> findByAlberoproc(Alberoproc alberoproc);

    /**
     * recupera tutte le istanze delle graduatorie del bando legato all'alberoproc e anagrafe(richiedente o titolare
     * legale)
     * 
     * @param alberoproc
     * @param anagrafe
     * @return
     */
    public List<AnagrafeFiere> findIstanzeAnagrafeGraduatoria(Alberoproc alberoproc, Anagrafe anagrafe);
}
