package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.amministrazioni.eventi.EventoEmailAmministrazioneAggiornata;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.responsabili.eventi.EventoEmailResponsabiliAggiornata;

@Service
public class SottoscrittoreEventoEmailAmministrazioniAggiornataComServiceImpl implements IEventSubscriber<EventoEmailAmministrazioneAggiornata> {

    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;

    @Autowired
    public SottoscrittoreEventoEmailAmministrazioniAggiornataComServiceImpl(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO) {

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
    }

    @Override
    public void onEvent(EventoEmailAmministrazioneAggiornata e) {

	this.comunicazioniMassiveDettaglioDAO.aggiornaMailAmministrazioni(e.getCodiceAmministrazioni());
    }
}
