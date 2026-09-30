package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeDMassive;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTMassive;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaMassiveDDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.ICreazioneMassiveTestataService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IWorkflowComunicazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.model.ComunicazioneMassivaGenModel;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.workflow.IWorkFlowComunicazioniManifestazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ListaComunicazioniResoconti;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ResocontoOperazioniMassive;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.RigaComunicazioneDettagliata;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.comunicazioni.massive.IMercatipresenzeDMassiveDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.comunicazioni.massive.IMercatipresenzeTMassiveDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneMassivaModel;
import it.gruppoinit.pal.gp.core.utils.ICurrentDateService;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;

@Service
public class ComunicazioniManifestazioniServiceImpl implements IComunicazioniManifestazioniService {

    private ICreazioneMassiveTestataService creazioneMassiveTestataService;
    private IComunicazioniMassiveDAO comunicazioniMassiveDAO;
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private IWorkFlowComunicazioniManifestazioniService workFlowComunicazioniManifestazioniService;
    private IMercatipresenzeTMassiveDAO mercatipresenzeTMassiveDAO;
    private IMercatipresenzeDMassiveDAO mercatipresenzeDMassiveDAO;
    private IConfigurazioneComunicazioneService configurazioneComunicazioneService;
    private ICurrentDateService currentDateService;
    private DocumentiDaFirmareDAO documentiDaFirmareDAO;
    private CreazioneComunicazioneManifestazioniFactory creazioneComunicazioneManifestazioniFactory;
    private IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO;

    @Autowired
    public void setMercatipresenzeDMassiveDAO(IMercatipresenzeDMassiveDAO mercatipresenzeDMassiveDAO) {

	this.mercatipresenzeDMassiveDAO = mercatipresenzeDMassiveDAO;
    }

    @Autowired
    public void setCreazioneComunicazioneManifestazioniFactory(
	    CreazioneComunicazioneManifestazioniFactory creazioneComunicazioneManifestazioniFactory) {

	this.creazioneComunicazioneManifestazioniFactory = creazioneComunicazioneManifestazioniFactory;
    }

    @Autowired
    public void setCreazioneMassiveTestataService(ICreazioneMassiveTestataService creazioneMassiveTestataService) {

	this.creazioneMassiveTestataService = creazioneMassiveTestataService;
    }

    @Autowired
    public void setComunicazioniMassiveDAO(IComunicazioniMassiveDAO comunicazioniMassiveDAO) {

	this.comunicazioniMassiveDAO = comunicazioniMassiveDAO;
    }

    @Autowired
    public void setComunicazioniMassiveDettaglioDAO(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO) {

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
    }

    @Autowired
    public void setWorkFlowComunicazioniManifestazioniService(
	    IWorkFlowComunicazioniManifestazioniService workFlowComunicazioniManifestazioniService) {

	this.workFlowComunicazioniManifestazioniService = workFlowComunicazioniManifestazioniService;
    }

    @Autowired
    public void setMercatipresenzeTMassiveDAO(IMercatipresenzeTMassiveDAO mercatipresenzeTMassiveDAO) {

	this.mercatipresenzeTMassiveDAO = mercatipresenzeTMassiveDAO;
    }

    @Autowired
    public void setConfigurazioneComunicazioneService(IConfigurazioneComunicazioneService configurazioneComunicazioneService) {

	this.configurazioneComunicazioneService = configurazioneComunicazioneService;
    }

    @Autowired
    public void setCurrentDateService(ICurrentDateService currentDateService) {

	this.currentDateService = currentDateService;
    }

    @Autowired
    public void setDocumentiDaFirmareDAO(DocumentiDaFirmareDAO documentiDaFirmareDAO) {

	this.documentiDaFirmareDAO = documentiDaFirmareDAO;
    }

    @Autowired
    public void setAppIoCodaMassiveDDAO(IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO) {

	this.appIoCodaMassiveDDAO = appIoCodaMassiveDDAO;
    }

