package it.gruppoinit.pal.gp.core.features.oneri.regulus;

import java.math.BigInteger;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.IstanzeOneriRegulus;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.regulus.schema.billpaymentnotify.response.MSG.ESITO;

/**
 * @author francescop
 * 
 */
public interface IstanzeOneriRegulusService extends BaseService<IstanzeOneriRegulus, PkId> {

    /**
     * Metodo per l'inserimento delle Istanze oneri regulus.Vengono inserite una volta che il pagamento è stato
     * effettuato. Viene aggiornato il campo datapagamento in ISTANZEONERI
     * 
     * @param list
     * @param importobollo
     * 
     * @return Esito esito del bollo.se è diverso da null allora l'importo del bollo pagato da regulus è diverso da
     *         quello presente in istanzeoneri
     */
    public ESITO insertOneriRegulus(List<IstanzeOneriRegulus> list, BigInteger importobollo);

    public void deleteByIdOnere(int istanzeOneriId);

    public List<IstanzeOneriRegulus> findByIdIstanzeOneri(Integer idIstanzeOneri);
}
