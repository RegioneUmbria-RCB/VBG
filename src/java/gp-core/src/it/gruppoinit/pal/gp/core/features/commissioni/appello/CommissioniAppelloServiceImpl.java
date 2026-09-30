package it.gruppoinit.pal.gp.core.features.commissioni.appello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommedilizieCarica;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.commissioni.appello.models.SoggettoPraticaModel;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.ICommissioniAuditingService;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioSoggettoAppelloEliminato;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioSoggettoAppelloNuovo;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class CommissioniAppelloServiceImpl implements ICommissioniAppelloService {

    ICommissioniAppelloDAO appelloDAO;
    private ICommissioniAuditingService auditingService;
    private UserSecurityService userSecurityService;
    private AnagrafeService anagrafeService;

    @Autowired
    public CommissioniAppelloServiceImpl(ICommissioniAppelloDAO appelloDAO, ICommissioniAuditingService auditingService,
	    UserSecurityService userSecurityService, AnagrafeService anagrafeService) {

	this.appelloDAO = appelloDAO;
	this.auditingService = auditingService;
	this.userSecurityService = userSecurityService;
	this.anagrafeService = anagrafeService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void convocaSoggettiIstanza(Integer idRiga, List<SoggettoPraticaModel> listSoggetti) {

	if (idRiga == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo convocaSoggettiIstanza senza passare la riga della commissione");
	}
	//1. Recupero la commissione
	CommissioniedilizieR riga = (CommissioniedilizieR) this.appelloDAO.getById(CommissioniedilizieR.class, idRiga);
	Integer idCommissione = riga.getCommissioniedilizieT().getId().getCodice();
	for (SoggettoPraticaModel spm : listSoggetti) {
	    Integer codiceAnagrafe = spm.getCodiceAnagrafe();
	    Integer codiceCarica = spm.getCodiceCarica();
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceAnagrafe));
	    boolean selezionato = spm.isSelezionato();
	    // per ogni convocato verifico se già registrato   
	    Integer idAppello = this.appelloDAO.soggettoPresenteInAppello(idCommissione, codiceAnagrafe);
	    if (idAppello == null) { //non presente in appello
		if (selezionato) {
		    idAppello = this.appelloDAO.convocaSoggetto(idCommissione, codiceAnagrafe, codiceCarica);
		    this.appelloDAO.collegaAppelloAPratica(idAppello, idRiga);
		    //TODO messaggio di inserimento
		    this.auditingService.log(riga.getCommissioniedilizieT().getId().getCodice(),
			    new MessaggioSoggettoAppelloNuovo(this.getResponsabile(), anagrafe.getDescrizioneRichiedenteBreve()));
		} // se non presente in appello e deselezionato allora niente
		continue;
	    }
	    //  PRESENTI IN appello
	    if (!selezionato) { // è stata tolta l'associazione dalla pratica
		// devo togliere associazione della pratica
		//TODO Controlla che non ci siano già votazioni per quello appello
		if (this.appelloDAO.esisteVotazione(idAppello)) {
		    throw new RuntimeException("Non è possibile rimuovere un soggetto che ha participato alla votazioni");
		}
		this.appelloDAO.deleteAppelloPraticheByIdRigaAndAppello(idRiga, idAppello);
		int conteggioAppelloPratiche = this.appelloDAO.countCommEdilizieAppelloPraticheByAppelloAndRiga(idAppello, idRiga);
		if (conteggioAppelloPratiche == 0) {
		    // non ci sono pratiche assegnate cancello anche la convocazione
		    this.appelloDAO.deleteAppelloByIdAppello(idAppello);
		}
		//TODO messaggio di eliminazione
		this.auditingService.log(riga.getCommissioniedilizieT().getId().getCodice(),
			new MessaggioSoggettoAppelloEliminato(this.getResponsabile(), anagrafe.getDescrizioneRichiedenteBreve()));
		continue;
	    }
	    int conteggioAppelloPratiche = this.appelloDAO.countCommEdilizieAppelloPraticheByAppelloAndRiga(idAppello, idRiga);
	    // verifico che già sia presente l'associazione con la pratica / carica
	    // presente in appello e selezionato
	    // verifico che non sia già presente una riga in appello pratiche
	    if (conteggioAppelloPratiche == 0) { // se no la aggiungo altrimenti niente
		this.appelloDAO.collegaAppelloAPratica(idAppello, idRiga);
	    }
	    // aggiorno la carica
	    CommedilizieAppello appello = (CommedilizieAppello) this.appelloDAO.getById(CommedilizieAppello.class, idAppello);
	    CommedilizieCarica c = null;
	    if (codiceCarica != null) {
		c = (CommedilizieCarica) this.appelloDAO.getById(CommedilizieCarica.class, codiceCarica);
	    }
	    appello.setCommedilizieCarica(c);
	    this.appelloDAO.saveEntity(appello);
	}
    }

    private String getResponsabile() {

	return ((Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails()).getResponsabile();
    }
}