    @Override
    public int creaNuovaComunicazione(ConfigurazioneComunicazioniManifestazioni configurazione) {

	ICreazioneComunicazioneManifestazioni i = creazioneComunicazioneManifestazioniFactory.getImplementation(configurazione);
	return i.creaNuovaComunicazione(configurazione);
    }

    @Override
    public void elabora() {

	//1. Recupero le massive da elaborare ( non in stato concluso )
	List<Integer> idTestate = this.mercatipresenzeTMassiveDAO
		.findIdComunicazioniNonCompletate(this.workFlowComunicazioniManifestazioniService.getStatoConclusivo().name());
	for (Integer idTestata : idTestate) {
	    //2. Per ognuna avvio l'elaborazione
	    this.elabora(idTestata);
	}
    }

    @Override
    public void elabora(int idTestata) {

	ConfigurazioneComunicazioniManifestazioni configurazione = new ConfigurazioneComunicazioniManifestazioni();
	this.configurazioneComunicazioneService.getById(idTestata, configurazione);
	List<MassiveDettaglio> righe = this.comunicazioniMassiveDettaglioDAO.getRigheByIdTestata(idTestata,
		this.workFlowComunicazioniManifestazioniService.getStatoConclusivo().name());
	for (MassiveDettaglio riga : righe) {
	    this.workFlowComunicazioniManifestazioniService.elabora(riga.getId().getCodice(), configurazione);
	}
    }

    @Override
    public ComunicazioneMassivaModel getComunicazioneByIdTestata(int idTestata) {

	return ComunicazioneMassivaModel.fromMassiveTestata(this.comunicazioniMassiveDAO.getTestataById(idTestata),
		this.workFlowComunicazioniManifestazioniService, appIoCodaMassiveDDAO);
    }

    @Override
    public ComunicazioneMassivaGenModel getComunicazioneByIdGenTestata(int idTestata) {

	return ComunicazioneMassivaGenModel.fromMassiveTestata(this.comunicazioniMassiveDAO.getTestataById(idTestata),
		this.workFlowComunicazioniManifestazioniService, appIoCodaMassiveDDAO);
    }

    @Override
    public void elaboraRiga(int idRiga) {

	MassiveDettaglio riga = this.comunicazioniMassiveDettaglioDAO.getById(idRiga);
	ConfigurazioneComunicazioniManifestazioni configurazione = new ConfigurazioneComunicazioniManifestazioni();
	this.configurazioneComunicazioneService.getById(riga.getMassiveTestata().getId().getCodice(), configurazione);
	this.workFlowComunicazioniManifestazioniService.elabora(riga.getId().getCodice(), configurazione);
    }

