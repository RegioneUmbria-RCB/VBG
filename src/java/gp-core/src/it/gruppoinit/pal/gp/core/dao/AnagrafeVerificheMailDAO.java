package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AnagrafeVerificheMail;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.anagrafe.model.CodiceVerificaMailAnagrafeBean;

public interface AnagrafeVerificheMailDAO extends BaseDAO<AnagrafeVerificheMail, PkId> {

    void deleteByAnagrafe(Integer codiceAnagrafe);

    boolean isProceduraVerificaInCorso(Integer codiceAnagrafe);

    void eliminaVerificheInCorso(Integer codiceAnagrafe);

    void generaCodiceVerificaMail(CodiceVerificaMailAnagrafeBean codiceVerificaMailAnagrafeBean);

    String updateVerificaCambioMail(Integer codiceAnagrafe, String codiceVerifica);

    /**
     * Recupera l'ultimo codice verifica generato ovvero quello con data scadenza maggiore non verificato
     * 
     * @param codice
     * @return
     */
    String recuperaUltimoCodiceVerificaPerAnagrafe(Integer codice);
}
