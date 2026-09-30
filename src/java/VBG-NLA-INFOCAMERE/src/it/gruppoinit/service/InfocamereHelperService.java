package it.gruppoinit.service;

import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.gruppoinit.constants.TipocooperazioneEnum;

import java.io.File;
import java.util.List;

public interface InfocamereHelperService {

    public RiepilogoPraticaSUAP getRiepilogoPraticaSUAP(File allegatoSuap, String nomefile);

    public TipocooperazioneEnum decodeTipoCooperazione(String tipocooperazione);

    public TipocooperazioneEnum decodeTipoCooperazioneBO(List<it.init.sigepro.rte.types.ParametroType> parametroType);

    public String recuperaTipoOperazioneDaAltriDatiNotifica(List<it.init.sigepro.rte.types.ParametroType> listparametroType);

    public String recuperaDescrizioneTipoOperazioneDaAltriDatiNotifica(List<it.init.sigepro.rte.types.ParametroType> listparametroType);
}
