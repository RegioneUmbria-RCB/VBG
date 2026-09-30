package it.gruppoinit.pal.gp.core.features.attivita.restrizioni;

import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione.IVerticalizzazioneIAttivitaService;
import it.gruppoinit.pal.gp.core.service.VwIAttivitalistaService;

public class RestrizioneLocalizzazioneEsistente implements IRestrizione {

    private IVerticalizzazioneIAttivitaService verticalizzazioneIAttivitaService;
    private VwIAttivitalistaService vwIAttivitalistaService;
    private Set<Istanzestradario> localizzazioni;
    private boolean checkAttive;
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 20;
    public static final String ETICHETTA = "service_error.esiste_gia_una_attivita_con_la_localizzazione";

    public RestrizioneLocalizzazioneEsistente(IVerticalizzazioneIAttivitaService verticalizzazioneIAttivitaService,
	    VwIAttivitalistaService vwIAttivitalistaService, Set<Istanzestradario> localizzazioni, boolean checkAttive) {

	this.verticalizzazioneIAttivitaService = verticalizzazioneIAttivitaService;
	this.vwIAttivitalistaService = vwIAttivitalistaService;
	this.localizzazioni = localizzazioni;
	this.checkAttive = checkAttive;
    }

    @Override
    public RestrizioneVerificataBean verifica() {

	RestrizioneVerificataBean retVal = new RestrizioneVerificataBean();
	if (this.localizzazioni == null || this.localizzazioni.isEmpty()) {
	    return retVal;
	}
	String[] software = null;
	String gruppoSoftware = this.verticalizzazioneIAttivitaService.getGruppoSoftware();
	//
	if (StringUtils.isNotBlank(gruppoSoftware)) {
	    software = StringUtils.stripAll(gruppoSoftware.split(","));
	}
	StringBuilder criterioDiRicerca = new StringBuilder("");
	for (Istanzestradario localizzazione : this.localizzazioni) {
	    criterioDiRicerca.append(localizzazione.getStradario().getPrefisso()).append(" ").append(localizzazione.getStradario().getDescrizione());
	    if (StringUtils.isNotBlank(localizzazione.getCivico())) {
		criterioDiRicerca.append(" ").append(localizzazione.getCivico());
	    }
	    if (localizzazione.getStradariocolore() != null) {
		criterioDiRicerca.append(" ").append(localizzazione.getStradariocolore().getId().getCodicecolore());
	    }
	    criterioDiRicerca.append(",");
	}
	retVal.setEtichettaEccezione(RestrizioneLocalizzazioneEsistente.ETICHETTA);
	retVal.setCriterioDiRicerca(criterioDiRicerca.substring(0, criterioDiRicerca.length() - 2));
	retVal.setElencoAttivita(this.vwIAttivitalistaService.findByLocalizzazioni(this.localizzazioni, software, this.checkAttive,
		RestrizioneLocalizzazioneEsistente.FIRST_RESULT, RestrizioneLocalizzazioneEsistente.MAX_RESULT));
	return retVal;
    }
}
