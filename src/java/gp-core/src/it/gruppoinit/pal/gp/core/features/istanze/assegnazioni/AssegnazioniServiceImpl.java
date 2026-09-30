package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiDettaglio;
import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiDettaglioId;
import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiTestata;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttori;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.GruppiIstruttoriService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class AssegnazioniServiceImpl implements IAssegnazioniService {

    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private IAssegnazioneGruppiTestataService assegnazioneGruppiTestataService;
    @Autowired
    private IAssegnazioneGruppiDettaglioService assegnazioneGruppiDettaglioService;
    @Autowired
    private GruppiIstruttoriService gruppiIstruttoriService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @Override
    public void assegnaPratica(Integer codiceIstanza, ResponsabileIstanzaEnum responsabileIstanzaEnum, Integer codiceResposabile) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	if (istanza.getGruppiIstruttori() == null) {
	    throw new IllegalArgumentException("Non sono configurati correttamente i gruppi istruttori per l'istanza");
	}
	boolean chiusa = istanzeService.isChiusa(codiceIstanza);
	if (!chiusa) {
	    Integer gruppoIstruttori = null;
	    if (responsabileIstanzaEnum.equals(ResponsabileIstanzaEnum.RESPONSABILE_PROCEDIMENTO)) {
		if (istanza.getGruppiIstruttori() == null) {
		    throw new RuntimeException("Il gruppo istruttori non risulta essere configurato correttamente");
		}
		gruppoIstruttori = istanza.getGruppiIstruttori().getId().getCodice();
	    } else if (responsabileIstanzaEnum.equals(ResponsabileIstanzaEnum.RESPONSABILE_ISTRUTTORIA)) {
		gruppoIstruttori = this.verificaVerticalizzazione(istanza);
		if (gruppoIstruttori == null) {
		    if (istanza.getGruppiIstruttori() != null && istanza.getGruppiIstruttori().getId() != null
			    && istanza.getGruppiIstruttori().getId().getCodice() != null) {
			gruppoIstruttori = istanza.getGruppiIstruttori().getId().getCodice();
		    } else {
			throw new RuntimeException("Gruppo istruttori non correttamente impostato");
		    }
		}
	    }
	    Integer idTestata = assegnazioneGruppiTestataService.trovaTestataAperta(gruppoIstruttori);
	    AssegnazioneGruppiTestata assegnazioneGruppiTestata = null;
	    if (idTestata == null) {
		assegnazioneGruppiTestata = new AssegnazioneGruppiTestata();
		PkId id = new PkId();
		id.setIdcomune(ORMHelper.getIdcomune());
		assegnazioneGruppiTestata.setId(id);
		assegnazioneGruppiTestata.setAmbito(responsabileIstanzaEnum.name());
		assegnazioneGruppiTestata.setDataApertura(new Date());
		GruppiIstruttori gi = gruppiIstruttoriService.findById(new PkId(gruppoIstruttori));
		assegnazioneGruppiTestata.setGruppiIstruttori(gi);
		assegnazioneGruppiTestataService.insert(assegnazioneGruppiTestata);
	    } else {
		assegnazioneGruppiTestata = assegnazioneGruppiTestataService.findById(idTestata);
	    }
	    AssegnazioneGruppiDettaglio assegnazioneGruppiDettaglio = new AssegnazioneGruppiDettaglio();
	    AssegnazioneGruppiDettaglioId idDet = new AssegnazioneGruppiDettaglioId();
	    idDet.setCodiceIstanza(codiceIstanza);
	    idDet.setIdcomune(ORMHelper.getIdcomune());
	    idDet.setIdTestata(assegnazioneGruppiTestata.getId().getCodice());
	    assegnazioneGruppiDettaglio.setId(idDet);
	    assegnazioneGruppiDettaglioService.insert(assegnazioneGruppiDettaglio);
	}
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
}
