package it.gruppoinit.pal.gp.core.features.protocollazione.logic.flusso;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.AmministrProtocollo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.TipiMovimentoStcMappingProtocollo;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.service.AmministrProtocolloService;
import it.gruppoinit.pal.gp.core.service.TipimovStcMappingService;
import it.gruppoinit.protocollo.schemas.messages.DatiAnagraficiType;

public class FlussoResolver {

    private TipimovStcMappingService tipimovStcMappingService;
    private AmministrProtocolloService amministrazioniProtocolloService;
    private IVerticalizzazioneProtocolloAttivoService vertProtoAttivo;
    private DatiAnagraficiType amministrazioneMittente;
    private boolean istanzaProtocollata;
    private String codiceComune;
    private String software;

    public FlussoResolver(TipimovStcMappingService tipimovStcMappingService, AmministrProtocolloService amministrazioniProtocolloService,
	    IVerticalizzazioneProtocolloAttivoService vertProtoAttivo, DatiAnagraficiType amministrazioneMittente, boolean istanzaProtocollata,
	    String codiceComune, String software) {

	this.tipimovStcMappingService = tipimovStcMappingService;
	this.amministrazioniProtocolloService = amministrazioniProtocolloService;
	this.vertProtoAttivo = vertProtoAttivo;
	this.amministrazioneMittente = amministrazioneMittente;
	this.istanzaProtocollata = istanzaProtocollata;
	this.codiceComune = codiceComune;
	this.software = software;
    }

    public String resolveDaMovimento(Movimenti movimento) {

	boolean mittentiPresenti = amministrazioneMittente != null;
	if (!mittentiPresenti && Boolean.TRUE.equals(movimento.getCreatoDaStc())) {
	    return ProtocollazioneCommand.FLUSSO_ARRIVO;
	}
	if (Boolean.TRUE.equals(movimento.getTipomovimento().getFlagStc())) {
	    if (movimento.getTipomovimento().getId().getTipomovimento().equalsIgnoreCase(vertProtoAttivo.getTipoMovRicevuta())
		    && !istanzaProtocollata) {
		throw new RuntimeException("Non e' possibile protocollare la ricevuta in quanto l'istanza non e' stata protocollata");
	    }
	}
	TipiMovimentoStcMappingProtocollo mappingProtocollo = this.tipimovStcMappingService
		.findDatiProtocolloByTipoMovimento(movimento.getTipomovimento().getId().getTipomovimento());
	if (mappingProtocollo != null && StringUtils.isNotBlank(mappingProtocollo.getFlusso())) {
	    return mappingProtocollo.getFlusso();
	}
	if (mittentiPresenti) {
	    String codAmm = amministrazioneMittente.getCod();
	    if (!StringUtils.isBlank(codAmm)) {
		Integer codiceAmministrazione = Integer.parseInt(codAmm);
		AmministrProtocollo ammProt = this.amministrazioniProtocolloService.findByAmministrazioneComuneESoftware(codiceAmministrazione,
			codiceComune, software);
		if (ammProt != null && StringUtils.isNotBlank(ammProt.getProtUo())) {
		    return ProtocollazioneCommand.FLUSSO_INTERNO;
		}
	    }
	}
	if (StringUtils.isBlank(vertProtoAttivo.getFlussoDefault())) {
	    throw new RuntimeException("La verticalizzazione protocollo_attivo non ha settato il campo flussodefault");
	}
	return vertProtoAttivo.getFlussoDefault();
    }
}
