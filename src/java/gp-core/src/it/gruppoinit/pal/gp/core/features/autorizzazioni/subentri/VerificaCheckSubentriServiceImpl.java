package it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean.CHIAMANTE;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class VerificaCheckSubentriServiceImpl implements IVerificaCheckSubentriService {

    @Autowired
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private AutorizzazioniSubentriService autorizzazioniSubentriService;

    @Override
    public EsitoElaborazioneEvento checkPossoSubentrare(CheckSubentroRequest request) {

	List<OperazioneEventoBean> errors = new ArrayList<OperazioneEventoBean>();
	Integer idAutConcSubentro = request.getIdAutConc();
	Integer autorizzazioneAttuale = autorizzazioniConcessioniService.getFkIdautAttualePerAutCollegata(idAutConcSubentro);
	if (autorizzazioneAttuale != null) {
	    Autorizzazioni autSubentrata = autorizzazioniService.findById(new PkId(idAutConcSubentro));
	    StringBuilder messaggio = new StringBuilder("Non è possibile subentrare l'autorizzazione ") //
		    .append(autSubentrata.getTransientEstremiAut()) //
		    .append(" che risulta atto collegato a ");
	    List<AutorizzazioniConcessioni> concessioni = autorizzazioniConcessioniService.findByAutorizzazioneAttuale(autorizzazioneAttuale);
	    for (AutorizzazioniConcessioni concessione : concessioni) {
		messaggio.append(" concessione: ").append(concessione.getTransientEstremiConcessione());
		concessione.getTransientEstremiConcessione();
	    }
	    OperazioneEventoBean ope = new OperazioneEventoBean(String.valueOf(idAutConcSubentro), messaggio.toString(),
		    CHIAMANTE.AUTORIZZAZIONI_SUBENTRI);
	    errors.add(ope);
	}
	List<AutorizzazioniSubentri> subentri = autorizzazioniSubentriService.findByAutorizzazione(idAutConcSubentro, 0, 1);
	if (!subentri.isEmpty()) {
	    AutorizzazioniSubentri ultimoSubentro = subentri.get(0);
	    if (request.getDatiCausali().getDataCessazione() != null
		    && Utilities.isDateGreater(ultimoSubentro.getDataCessazione(), request.getDatiCausali().getDataCessazione())) {
		StringBuilder messaggio = new StringBuilder("Non è possibile subentrare l'autorizzazione ") //
			.append(" la data di cessazione indicata [") //
			.append(Utilities.formatDate(request.getDatiCausali().getDataCessazione(), false)) //
			.append("] è precedente all'ultimo subentro effettuato [") //
			.append(Utilities.formatDate(ultimoSubentro.getDataCessazione(), false)) //
			.append("]");
		OperazioneEventoBean ope = new OperazioneEventoBean(String.valueOf(idAutConcSubentro), messaggio.toString(),
			CHIAMANTE.AUTORIZZAZIONI_SUBENTRI);
		errors.add(ope);
	    }
	}
	return new EsitoElaborazioneEvento(new ArrayList<OperazioneEventoBean>(), errors);
    }
}
