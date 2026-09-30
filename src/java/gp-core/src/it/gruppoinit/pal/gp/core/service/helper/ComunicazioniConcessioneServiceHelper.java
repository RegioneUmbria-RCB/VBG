package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniDConcessioni;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniT;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDConcessioniService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniManagerService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComunicazioniConcessioneServiceHelper extends ComunicazioniServiceHelper<AutorizzazioniConcessioni> {

    private static final Logger log = LoggerFactory.getLogger(ComunicazioniConcessioneServiceHelper.class);
    private ComunicazioniDConcessioniService comunicazioniDConcessioniService;
    private ComunicazioniManagerService comunicazioniManagerService;

    public ComunicazioniConcessioneServiceHelper(ComunicazioniManagerService comunicazioniManagerService,
	    ComunicazioniDConcessioniService comunicazioniDConcessioniService) {

	super(comunicazioniManagerService);
	this.comunicazioniDConcessioniService = comunicazioniDConcessioniService;
	this.comunicazioniManagerService = comunicazioniManagerService;
    }

    @Override
    public List<ComunicazioniDHelper> createComunicazioniDHelper(Integer codicecomunicaziot) {

	List<ComunicazioniDHelper> ris = new ArrayList<ComunicazioniDHelper>();
	List<ComunicazioniDConcessioni> list = comunicazioniDConcessioniService.findByComunicazioniTNonTerminate(codicecomunicaziot);
	log.debug("createComunicazioniDHelper# recuperata lista ComunicazioniDConcessioni per comunicazione t = {}", codicecomunicaziot);
	ComunicazioniDHelper comunicazioniDHelper = null;
	for (ComunicazioniDConcessioni comunicazioniDConcessioni : list) {
	    comunicazioniDHelper = new ComunicazioniDHelper();
	    comunicazioniDHelper.setCodiceComunicazioneD(comunicazioniDConcessioni.getComunicazioniD().getId().getCodice());
	    comunicazioniDHelper.setCodiceIstanza(comunicazioniDConcessioni.getAutorizzazioniConcessioni().getAutorizzazioniByFkAutconcAutatt()
		    .getIstanza().getId().getCodice());
	    String a = comunicazioniDConcessioni.getAutorizzazioniConcessioni().getAutorizzazioniByFkAutconcAutatt().getIstanza()
		    .getDomicilioElettronico();
	    if (StringUtils.isBlank(a)) {
		log.debug("createComunicazioniDHelper# ");
		a = StringUtils.defaultIfEmpty(comunicazioniDConcessioni.getAutorizzazioniConcessioni().getAutorizzazioniByFkAutconcAutatt()
			.getIstanza().getRichiedente().getPec(), comunicazioniDConcessioni.getAutorizzazioniConcessioni()
			.getAutorizzazioniByFkAutconcAutatt().getIstanza().getRichiedente().getEmail());
		log.debug("createComunicazioniDHelper# Destinatario = {} ({})", a, "Indirizzo email/pec richiedente");
		comunicazioniDHelper.setDestinatarioA(a);
	    } else {
		log.debug("createComunicazioniDHelper# Destinatario = {} ({})", a, "Domicilio elettronico");
		comunicazioniDHelper.setDestinatarioA(a);
	    }
	    ris.add(comunicazioniDHelper);
	}
	return ris;
    }

    @Override
    protected void inizializzaDettaglioComunicazione(List<AutorizzazioniConcessioni> list, ComunicazioniT comunicazioniT) {

	for (AutorizzazioniConcessioni autorizzazioniConcessioni : list) {
	    ComunicazioniD comunicazioniD = new ComunicazioniD();
	    comunicazioniD.setComunicazioniT(comunicazioniT);
	    comunicazioniD.setStatoElaborazione(ComunicazioniDStatoEnum.NON_INIZIALIZZATA.value());
	    comunicazioniManagerService.insertComunicazioneD(comunicazioniD);
	    ComunicazioniDConcessioni comunicazioniDConcessioni = new ComunicazioniDConcessioni();
	    comunicazioniDConcessioni.setComunicazioniD(comunicazioniD);
	    comunicazioniDConcessioni.setAutorizzazioniConcessioni(autorizzazioniConcessioni);
	    comunicazioniDConcessioniService.insert(comunicazioniDConcessioni);
	    log.debug("iniziaizzaDettaglioComunicazione# Inserita dettaglio comunicazione per la concessione = {}, autorizzazione numero = {}",
		    autorizzazioniConcessioni.getId().getCodice(), autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutatt().getAutorigNumero());
	}
    }

    @Override
    protected boolean validateEntityPerComunicazione(ComunicazioniT comunicazioniT) {

	// non implemento il metodo perchè al momento faccio una pre validazione al salvataggio delle
	// comunicazioni
	return true;
    }
}
