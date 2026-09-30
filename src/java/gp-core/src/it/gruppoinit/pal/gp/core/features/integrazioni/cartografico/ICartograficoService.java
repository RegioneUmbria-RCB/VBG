package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.attivita.datilocalizzativi.LocalizzazioniAttivitaDTO;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.GetInfoResponse;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.InnescoResponse;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client.ParametriResponse;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeStradarioExtendedDTO;

public interface ICartograficoService {

    InnescoResponse getUrlInnescoAttivita(UtilizzoEnum utilizzo, List<LocalizzazioniAttivitaDTO> localizzazioni, String returnTo);

    InnescoResponse getUrlInnescoAutorizzazioni(UtilizzoEnum utilizzo, List<IstanzeStradarioExtendedDTO> localizzazioni, String returnTo);

    InnescoResponse getUrlInnescoIstanza(Integer codiceIstanza, UtilizzoEnum utilizzo, String uuidLocalizzazione, String returnTo);

    InnescoResponse getUrlInnescoIstanze(UtilizzoEnum utilizzo, List<IstanzeStradarioExtendedDTO> localizzazioni, String returnTo);

    ParametriResponse getParametri(String uuIdLocalizzazione);

    GetInfoResponse getInfoConnettore();

    void salvaParametri(Integer codiceIstanza, String uuidLocalizzazione, ParametriResponse response);

    void aggiornaLocalizzazione(Integer codiceIstanza, String uuidLocalizzazione, ParametriResponse response);
}
