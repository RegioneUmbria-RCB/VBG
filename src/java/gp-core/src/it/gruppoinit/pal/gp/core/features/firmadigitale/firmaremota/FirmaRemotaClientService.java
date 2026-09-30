package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.RecuperaParametriRequest;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.RecuperaParametriResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.AvviaProcessoResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.ConfigurazioneParametro;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.RecuperaFileFirmatoResponse;
import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model.VerificaStatoResponse;

public interface FirmaRemotaClientService {

    RecuperaParametriResponse recuperaParametri(RecuperaParametriRequest request);

    AvviaProcessoResponse avviaProcesso(String endPoint, List<ConfigurazioneParametro> parametri);

    void aggiungiDocumenti(String endpoint, String sessionId, List<Integer> codiciOgggetto);

    void firmaDocumenti(String endpoint, String sessionId, List<ConfigurazioneParametro> parametri, List<Integer> codiciOgggetto);

    VerificaStatoResponse verificaStato(String endpoint, String sessionId);

    RecuperaFileFirmatoResponse recuperaFileFirmato(String endpoint, String sessionId, Integer codiceOggetto);
}