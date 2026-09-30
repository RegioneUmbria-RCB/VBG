package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ProtocolloSoggettoCommand;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.destinatari.IndirizzoMailResolver;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.service.TipisoggettopeopleService;

public class ProtMittDestAutoResolverNotAutomatica {

    private IVerticalizzazioneProtocolloAttivoService verticalizzazioniService;
    private Istanze i;
    private TipoMittDestAutoEnum tipo = TipoMittDestAutoEnum.RICHIEDENTE_AZIENDA; // valore default
    private TipisoggettopeopleService tipisoggettopeopleService;

    public ProtMittDestAutoResolverNotAutomatica(IVerticalizzazioneProtocolloAttivoService verticalizzazioniService, Istanze istanza,
	    TipisoggettopeopleService tipisoggettopeopleService) {

	this.verticalizzazioniService = verticalizzazioniService;
	this.tipisoggettopeopleService = tipisoggettopeopleService;
	this.i = istanza;
    }

    public ProtocolloSoggettoCommand resolveMittenteAmministrazione(Amministrazioni amministrazione) {

	return ProtocolloSoggettoCommand.fromAmministrazione(amministrazione, this.verticalizzazioniService.getMezzoDefault());
    }

    public ProtocolloSoggettoCommand resolveDestinatarioAmministrazione(Amministrazioni amministrazione) {

	return ProtocolloSoggettoCommand.fromAmministrazione(amministrazione, this.verticalizzazioniService.getMezzoDefault());
    }

    /**
     * <pre>
     TIPO_MITTDEST_AUTO
     Indica con quale dato deve essere valorizzato il mittente / destinatario per le protocollazioni automatiche, se una protocollazione è in arrivo allora il 
    	    soggetto interessato sarà il mittente, se la protocollazione automatica sarà in partenza allora il soggetto sarà il destinatario.
    	    Può assumere i seguenti valori: 
     se valorizzato a 0 (o nullo) il sistema indicherà come mittente / destinatario il Richiedente e l'Azienda, 
     se valorizzato a 1 verrà proposto solo il Richiedente, 
     se valorizzato a 2 il sistema indicherà come Mittenti solo l'Azienda, se, in questo caso l'Azienda non è presente indicherà solamente il Richiedente,
     se valorizzato a 3 il sistema indicherà l'azienda e il tecnico, se l'azienda non è presente indicherà il richiedente, 
     se valorizzato a 4 il sistema indicherà solo il tecnico, se non presente il richiedente.
     se valorizzato a 5 se azienda ha stesso cf di richiedente e qualifica del soggetto ha quella mappatura allora prendo richiedente
     * </pre>
     */
    public List<ProtocolloSoggettoCommand> resolveMittDestAnagrafe() {

	ProtocolloMezzi mezzo = this.verticalizzazioniService.getMezzoDefault();
	IndirizzoMailResolver mailResolver = new IndirizzoMailResolver(this.verticalizzazioniService, i.getComune().getCodicecomune(),
		i.getSoftware().getCodice());
	List<ProtocolloSoggettoCommand> ret = new ArrayList<ProtocolloSoggettoCommand>();
	tipo = this.verticalizzazioniService.getTipoMittDestAuto(i.getSoftware().getCodice());
	switch (tipo) {
	case RICHIEDENTE_AZIENDA:
	    ret.add(ProtocolloSoggettoCommand.fromRichiedente(mailResolver, i.getRichiedente(), i.getDomicilioElettronico(), mezzo));
	    if (i.getTitolarelegale() != null) {
		ret.add(ProtocolloSoggettoCommand.fromTitolareLegale(mailResolver, i.getTitolarelegale(), i.getDomicilioElettronico(), mezzo));
	    }
	    break;
	case RICHIEDENTE:
	    ret.add(ProtocolloSoggettoCommand.fromRichiedente(mailResolver, i.getRichiedente(), i.getDomicilioElettronico(), mezzo));
	    break;
	case AZIENDA_O_RICHIEDENTE:
	    if (i.getTitolarelegale() != null) {
		ret.add(ProtocolloSoggettoCommand.fromTitolareLegale(mailResolver, i.getTitolarelegale(), i.getDomicilioElettronico(), mezzo));
	    } else {
		ret.add(ProtocolloSoggettoCommand.fromRichiedente(mailResolver, i.getRichiedente(), i.getDomicilioElettronico(), mezzo));
	    }
	    break;
	case TECNICO_E_AZIENDA_O_RICHIEDENTE:
	    if (i.getProfessionista() != null) {
		ret.add(ProtocolloSoggettoCommand.fromProfessionista(mailResolver, i.getProfessionista(), i.getDomicilioElettronico(), mezzo));
	    }
	    if (i.getTitolarelegale() != null) {
		ret.add(ProtocolloSoggettoCommand.fromTitolareLegale(mailResolver, i.getTitolarelegale(), i.getDomicilioElettronico(), mezzo));
	    } else {
		ret.add(ProtocolloSoggettoCommand.fromRichiedente(mailResolver, i.getRichiedente(), i.getDomicilioElettronico(), mezzo));
	    }
	    break;
	case TECNICO_O_RICHIEDENTE:
	    if (i.getProfessionista() != null) {
		ret.add(ProtocolloSoggettoCommand.fromProfessionista(mailResolver, i.getProfessionista(), i.getDomicilioElettronico(), mezzo));
	    } else {
		ret.add(ProtocolloSoggettoCommand.fromRichiedente(mailResolver, i.getRichiedente(), i.getDomicilioElettronico(), mezzo));
	    }
	    break;
	case DITTA_INDIVIDUALE:
	    String mappaturaDI = this.verticalizzazioniService.getMappaturaDittaIndividuale();
	    if (i.getTitolarelegale() == null) {
		// SE AZIENDA NULLA
		ret.add(ProtocolloSoggettoCommand.fromRichiedente(mailResolver, i.getRichiedente(), i.getDomicilioElettronico(), mezzo));
	    } else if (StringUtils.isBlank(mappaturaDI) || EntityUtils.getNestedProperty(i.getTipisoggetto(), "id.codice") == null
		    || !StringUtils.defaultString(i.getTitolarelegale().getCodicefiscale(), "CF_AZIENDA_SE_NULL").trim()
			    .equalsIgnoreCase(StringUtils.defaultString(i.getRichiedente().getCodicefiscale(), "CF_RICHIEDENTE_SE_NULL").trim())) {
		// SE mappaturaDI==null qualifica null O CF RICHIEDENTE<>CF AZIENDA
		// se valorizzato a 5 se azienda ha stesso cf di richiedente e qualifica del soggetto ha quella mappatura allora prendo richiedente
		ret.add(ProtocolloSoggettoCommand.fromTitolareLegale(mailResolver, i.getTitolarelegale(), i.getDomicilioElettronico(), mezzo));
	    } else {
		// verifico se la qualifica del soggetto ha la mappatura configurata nelle verticalizzazioni
		if (tipisoggettopeopleService.existsByTipoSoggettoAndMappatura(i.getTipisoggetto().getId().getCodice(), mappaturaDI)) {
		    ret.add(ProtocolloSoggettoCommand.fromRichiedente(mailResolver, i.getRichiedente(), i.getDomicilioElettronico(), mezzo));
		} else {
		    ret.add(ProtocolloSoggettoCommand.fromTitolareLegale(mailResolver, i.getTitolarelegale(), i.getDomicilioElettronico(), mezzo));
		}
	    }
	    break;
	default:
	    break;
	}
	return ret;
    }
}
