package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.anagrafica.eventi.EventoEmailAnagrafeAggiornata;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;

@Service
public class SottoscrittoreEventoEmailAnagrafeAggiornataServiceImpl implements IEventSubscriber<EventoEmailAnagrafeAggiornata> {

    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;

    @Autowired
    public SottoscrittoreEventoEmailAnagrafeAggiornataServiceImpl(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO) {

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
    }

    @Override
    public void onEvent(EventoEmailAnagrafeAggiornata e) {

	comunicazioniMassiveDettaglioDAO.aggiornaMailAnagrafe(e.getCodiceAnagrafe());
    }
}
