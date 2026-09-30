package it.gruppoinit.pal.gp.pay.connector.mip.genova;

import java.util.Map;

import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.service.helper.EsitoElaborazione;

public interface ICaricamentoMassivoPosizioniNEXIGenovaService {

    EsitoElaborazione elaboraCaricamentoMassivoPosizioni(Map<String, String> params, IPayConnector connector);
}
