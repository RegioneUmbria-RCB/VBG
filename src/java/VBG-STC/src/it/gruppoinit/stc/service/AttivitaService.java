package it.gruppoinit.stc.service;

import it.gruppoinit.stc.dao.impl.PraticheHelper;
import it.gruppoinit.stc.domain.Attivita;
import it.init.sigepro.rte.types.SportelloType;

public interface AttivitaService extends BaseService<Attivita, Integer> {

    public Attivita findByUniqueKey(Attivita example);

    public String findIdProcedimentoPrecedentiComunicazioni(Integer idPraticaMittente, Integer idPraticaDestinataria);

    public Attivita findBySportelloTypeAndIdAttivita(SportelloType sportello, String idAttivita);

    public SportelloType findSportelloDestinatarioByIdAttivitaMittente(Integer idAttivitaMittente);

    public PraticheHelper findPraticaMittenteByIdAttivitaDestinataria(Integer idAttivitaDestinataria);
}
