package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.responsabili.eventi.EventoEmailResponsabiliAggiornata;

@Service
public class SottoscrittoreEventoEmailResponsabileAggComServiceImpl implements IEventSubscriber<EventoEmailResponsabiliAggiornata> {

    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;

    @Autowired
    public SottoscrittoreEventoEmailResponsabileAggComServiceImpl(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO) {

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
    }

    @Override
    public void onEvent(EventoEmailResponsabiliAggiornata e) {

	this.comunicazioniMassiveDettaglioDAO.aggiornaMailResponsabili(e.getCodiceResponsabile());
    }
}
