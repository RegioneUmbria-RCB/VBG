package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.configurazione;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.FirmeRemote;
import it.gruppoinit.pal.gp.core.domain.FirmeRemoteParametri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.FirmaRemotaListModel;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.FirmaRemotaModel;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.ProviderFirmaModel;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface FirmeRemoteService extends BaseService<FirmeRemote, PkId> {

    List<FirmaRemotaListModel> listaFirmeIntegrate();

    int insert(FirmaRemotaModel firma);

    List<FirmeRemoteParametri> findParamertiByIdTestata(Integer codice);

    FirmaRemotaModel getFirmaRemota(Integer codice);

    void updateFirma(FirmaRemotaModel firma);

    void delete(Integer codice);

    List<ProviderFirmaModel> getFirmeIntegrate();

    List<ChiaveValoreBean<Integer, String>> getFirmeAttive();

    void insertParametro(FirmeRemoteParametri parametro);

    boolean almenoUnaFirmaRemotaAttiva();
}
