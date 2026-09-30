package it.gruppoinit.pal.gp.core.features.protocollazione.logic.destinatari;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.PercorsoAlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.domain.web.ProtocolloSoggettoCommand;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtMittDestAutoResolverNotAutomatica;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.TipisoggettopeopleService;

public class DestinatariResolver {

    private AlberoprocService alberoprocService;
    private AlberoprocProtocolloService alberoprocProtocolloService;
    private IVerticalizzazioneProtocolloAttivoService vertProtoAttivoService;
    private TipisoggettopeopleService tipisoggettopeopleService;
    private AmministrazioniService amministrazioniService;
    private String flusso;
    private Istanze istanza;

    public DestinatariResolver(AlberoprocService alberoprocService, AlberoprocProtocolloService alberoprocProtocolloService,
	    AmministrazioniService amministrazioniService, IVerticalizzazioneProtocolloAttivoService vertProtoAttivoService,
	    TipisoggettopeopleService tipisoggettopeopleService, String flusso, Istanze istanza) {

	this.alberoprocService = alberoprocService;
	this.alberoprocProtocolloService = alberoprocProtocolloService;
	this.vertProtoAttivoService = vertProtoAttivoService;
	this.tipisoggettopeopleService = tipisoggettopeopleService;
	this.amministrazioniService = amministrazioniService;
	this.flusso = flusso;
	this.istanza = istanza;
    }

    public List<ProtocolloSoggettoCommand> resolve() {

	if (ProtocollazioneCommand.FLUSSO_ARRIVO == this.flusso || ProtocollazioneCommand.FLUSSO_INTERNO == this.flusso) {
	    ProtocolloSoggettoCommand protAmministrazione = null;
	    Alberoproc ap = this.alberoprocService.findById(new PkId(istanza.getAlberoproc().getId().getCodice()));
	    AlberoprocHelper helper = this.alberoprocService.findAlberoprocHelper(ap);
	    List<PercorsoAlberoprocHelper> percorsoAlberoprocHelpers = helper.getPercorsoAlberoprocHelpers();
	    for (PercorsoAlberoprocHelper pah : percorsoAlberoprocHelpers) {
		AlberoprocProtocollo app = alberoprocProtocolloService.findByAlberoprocIdAndComune(pah.getId(),
			this.istanza.getComune().getCodicecomune());
		if (app != null && app.getAmministrazioni() != null && app.getAmministrazioni().getId() != null
			&& app.getAmministrazioni().getId().getCodice() != null) {
		    protAmministrazione = ProtocolloSoggettoCommand.fromAmministrazione(app.getAmministrazioni(),
			    this.vertProtoAttivoService.getMezzoDefault(), this.vertProtoAttivoService.getModalitaTrasmissioneDefault());
		    break;
		}
	    }
	    if (protAmministrazione == null) {
		Integer codiceAmministrazione = this.vertProtoAttivoService.getCodiceAmministrazioneDefault();
		if (codiceAmministrazione != null) {
		    Amministrazioni amministrazione = this.amministrazioniService.findById(new PkId(codiceAmministrazione));
		    if (amministrazione == null) {
			throw new DestinatariResolverException("Nella verticalizzazione " + vertProtoAttivoService.nomeVerticalizzazione() +
							       " è impostato un codice amministrazione inesistente");
		    }
		    protAmministrazione = ProtocolloSoggettoCommand.fromAmministrazione(amministrazione,
			    this.vertProtoAttivoService.getMezzoDefault(), this.vertProtoAttivoService.getModalitaTrasmissioneDefault());
		}
	    }
	    if (protAmministrazione == null) {
		throw new DestinatariResolverException("Amministrazione destinatario non presente");
	    }
	    if (!protAmministrazione.getAmministrazioni().haUnitaOrganizzativaORuoloSettati(istanza.getComune().getCodicecomune(),
		    istanza.getSoftware().getCodice())) {
		Integer codiceAmministrazione = protAmministrazione.getAmministrazioni().getId().getCodice();
		StringBuilder sb = new StringBuilder("") //
			.append("L'amministrazione con codice ") //
			.append(codiceAmministrazione) //
			.append(" per il comune ") //
			.append(istanza.getComune().getCodicecomune()) //
			.append(" non ha settato ne' unità organizzativa ne' ruolo");
		throw new DestinatariResolverException(sb.toString());
	    }
	    List<ProtocolloSoggettoCommand> retVal = new ArrayList<ProtocolloSoggettoCommand>();
	    retVal.add(protAmministrazione);
	    return retVal;
	} else {
	    if (this.istanza == null) {
		throw new DestinatariResolverException("Istanza non valorizzata");
	    }
	    ProtMittDestAutoResolverNotAutomatica resolver = new ProtMittDestAutoResolverNotAutomatica(this.vertProtoAttivoService, istanza,
		    this.tipisoggettopeopleService);
	    List<ProtocolloSoggettoCommand> retVal = resolver.resolveMittDestAnagrafe();
	    if (retVal.isEmpty()) {
		throw new DestinatariResolverException(
			"Un protocollo in partenza deve avere come destinatario almeno un'amministrazione o un'anagrafica!");
	    }
	    return retVal;
	}
    }
}
