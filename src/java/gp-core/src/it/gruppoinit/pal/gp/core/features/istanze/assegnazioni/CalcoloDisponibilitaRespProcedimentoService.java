package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.GruppiIstruttoriResp;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriRespService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliAssenzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

public class CalcoloDisponibilitaRespProcedimentoService implements ICalcoloDisponibilitaService {

    private static final Logger log = LoggerFactory.getLogger(CalcoloDisponibilitaRespProcedimentoService.class);
    private IstanzeService istanzeService;
    private AlberoprocService alberoprocService;
    private GruppiIstruttoriRespService gruppiIstruttoriRespService;
    private ResponsabiliAssenzeService responsabiliAssenzeService;
    private ResponsabiliService responsabiliService;
    private IAssegnazioneGruppiTestataService assegnazioneGruppiTestataService;
    private IAssegnazioneGruppiDettaglioService assegnazioneGruppiDettaglioService;

    public CalcoloDisponibilitaRespProcedimentoService(IstanzeService istanzeService, AlberoprocService alberoprocService,
	    GruppiIstruttoriRespService gruppiIstruttoriRespService, ResponsabiliAssenzeService responsabiliAssenzeService,
	    ResponsabiliService responsabiliService, IAssegnazioneGruppiTestataService assegnazioneGruppiTestataService,
	    IAssegnazioneGruppiDettaglioService assegnazioneGruppiDettaglioService) {

	super();
	this.istanzeService = istanzeService;
	this.alberoprocService = alberoprocService;
	this.gruppiIstruttoriRespService = gruppiIstruttoriRespService;
	this.responsabiliAssenzeService = responsabiliAssenzeService;
	this.responsabiliService = responsabiliService;
	this.assegnazioneGruppiTestataService = assegnazioneGruppiTestataService;
	this.assegnazioneGruppiDettaglioService = assegnazioneGruppiDettaglioService;
    }

    @Override
    public List<DisponibilitaResponsabile> calcola(Integer codiceIstanza) {

	//trovo i responsabili
	List<DisponibilitaResponsabile> list = new ArrayList<DisponibilitaResponsabile>();
	Istanze ist = istanzeService.findById(new PkId(codiceIstanza));
	if (ist.getGruppiIstruttori() != null && ist.getGruppiIstruttori().getId() != null && ist.getGruppiIstruttori().getId().getCodice() != null) {
	    Integer idTestata = assegnazioneGruppiTestataService.trovaTestataAperta(ist.getGruppiIstruttori().getId().getCodice());
	    Integer idAlberoproc = ist.getAlberoproc().getId().getCodice();
	    String scCodice = alberoprocService.findGerarchiaAlberoGruppi(idAlberoproc, ist.getGruppiIstruttori().getId().getCodice());
	    log.debug("CalcoloDisponibilitaRespProcedimentoService#calcola - idTestata: {}, idAlberoProc: {}, scCodice: {}",
		    new Object[] { idTestata, idAlberoproc, scCodice });
	    Map<Integer, Integer> codResp = new HashMap<Integer, Integer>();
	    List<GruppiIstruttoriResp> grsits = gruppiIstruttoriRespService.findByGruppoIstruttori(ist.getGruppiIstruttori().getId().getCodice(),
		    true, true, null, null);
	    for (GruppiIstruttoriResp gist : grsits) {
		if ((gist.getResponsabili() != null && gist.getResponsabili().getId() != null && gist.getResponsabili().getId().getCodice() != null)
			&& (!responsabiliAssenzeService.isAssente(gist.getResponsabili(), ist.getData()))) {
		    codResp.put(gist.getResponsabili().getId().getCodice(), Integer.valueOf(0));
		}
	    }
	    if (codResp.size() > 0) {
		this.calcolaDisponibilita(codiceIstanza, list, scCodice, codResp, ist, idTestata);
	    }
	}
	Collections.sort(list, new Comparator<DisponibilitaResponsabile>() {

	    @Override
	    public int compare(DisponibilitaResponsabile o1, DisponibilitaResponsabile o2) {

		if (o1.getPercentualeAssegnata().compareTo(o2.getPercentualeAssegnata()) > 0) {
		    return 1;
		} else if (o1.getPercentualeAssegnata().compareTo(o2.getPercentualeAssegnata()) < 0) {
		    return -1;
		} else {
		    return 0;
		}
	    }
	});
	return list;
    }

    private void calcolaDisponibilita(Integer codiceIstanza, List<DisponibilitaResponsabile> list, String scCodice, Map<Integer, Integer> codResp,
	    Istanze ist, Integer idTestata) {

	List<ChiaveValoreBean<Integer, Integer>> ls = istanzeService.countPresenzeResponsabiliPerIstanzeInCorsoNew(codiceIstanza, codResp, scCodice,
		idTestata);
	for (ChiaveValoreBean<Integer, Integer> cbv : ls) {
	    Integer codiceResponsabile = cbv.getChiave();
	    Integer istanzeResponsabile = cbv.getValore();
	    codResp.put(codiceResponsabile, istanzeResponsabile);
	}
	// trovate il conteggio istanze per operatore adesso devo valutare il peso
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
	}
	if (isChiudiGruppo) {
	    log.debug("#CalcoloDisponibilitaRespProcedimentoService - chiudo il gruppo in automatico");
	    assegnazioneGruppiTestataService.chiudiAssegnazione(idTestata);
	}
    }

    @Override
    public NumeriIstanzaResponse dettaglio(Integer codiceResponsabile, Integer idTestata) {

	NumeriIstanzaResponse nir = new NumeriIstanzaResponse();
	String tipoResp = ResponsabileIstanzaEnum.RESPONSABILE_PROCEDIMENTO.name();
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
