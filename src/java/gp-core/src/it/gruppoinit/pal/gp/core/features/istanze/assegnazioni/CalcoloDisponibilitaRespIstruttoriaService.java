package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttoriResp;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriRespService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliAssenzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class CalcoloDisponibilitaRespIstruttoriaService implements ICalcoloDisponibilitaService {

    private static final Logger log = LoggerFactory.getLogger(CalcoloDisponibilitaRespIstruttoriaService.class);
    private IstanzeService istanzeService;
    private VerticalizzazioniService verticalizzazioniService;
    private GruppiIstruttoriRespService gruppiIstruttoriRespService;
    private AlberoprocService alberoprocService;
    private ResponsabiliAssenzeService responsabiliAssenzeService;
    private ResponsabiliService responsabiliService;
    private IAssegnazioneGruppiTestataService assegnazioneGruppiTestataService;
    private IAssegnazioneGruppiDettaglioService assegnazioneGruppiDettaglioService;

    public CalcoloDisponibilitaRespIstruttoriaService(IstanzeService istanzeService, VerticalizzazioniService verticalizzazioniService,
	    GruppiIstruttoriRespService gruppiIstruttoriRespService, AlberoprocService alberoprocService,
	    ResponsabiliAssenzeService responsabiliAssenzeService, ResponsabiliService responsabiliService,
	    IAssegnazioneGruppiTestataService assegnazioneGruppiTestataService,
	    IAssegnazioneGruppiDettaglioService assegnazioneGruppiDettaglioService) {

	super();
	this.istanzeService = istanzeService;
	this.verticalizzazioniService = verticalizzazioniService;
	this.gruppiIstruttoriRespService = gruppiIstruttoriRespService;
	this.alberoprocService = alberoprocService;
	this.responsabiliAssenzeService = responsabiliAssenzeService;
	this.responsabiliService = responsabiliService;
	this.assegnazioneGruppiTestataService = assegnazioneGruppiTestataService;
	this.assegnazioneGruppiDettaglioService = assegnazioneGruppiDettaglioService;
    }

    @Override
    public List<DisponibilitaResponsabile> calcola(Integer codiceIstanza) {

	List<DisponibilitaResponsabile> list = new ArrayList<DisponibilitaResponsabile>();
	Istanze ist = istanzeService.findById(new PkId(codiceIstanza));
	Integer gruppoIstruttori = this.verificaVerticalizzazione(ist);
	Map<Integer, Integer> codResp = new HashMap<Integer, Integer>();
	String scCodice = "";
	if (gruppoIstruttori != null) {
	    this.assegnaAMappa(ist, gruppoIstruttori, codResp);
	} else {
	    if (ist.getGruppiIstruttori() != null && ist.getGruppiIstruttori().getId() != null
		    && ist.getGruppiIstruttori().getId().getCodice() != null) {
		gruppoIstruttori = ist.getGruppiIstruttori().getId().getCodice();
		Integer idAlberoproc = ist.getAlberoproc().getId().getCodice();
		scCodice = alberoprocService.findGerarchiaAlberoGruppi(idAlberoproc, ist.getGruppiIstruttori().getId().getCodice());
		this.assegnaAMappa(ist, ist.getGruppiIstruttori().getId().getCodice(), codResp);
	    }
	}
	Integer idTestata = 0;
	if (codResp.size() > 0) {
	    idTestata = assegnazioneGruppiTestataService.trovaTestataAperta(gruppoIstruttori);
	    log.debug("CalcoloDisponibilitaRespIstruttoriaService#calcola - idTestata: {}, gruppoIstruttori: {}",
		    new Object[] { idTestata, gruppoIstruttori });
	    List<ChiaveValoreBean<Integer, Integer>> ls = istanzeService.countPresenzeIstruttoriPerIstanzeInCorsoNew(codiceIstanza, codResp, scCodice,
		    idTestata);
	    for (ChiaveValoreBean<Integer, Integer> cbv : ls) {
		Integer codiceResponsabile = cbv.getChiave();
		Integer istanzeResponsabile = cbv.getValore();
		codResp.put(codiceResponsabile, istanzeResponsabile);
	    }
	}
	boolean isChiudiGruppo = true;
	for (Map.Entry<Integer, Integer> entry : codResp.entrySet()) {
	    Integer valore = entry.getValue();
	    Integer codiceResponsabile = entry.getKey();
	    Responsabili resp = responsabiliService.findById(new PkId(codiceResponsabile));
	    int peso = resp.getPesoCaricoLavoro() == null ? 0 : resp.getPesoCaricoLavoro();
	    if (peso > 0) {
		int carico = 100 * valore.intValue() / peso;
		if (carico < 100) {
		    isChiudiGruppo = false;
		}
		DisponibilitaResponsabile dr = new DisponibilitaResponsabile(codiceResponsabile, resp.getResponsabile(), peso, carico, ist,
			idTestata);
		list.add(dr);
	    }
	    if (isChiudiGruppo) {
		log.debug("#CalcoloDisponibilitaRespIstruttoriaService - chiudo il gruppo in automatico");
		assegnazioneGruppiTestataService.chiudiAssegnazione(idTestata);
	    }
	}
	Collections.sort(list, new Comparator<DisponibilitaResponsabile>() {

	    @Override
	    public int compare(DisponibilitaResponsabile o1, DisponibilitaResponsabile o2) {

		if (o1.getPercentualeAssegnata() != null && o2.getPercentualeAssegnata() != null) {
		    return o1.getPercentualeAssegnata().compareTo(o2.getPercentualeAssegnata());
		}
		return 0;
	    }
	});
	return list;
    }

    private Integer verificaVerticalizzazione(Istanze ist) {

	Integer gruppoIstruttori = null;
	boolean isAssegnazioneOperatori = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI);
	if (isAssegnazioneOperatori) {
	    Verticalizzazioniparametri idGruppo = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI,
		    WebConstants.VERTICALIZZAZIONE_ASSEGNAZIONE_OPERATORI_GRUPPO_ISTRUTTORI_DEFAULT, ist.getComune().getCodicecomune(),
		    ist.getSoftware().getCodice());
	    if ((idGruppo != null && StringUtils.isNotBlank(idGruppo.getValore())) && (Utilities.isInteger(idGruppo.getValore().trim()))) {
		gruppoIstruttori = Integer.parseInt(idGruppo.getValore().trim());
	    }
	}
	return gruppoIstruttori;
    }

    private void assegnaAMappa(Istanze ist, Integer gruppoIstruttori, Map<Integer, Integer> codResp) {

	List<GruppiIstruttoriResp> grsits = gruppiIstruttoriRespService.findByGruppoIstruttori(gruppoIstruttori, true, true, null, null);
	for (GruppiIstruttoriResp gist : grsits) {
	    if ((gist.getResponsabili() != null && gist.getResponsabili().getId() != null && gist.getResponsabili().getId().getCodice() != null)
		    && (!responsabiliAssenzeService.isAssente(gist.getResponsabili(), ist.getData()))) {
		codResp.put(gist.getResponsabili().getId().getCodice(), Integer.valueOf(0));
	    }
	}
    }

    @Override
    public NumeriIstanzaResponse dettaglio(Integer codiceResponsabile, Integer idTestata) {

	NumeriIstanzaResponse nir = new NumeriIstanzaResponse();
	String tipoResp = ResponsabileIstanzaEnum.RESPONSABILE_ISTRUTTORIA.name();
	List<IdentificativoDescrizioneBean> numeriIstanza = assegnazioneGruppiDettaglioService.findIstanzeByResponsabileETestata(codiceResponsabile,
		idTestata, tipoResp);
	if (numeriIstanza.isEmpty()) {
	    nir.setNumeriIstanza(new ArrayList<IdentificativoDescrizioneBean>());
	} else {
	    nir.setNumeriIstanza(numeriIstanza);
	}
	return nir;
    }
}
