package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazione;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.PercorsoAlberoprocHelper;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.ProtocolloConfigurazioneService;

public class OggettoECorpoMailResolver {

    private AlberoprocService alberoprocService;
    private AlberoprocProtocolloService alberoprocProtocolloService;
    private AmbitoProtocollazioneEnum ambitoProtocollazione;
    private ProtocolloConfigurazioneService protConfigService;
    private MailtipoService mailtipoService;
    private boolean isFascicolazione;
    private Istanze istanza;
    private Movimenti movimento;

    public OggettoECorpoMailResolver(AlberoprocService alberoprocService, AlberoprocProtocolloService alberoprocProtocolloService,
	    ProtocolloConfigurazioneService protConfigService, MailtipoService mailtipoService, AmbitoProtocollazioneEnum ambitoProtocollazione,
	    boolean isFascicolazione, Istanze istanza, Movimenti movimento) {

	this.alberoprocService = alberoprocService;
	this.alberoprocProtocolloService = alberoprocProtocolloService;
	this.protConfigService = protConfigService;
	this.mailtipoService = mailtipoService;
	this.ambitoProtocollazione = ambitoProtocollazione;
	this.isFascicolazione = isFascicolazione;
	this.istanza = istanza;
	this.movimento = movimento;
    }

    public OggettoECorpoMailProtocollo resolve() {

	String oggettoDefault = null;
	Mailtipo mailTipo = null;
	if (AmbitoProtocollazioneEnum.DA_ISTANZA.equals(ambitoProtocollazione)) {
	    Alberoproc ap = this.alberoprocService.findById(new PkId(istanza.getAlberoproc().getId().getCodice()));
	    AlberoprocHelper helper = this.alberoprocService.findAlberoprocHelper(ap);
	    List<PercorsoAlberoprocHelper> percorsoAlberoprocHelpers = helper.getPercorsoAlberoprocHelpers();
	    if (isFascicolazione) {
		for (PercorsoAlberoprocHelper pah : percorsoAlberoprocHelpers) {
		    AlberoprocProtocollo app = alberoprocProtocolloService.findByAlberoprocIdAndComune(pah.getId(),
			    istanza.getComune().getCodicecomune());
		    if (app != null && app.getTestoFascicolo() != null && app.getTestoFascicolo().getId() != null
			    && app.getTestoFascicolo().getId().getCodice() != null) {
			mailTipo = this.mailtipoService.replaceOggettoCorpo(app.getTestoFascicolo(), istanza, null);
			break;
		    }
		}
		oggettoDefault = "Fascicolo dell'istanza n. " + istanza.getNumeroistanza();
	    } else {
		for (PercorsoAlberoprocHelper pah : percorsoAlberoprocHelpers) {
		    AlberoprocProtocollo app = alberoprocProtocolloService.findByAlberoprocIdAndComune(pah.getId(),
			    istanza.getComune().getCodicecomune());
		    if (app != null && app.getTestoProtocollo() != null && app.getTestoProtocollo().getId() != null
			    && app.getTestoProtocollo().getId().getCodice() != null) {
			mailTipo = this.mailtipoService.replaceOggettoCorpo(app.getTestoProtocollo(), istanza, null);
			break;
		    }
		}
		oggettoDefault = "Protocollo dell'istanza n. " + istanza.getNumeroistanza();
	    }
	} else if (AmbitoProtocollazioneEnum.DA_MOVIMENTO.equals(ambitoProtocollazione)) {
	    //verifica se il tipo movimento ha impostata la mail per il protocollo
	    //verifica su PROTOCOLLO_CONFIGURAZIONE ( idcomune, software ) se valorizzato CODTESTOMOVIMENTI 
	    //ALTRIMENTI costante Protocollo dell'istanza n.  concatenato numero istanza
	    if (this.movimento.getTipomovimento().getMailtipoOggProt() != null
		    && this.movimento.getTipomovimento().getMailtipoOggProt().getId() != null
		    && this.movimento.getTipomovimento().getMailtipoOggProt().getId().getCodice() != null) {
		mailTipo = this.mailtipoService.replaceOggettoCorpo(this.movimento.getTipomovimento().getMailtipoOggProt(), null, movimento);
	    } else {
		ProtocolloConfigurazione protConfig = this.protConfigService.findById(
			new ProtocolloConfigurazioneId(this.movimento.getId().getIdcomune(), this.movimento.getIstanza().getSoftware().getCodice()));
		if (protConfig != null && protConfig.getMailtipoByFkMovimento() != null && protConfig.getMailtipoByFkMovimento().getId() != null
			&& protConfig.getMailtipoByFkMovimento().getId().getCodice() != null) {
		    mailTipo = this.mailtipoService.replaceOggettoCorpo(protConfig.getMailtipoByFkMovimento(), null, movimento);
		}
	    }
	    oggettoDefault = "Protocollo dell'istanza n. " + movimento.getIstanza().getNumeroistanza();
	}
	return new OggettoECorpoMailProtocollo(mailTipo, oggettoDefault);
    }
}
