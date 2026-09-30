package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.RegistrazioniCausaliDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;

import java.util.List;

public interface RegistrazioniCausaliService extends BaseService<RegistrazioniCausali, PkId> {

    /**
     * @see RegistrazioniCausaliDAO#findByDescrizione(String)
     * @param descrizione
     * @return
     */
    public List<RegistrazioniCausali> findByDescrizione(String descrizione);

    /**
     * @see RegistrazioniCausaliDAO#findByAbilitato(String)
     * @return
     */
    public List<RegistrazioniCausali> findByAbilitato();

    /**
     * @see RegistrazioniCausaliDAO#findByDescrizioneMercati(String)
     * @param descrizione
     * @return
     */
    public List<RegistrazioniCausali> findByDescrizioneMercati(String descrizione);

    /**
     * @see RegistrazioniCausaliDAO#findByDescrizioneEscluseRiduzioni(String)
     * @param descrizione
     * @return
     */
    public List<RegistrazioniCausali> findByDescrizioneEscluseRiduzioni(String descrizione);

    /**
     * @see RegistrazioniCausaliDAO#findByDescrizioneSoloRiduzioni
     * @param descrizione
     * @return
     */
    public List<RegistrazioniCausali> findByDescrizioneSoloRiduzioni(String descrizione);
}