    @SuppressWarnings({ "rawtypes" })
    @Override
    public List<ListaComunicazioniResoconti> creaListaTestata(Integer idMercato) {

	// recupero tutte le testate relative al mercato	
	List<Integer> listIdTestate = this.creazioneMassiveTestataService.findByIdMercato(idMercato);
	List<ListaComunicazioniResoconti> comunicazioniResoconti = new ArrayList<ListaComunicazioniResoconti>();
	for (Integer idTestata : listIdTestate) {
	    comunicazioniResoconti.add(this.getResoconto(idTestata));
	}
	return comunicazioniResoconti;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ListaComunicazioniResoconti getResoconto(Integer idTestata) {

	MassiveTestata mt = this.comunicazioniMassiveDAO.getTestataById(idTestata);
	//creo ListaComunicazioniResoconti e setto le varie proprietà
	ListaComunicazioniResoconti listaComunicazioniResoconti = new ListaComunicazioniResoconti(
		this.workFlowComunicazioniManifestazioniService.getStatoConclusivo());
	listaComunicazioniResoconti.setId(idTestata);
	listaComunicazioniResoconti.setDescrizione(mt.getDescrizione());
	listaComunicazioniResoconti.setData(mt.getDataComunicazione());
	// creo ResocontoOperazioniMassive
	List<ResocontoOperazioniMassive> listRom = this.creazioneMassiveTestataService.findResocontoOperazioniMassiveById(idTestata);
	//setto la lista nel resoconto
	listaComunicazioniResoconti.setResocontoOperazioniMassive(listRom);
	return listaComunicazioniResoconti;
    }

    @Override
    public RigaComunicazioneDettagliata getRigaDettagliata(int idRiga) {

	throw new NotImplementedException();
    }

    @Override
    public void eliminaMassiva(int idTestata, Responsabili operatore) {

	MercatipresenzeTMassive presenza = this.mercatipresenzeTMassiveDAO.findByIdTestata(idTestata);
	String msgCancellazione = "##eliminaMassiva_manifestazioni## L'operatore " + operatore + " ha eliminato la massiva con codice " + idTestata +
				  " della giornata di calendario " + presenza.getMercatipresenzeT().getId().getCodice();
	LoggerCancellazioni.log(msgCancellazione);
	this.comunicazioniMassiveDAO.eliminaMassiva(idTestata, operatore.getId().getCodice(), this.currentDateService.getCurrentDate());
	List<Integer> documentiDaFirmarePerIdTestata = this.comunicazioniMassiveDettaglioDAO.getDocumentiDaFirmarePerIdTestata(idTestata);
	this.comunicazioniMassiveDettaglioDAO.eliminaMassiveDocDaFirmareByIdDocDaFirmare(documentiDaFirmarePerIdTestata);
	this.documentiDaFirmareDAO.eliminaDocDaFirmare(documentiDaFirmarePerIdTestata);
	//cancellazione fisica
	this.mercatipresenzeTMassiveDAO.deleteByIdTestata(idTestata);
    }

    @Override
    public boolean exists(Integer idTestata) {

	return this.mercatipresenzeTMassiveDAO.exists(idTestata);
    }

    @Override
    public List<ISoftwareComuneData> getSoftwareComuneFromIdDettaglioComunicazione(int idDettaglioComunicazione) {

	return this.mercatipresenzeTMassiveDAO.getSoftwareAndComuneDaDettaglioComunicazione(idDettaglioComunicazione);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public IWorkflowComunicazioniService getWorkFlowService() {

	return this.workFlowComunicazioniManifestazioniService;
    }

    @Override
    public boolean comunicazioneCancellabile(Integer idGiornata) {

	return this.mercatipresenzeTMassiveDAO.comunicazioneCancellabile(idGiornata,
		this.workFlowComunicazioniManifestazioniService.getStatoConclusivo().name());
    }

    @Override
    public void eliminaComunicazioniDellaGiornata(Integer idGiornata, Responsabili operatore) {

	List<MercatipresenzeTMassive> comunicazioni = this.mercatipresenzeTMassiveDAO.findByIdGiornata(idGiornata);
	for (MercatipresenzeTMassive comunicazione : comunicazioni) {
	    this.eliminaMassiva(comunicazione.getMassiveTestata().getId().getCodice(), operatore);
	}
    }

    @Override
    public boolean presentiComunicazioniPerTipologiaEGiornata(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione) {

	return mercatipresenzeTMassiveDAO.countComunicazioniPerTipologiaEGiornata(idGiornata, tipoComunicazione) > 0;
    }

    @Override
    public Integer recuperaPrimaComunicazionePerTipologiaEGiornata(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione) {

	return mercatipresenzeTMassiveDAO.recuperaPrimaComunicazionePerTipologiaEGiornata(idGiornata, tipoComunicazione);
    }

    @Override
    public boolean comunicazioneCancellabilePerTipologia(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione) {

	return mercatipresenzeTMassiveDAO.comunicazioneCancellabilePerTipologiaEGiornata(idGiornata, tipoComunicazione,
		this.workFlowComunicazioniManifestazioniService.getStatoConclusivo().name());
    }

    @Override
    public void eliminaComunicazioniDellaGiornataPerTipologia(Integer idGiornata, Responsabili operatore, TIPO_COMUNICAZIONE tipoComunicazione) {

	List<Integer> idMassiveTestatas = mercatipresenzeTMassiveDAO.recuperaComunicazioniPerTipologiaEGiornata(idGiornata, tipoComunicazione);
	for (Integer idMassiveTestata : idMassiveTestatas) {
	    this.eliminaMassiva(idMassiveTestata, operatore);
	}
    }

    @Override
    public boolean comunicazioneCancellabilePerTipologiaEPResenza(Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione,
	    Integer idMercatipresenzeD) {

	if (idGiornata == null || tipoComunicazione == null || idMercatipresenzeD == null) {
	    throw new IllegalArgumentException(
		    "i parametri Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione, Integer idMercatipresenzeD sono obbligatori");
	}
	return mercatipresenzeTMassiveDAO.comunicazioneCancellabilePerTipologiaEPResenza(idGiornata,
		this.workFlowComunicazioniManifestazioniService.getStatoConclusivo().name(), tipoComunicazione, idMercatipresenzeD,
		this.workFlowComunicazioniManifestazioniService.getStatoConclusivo().name());
    }

    @Override
    public void eliminaComunicazioniDellaGiornataPerTipologiaEPResenza(Integer idGiornata, Responsabili operatore,
	    TIPO_COMUNICAZIONE tipoComunicazione, Integer idMercatipresenzeD) {

	if (idGiornata == null || tipoComunicazione == null || idMercatipresenzeD == null) {
	    throw new IllegalArgumentException(
		    "i parametri Integer idGiornata, TIPO_COMUNICAZIONE tipoComunicazione, Integer idMercatipresenzeD sono obbligatori");
	    // il metodo comunicazioneCancellabilePerTipologiaEPResenza accetta idPresenzaNulla 
	}
	List<Integer> idMassiveTestatas = mercatipresenzeTMassiveDAO.recuperaComunicazioniPerTipologiaEGiornataEPresenza(idGiornata,
		tipoComunicazione, idMercatipresenzeD);
	for (Integer idMassiveTestata : idMassiveTestatas) {
	    this.eliminaMassivaPresenza(idMassiveTestata, operatore, idMercatipresenzeD);
	}
    }

    private void eliminaMassivaPresenza(Integer idTestata, Responsabili operatore, Integer idMercatipresenzeD) {

	MercatipresenzeTMassive presenza = this.mercatipresenzeTMassiveDAO.findByIdTestata(idTestata);
	String msgCancellazione = "##eliminaMassiva_manifestazioni## L'operatore " + operatore + " ha eliminato la presenza " + idMercatipresenzeD +
				  " della massiva con codice " + idTestata + " della giornata di calendario " +
				  presenza.getMercatipresenzeT().getId().getCodice();
	// cancellazione fisica
	// this.mercatipresenzeTMassiveDAO.deleteByIdTestata(idTestata);
	List<Integer> dettagli = this.mercatipresenzeTMassiveDAO.findIdMercatiPresenzeDMassiveByTestataAndPresenza(idTestata, idMercatipresenzeD);
	for (Integer id : dettagli) {
	    MercatipresenzeDMassive m = this.mercatipresenzeDMassiveDAO.getById(MercatipresenzeDMassive.class, id);
	    Integer idDettaglio = m.getMassiveDettaglio().getId().getCodice();
	    this.mercatipresenzeDMassiveDAO.delete(m);
	    List<Integer> documentiDaFirmarePerIdTestataAndPresenzaAndDettaglio = this.comunicazioniMassiveDettaglioDAO
		    .getDocumentiDaFirmarePerIdTestataAndDettaglio(idTestata, idDettaglio);
	    this.comunicazioniMassiveDettaglioDAO.eliminaMassiveDocDaFirmareByIdDocDaFirmare(documentiDaFirmarePerIdTestataAndPresenzaAndDettaglio);
	    this.documentiDaFirmareDAO.eliminaDocDaFirmare(documentiDaFirmarePerIdTestataAndPresenzaAndDettaglio);
	    this.comunicazioniMassiveDettaglioDAO.eliminaMassivaDettaglio(idDettaglio);
	}
	LoggerCancellazioni.log(msgCancellazione);
    }
}
