package it.gruppoinit.pal.gp.core.features.protocollazione.logic.mittenti;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.mittenti.exceptions.MittentiResolverException;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrProtocolloService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.TipisoggettopeopleService;

public class MittentiResolver {

    private AlberoprocService alberoprocService;
    private AlberoprocProtocolloService alberoprocProtocolloService;
    private IVerticalizzazioneProtocolloAttivoService vertProtoAttivoService;
    private AmministrazioniService amministrazioniService;
    private TipisoggettopeopleService tipisoggettopeopleService;
    private Istanze istanza;
    private String codiceComune;
    private String flusso;

    public MittentiResolver(AlberoprocService alberoprocService, AlberoprocProtocolloService alberoprocProtocolloService,
	    IVerticalizzazioneProtocolloAttivoService vertProtoAttivoService, AmministrazioniService amministrazioniService,
	    AmministrProtocolloService amministrazioniProtocolloService, TipisoggettopeopleService tipisoggettopeopleService, String codiceComune,
	    Istanze istanza, String flusso) {

	this.alberoprocService = alberoprocService;
	this.alberoprocProtocolloService = alberoprocProtocolloService;
	this.vertProtoAttivoService = vertProtoAttivoService;
	this.amministrazioniService = amministrazioniService;
	this.tipisoggettopeopleService = tipisoggettopeopleService;
	if (StringUtils.isBlank(flusso)) {
	    throw new MittentiResolverException("Impossibile determinare i mittenti senza passare il flusso");
	}
	if (!flusso.equals(ProtocollazioneCommand.FLUSSO_ARRIVO) && !flusso.equals(ProtocollazioneCommand.FLUSSO_INTERNO)
		&& !flusso.equals(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
	    throw new MittentiResolverException("Impossibile determinare i mittenti, flusso " + flusso + " non valido");
	}
	this.codiceComune = codiceComune;
	this.istanza = istanza;
	this.flusso = flusso;
    }

    public List<ProtocolloSoggettoCommand> resolve() {

	if (ProtocollazioneCommand.FLUSSO_PARTENZA == this.flusso || ProtocollazioneCommand.FLUSSO_INTERNO == this.flusso) {
	    List<ProtocolloSoggettoCommand> retVal = new ArrayList<ProtocolloSoggettoCommand>();
	    retVal.add(this.mittentiFlussoPartenzaInterno());
	    return retVal;
	} else {
	    ProtMittDestAutoResolverNotAutomatica resolver = new ProtMittDestAutoResolverNotAutomatica(this.vertProtoAttivoService, istanza,
		    this.tipisoggettopeopleService);
	    return resolver.resolveMittDestAnagrafe();
	}
    }

    private ProtocolloSoggettoCommand mittentiFlussoPartenzaInterno() {

	Integer codiceAmministrazione = this.getCodiceAmministrazione();
	if (codiceAmministrazione == null) {
	    return null;
	}
	Amministrazioni amm = this.amministrazioniService.findById(new PkId(codiceAmministrazione));
	return ProtocolloSoggettoCommand.fromAmministrazione(amm, this.vertProtoAttivoService.getMezzoDefault(),
		this.vertProtoAttivoService.getModalitaTrasmissioneDefault());
    }

    private Integer getCodiceAmministrazione() {

	Alberoproc ap = this.alberoprocService.findById(new PkId(istanza.getAlberoproc().getId().getCodice()));
	AlberoprocHelper helper = this.alberoprocService.findAlberoprocHelper(ap);
	List<PercorsoAlberoprocHelper> percorsoAlberoprocHelpers = helper.getPercorsoAlberoprocHelpers();
	for (PercorsoAlberoprocHelper pah : percorsoAlberoprocHelpers) {
	    AlberoprocProtocollo app = alberoprocProtocolloService.findByAlberoprocIdAndComune(pah.getId(), codiceComune);
	    if (app != null && app.getAmministrazioni() != null && app.getAmministrazioni().getId() != null
		    && app.getAmministrazioni().getId().getCodice() != null) {
		return app.getAmministrazioni().getId().getCodice();
	    }
	}
	return this.vertProtoAttivoService.getCodiceAmministrazioneDefault();
    }
}
