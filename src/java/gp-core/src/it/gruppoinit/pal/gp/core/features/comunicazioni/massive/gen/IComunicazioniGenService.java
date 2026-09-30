package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ListaComunicazioniResoconti;

public interface IComunicazioniGenService extends IComunicazioniMassiveService<ConfigurazioniComunicazioneGen> {
 public List<ListaComunicazioniResoconti> creaListaTestataGen(String sql, Object[] params, String fkscalar);
}
