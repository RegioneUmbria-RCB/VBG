package it.gruppoinit.pal.gp.core.features.commissioni.appello;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppelloPratiche;

@SuppressWarnings("rawtypes")
public interface ICommissioniAppelloDAO extends BaseDAO {

    List<CommedilizieAppelloPratiche> findCommEdilizieAppelloPraticheByAppello(Integer idAppello);

    List<CommedilizieAppelloPratiche> findCommEdilizieAppelloPraticheByAppelloAndRiga(Integer idAppello, Integer idRigaCommissioniEdilizieR);

    int countCommEdilizieAppelloPraticheByAppelloAndRiga(Integer idAppello, Integer idRigaCommissioniEdilizieR);

    void deleteAppelloPraticheByAppello(Integer idCommedilizieAppello);

    void deleteAppelloPraticheByIdRiga(Integer idRigaDettaglio);

    Integer soggettoPresenteInAppello(int codiceCommissione, int codiceAnagrafe);

    Integer convocaSoggetto(Integer idCommissione, Integer codiceAnagrafe, Integer codiceCarica);

    Integer collegaAppelloAPratica(Integer idAppello, Integer idRiga);

    Integer collegaCaricaByAppello(Integer codiceAnagrafe, int idCommissione);

    List<Integer> findSoggettiGiaPresenti(Integer idRiga);

    CommedilizieAppello findBySoggettoAndCommissione(Integer codAnagraf, Integer idCommissione);

    void deleteAppelloByIdAppello(Integer idAppello);

    void deleteAppelloPraticheByIdRigaAndAppello(Integer idRiga, Integer idAppello);

    boolean esisteVotazione(Integer idAppello);
}
