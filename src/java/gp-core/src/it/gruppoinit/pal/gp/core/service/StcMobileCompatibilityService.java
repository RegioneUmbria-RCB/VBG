package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.AutorizzazioniConcessioniIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.EndoIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.EsitoChiamataLista;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.IstanzaLista;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.LocalizzazioneIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.MovimentoIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.NomeValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.OnereIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.SoggettoCollegatoIstanza;

public interface StcMobileCompatibilityService {

    EsitoChiamataLista<IstanzaLista> findListaIstanzeByParams(String cfUtente, String civico, String indirizzo, String numeroIstanza,
	    String numeroProtocollo, String stato, String dalladata, String alladata, String comune, String nominativoAnagrafe,
	    Integer annoProtocollo, Integer codiceStradario, String tipoCatasto, String foglio, String particella, String sub, Integer firstResult,
	    Integer maxResults);

    List<NomeValoreBean> getDatiGeneraliIstanzaByUid(String id);

    List<LocalizzazioneIstanza> getLocalizzazioniPraticabyUid(String id);

    List<EndoIstanza> getEndoPraticaByUid(String id);

    List<OnereIstanza> getOneriPraticaByUid(String id);

    List<MovimentoIstanza> getMovimentiPraticaByUid(String id);

    List<AutorizzazioniConcessioniIstanza> getAutorizzazioniConcessioniPraticaByUid(String id);

    List<SoggettoCollegatoIstanza> getSoggettiCollegatiPraticaByUId(String id);
}
