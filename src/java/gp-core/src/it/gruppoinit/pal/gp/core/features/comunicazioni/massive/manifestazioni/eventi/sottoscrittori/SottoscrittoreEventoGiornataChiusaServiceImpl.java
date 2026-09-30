package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.IComunicazioniManifestazioniService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoGiornataChiusa;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.StrategiaInvioComunicazioniEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.IVerticalizzazioneAbbonamentoPosteggiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.VerticalizzazioneAbbonamentoPosteggiServiceImpl;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;

@Service
public class SottoscrittoreEventoGiornataChiusaServiceImpl implements IEventSubscriber<EventoGiornataChiusa> {

    private VerticalizzazioniService verticalizzazioniService;
    private ContiService contiService;
    private MailtipoService mailTipoService;
    private AmministrazioniService amministrazioniService;
    private MercatipresenzeTService mercatipresenzeTService;
    private IComunicazioniManifestazioniService comunicazioniService;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setContiService(ContiService contiService) {

	this.contiService = contiService;
    }

    @Autowired
    public void setMercatipresenzeTService(MercatipresenzeTService mercatipresenzeTService) {

	this.mercatipresenzeTService = mercatipresenzeTService;
    }

    @Autowired
    public void setComunicazioniService(IComunicazioniManifestazioniService comunicazioniService) {

	this.comunicazioniService = comunicazioniService;
    }

    @Autowired
    public void setMailTipoService(MailtipoService mailTipoService) {

	this.mailTipoService = mailTipoService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Override
    public void onEvent(EventoGiornataChiusa e) {

	MercatipresenzeT giornata = this.mercatipresenzeTService.findById(new PkId(e.getIdGiornata()));
	//1. Verifico se vanno inviate comunicazioni
	IVerticalizzazioneAbbonamentoPosteggiService vert = new VerticalizzazioneAbbonamentoPosteggiServiceImpl(this.verticalizzazioniService,
		this.contiService, this.mailTipoService, this.amministrazioniService, giornata.getMercato().getComune().getCodicecomune());
	if (!vert.isAttiva() || !vert.strategiaInvioComunicazioniSupportata(StrategiaInvioComunicazioniEnum.CHIUSURA_GIORNATA)) {
	    return;
	}
	//2. Verifico se presente una comunicazione per quella giornata
	//   potrebbe essere stata chiusa, inviata, riaperta e richiusa
	if (comunicazioniService.presentiComunicazioniPerTipologiaEGiornata(giornata.getId().getCodice(),
		ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE.CHIUSURA_GIORNATA)) {
	    return;
	}
	//3. Creo la comunicazione
	ConfigurazioneComunicazioniManifestazioni config = ConfigurazioneComunicazioniManifestazioni.fromGiornataManifestazione(vert, giornata);
	this.comunicazioniService.creaNuovaComunicazione(config);
    }
}
