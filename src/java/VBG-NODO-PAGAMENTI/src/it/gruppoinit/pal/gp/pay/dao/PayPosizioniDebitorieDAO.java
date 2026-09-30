/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.utils.PosizioneDebitoriaFiltrata;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.ws.rest.NuoviPagamentiRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PagamentiAnnullatiRestRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PagamentiEffettuatiRestRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PosizioneDebitoriaInfoRestResponse;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaRequestType;

/**
 * @author francol
 *
 */
public interface PayPosizioniDebitorieDAO extends BaseDAO<PayPosizioniDebitorie, PkId> {

    @Override
    void insert(PayPosizioniDebitorie entity);

    public PayPosizioniDebitorie findByRiferimenti(String iuv, Integer id);

    public PayPosizioniDebitorie findByIdPosizionePSP(String idPSP);

    public List<PayPosizioniDebitorie> findAllByIdPosizionePSP(String idPSP);

    /**
     * Ricerca per codice avviso pagamento
     */
    public PayPosizioniDebitorie findByCodiceAvviso(String codiceAvviso);

    public List<Integer> findByJsonRequestFilter(PosizioneDebitoriaRequestType richiesta, Integer offset, Integer limit);

    public int countByJsonRequestFilter(PosizioneDebitoriaRequestType richiesta);

    public List<PosizioneDebitoriaFiltrata> findByIdPosizioni(List<Integer> idPosizioni);

    List<PosizioneDebitoriaInfoRestResponse> findNuoviPagamentiDeiConnettori(NuoviPagamentiRequest richiesta);

    List<PosizioneDebitoriaInfoRestResponse> findPagamentiAnnullatiDeiConnettori(PagamentiAnnullatiRestRequest richiesta);

    List<PosizioneDebitoriaInfoRestResponse> findPagamentiEffettuatiDeiConnettori(PagamentiEffettuatiRestRequest richiesta);

    PayPosizioniDebitorie findByIdPosizionePSPOrIUVOrCodiceAvviso(String idPSP, String iuv, String codiceAvviso);

    Set<String> findRiferimentiClientByPosizioneDebitoria(Integer idPosizioneDebitoria);
}
