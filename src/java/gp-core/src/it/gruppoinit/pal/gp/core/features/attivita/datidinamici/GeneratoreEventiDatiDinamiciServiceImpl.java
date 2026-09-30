package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSchedaDinamicaAggiunta;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSchedaDinamicaEliminata;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSchedaDinamicaIstanzaEliminata;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSchedaDinamicaIstanzaSalvata;
import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoSchedaDinamicaSalvata;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;

@Service
public class GeneratoreEventiDatiDinamiciServiceImpl implements IGeneratoreEventiDatiDinamiciService {

    @Autowired
    private IEventPublisher publisher;

    @Override
    public void generaEventoSchedaAttivitaAggiunta(Integer idAttivita, Integer idSchedaDinamica) {

	this.publisher.publish(new EventoSchedaDinamicaAggiunta(idAttivita, idSchedaDinamica));
    }

    @Override
    public void generaEventoSchedaAttivitaSalvata(Integer idAttivita, Integer idSchedaDinamica) {

	this.publisher.publish(new EventoSchedaDinamicaSalvata(idAttivita, idSchedaDinamica));
    }

    @Override
    public void generaEventoSchedaDinamicaAttivitaEliminata(Integer idAttivita, Integer idSchedaDinamica, List<Integer> idCampiDinamiciDaEliminare) {

	this.publisher.publish(new EventoSchedaDinamicaEliminata(idAttivita, idSchedaDinamica, idCampiDinamiciDaEliminare));
    }

    @Override
    public void generaEventoSchedaDinamicaIstanzaSalvata(Integer idIstanza, Integer idSchedaDinamica) {

	this.publisher.publish(new EventoSchedaDinamicaIstanzaSalvata(idIstanza, idSchedaDinamica));
    }

    @Override
    public void generaEventoSchedaDinamicaIstanzaEliminata(Integer idIstanza, Integer idSchedaDinamica) {

	this.publisher.publish(new EventoSchedaDinamicaIstanzaEliminata(idIstanza, idSchedaDinamica));
    }
}
