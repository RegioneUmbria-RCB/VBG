package it.gruppoinit.pal.gp.core.features.nodopagamenti.sottoscrittori;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDaAllineare;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.IstoneriDettPosizioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DettPosizioneDebitoriaParametriEnte;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.DettPosizioneDaAllineareDAO;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.soggettopendenza.SoggettiPendenzaEnum;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoOnereIstanzaInserito;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;

@Service
public class SegnaPosizioneDaVerificareServiceImpl implements IEventSubscriber<EventoOnereIstanzaInserito> {

    private IstanzeoneriService istanzeoneriService;
    private static final String REGEX_RIF_POSIZIONE_DEBITORIA = "^\\$RIF:([A-Za-z0-9._%-]*):([\\d]*)$";
    private DettPosizioneDaAllineareDAO dettPosizioneDaAllineareDAO;
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    private final Pattern pattern = Pattern.compile(REGEX_RIF_POSIZIONE_DEBITORIA);
    private VerticalizzazioniService verticalizzazioniService;
    private ComuniService comuniService;

    @Autowired
    public SegnaPosizioneDaVerificareServiceImpl(IstanzeoneriService oneriService, DettPosizioneDaAllineareDAO dettPosizioneDaAllineareDAO,
	    DettPosizioneDebitoriaService dettPosizioneDebitoriaService, VerticalizzazioniService verticalizzazioniService,
	    ComuniService comuniService) {

	super();
	this.istanzeoneriService = oneriService;
	this.dettPosizioneDaAllineareDAO = dettPosizioneDaAllineareDAO;
	this.dettPosizioneDebitoriaService = dettPosizioneDebitoriaService;
	this.verticalizzazioniService = verticalizzazioniService;
	this.comuniService = comuniService;
    }

    @Override
    public void onEvent(EventoOnereIstanzaInserito e) {

	Istanzeoneri io = istanzeoneriService.findById(new PkId(e.getIdOnere()));
	if (io == null || StringUtils.isBlank(io.getDocriferimento()) || !io.getDocriferimento().matches(REGEX_RIF_POSIZIONE_DEBITORIA)) {
	    return;
	}
	// ciclare le istonerdettposbed per verifica se con quel riferimento trovo la posizione già inserita
	Matcher m = pattern.matcher(io.getDocriferimento());
	String cfEnteCreditore = "";
	String idPosizionedebitoria = "";
	if (m.find()) {
	    cfEnteCreditore = m.group(1);
	    idPosizionedebitoria = m.group(2);
	    DettPosizioneDebitoria dett = dettPosizioneDebitoriaService
		    .findByFkIdPosizioneDebitoriaAndCfEnteCreditore(Integer.valueOf(idPosizionedebitoria), cfEnteCreditore);
	    if (dett != null) {
		Set<IstoneriDettPosizioni> istoneriDettPosizioni = io.getIstoneriDettPosizioni();
		for (IstoneriDettPosizioni istopd : istoneriDettPosizioni) {
		    if (istopd.getDettPosizioneDebitoria().getId().getCodice().equals(dett.getId().getCodice())) {
			// ho già registrato la posizione debitoria e non devo fare altro
			return;
		    }
		}
	    }
	    insertPosizioneDebitoria(io.getDocriferimento(), io);
	}
    }

    private void insertPosizioneDebitoria(String rifDocumento, Istanzeoneri io) {

	Matcher m = pattern.matcher(rifDocumento);
	if (m.find()) {
	    String cfEnteCreditore = m.group(1);
	    String idPosizionedebitoria = m.group(2);
	    Istanze i = io.getIstanza();
	    String codiceComune = i.getComune().getCodicecomune();
	    SoggettiPendenzaEnum soggettoPendenza = new VerticalizzazioneNodoPagamentiServiceImpl(this.verticalizzazioniService, codiceComune)
		    .soggettoPendenza();
	    Anagrafe a = i.getRichiedente();
	    if (soggettoPendenza.equals(SoggettiPendenzaEnum.AZIENDA) && i.getTitolarelegale() != null) {
		a = i.getTitolarelegale();
	    }
	    String uuid = null; //TODO: settare uuid
	    DettPosizioneDebitoria d = new DettPosizioneDebitoria(a,
		    new DettPosizioneDebitoriaParametriEnte(cfEnteCreditore, codiceComune, i.getSoftware().getCodice()), comuniService);
	    d.setStato(".");
	    d.setDescStato(".");
	    d.setIdPosizioneDebitoria(Integer.parseInt(idPosizionedebitoria));
	    d.setCfEnteCreditore(cfEnteCreditore);
	    dettPosizioneDebitoriaService.insert(d);
	    Integer dettPosizioneDebitoriaId = d.getId().getCodice();
	    DettPosizioneDaAllineare dettDaAllineare = new DettPosizioneDaAllineare(dettPosizioneDebitoriaId);
	    dettPosizioneDaAllineareDAO.insert(dettDaAllineare);
	    istanzeoneriService.inserisciPosizioneDebitoriaSuOnere(io.getId().getCodice(), dettPosizioneDebitoriaId);
	}
    }
}
