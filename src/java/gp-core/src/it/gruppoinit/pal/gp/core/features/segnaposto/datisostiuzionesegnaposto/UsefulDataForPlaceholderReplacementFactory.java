package it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.IstanzemappaliDAO;
import it.gruppoinit.pal.gp.core.dao.IstanzerichiedentiDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeareeService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatisoftwareService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;

@Service
public class UsefulDataForPlaceholderReplacementFactory implements IUsefulDataForPlaceholderReplacementFactory {

    private MovimentiService movimentiService;
    private AmministrazioniService amministrazioniService;
    private ConfigurazioneService configurazioneservice;
    private IstanzestradarioService istanzeStradarioService;
    private IstanzeareeService istanzeAreeService;
    private IstanzemappaliDAO istanzeMappaliService;
    private AlberoprocService alberoprocService;
    private Dyn2CampiService dynCampiService;
    private ComuniassociatisoftwareService comuniAssociatiSoftwareService;
    private IstanzeprocedimentiService istanzeProcedimentiService;
    private IstanzerichiedentiDAO istanzeRichiedentiDao;
    //private IstanzecollegateService istanzecollegateService;
    //private DomandestcService domandestcService;
    private Istanzedyn2datiService istanzeDynDatiService;

    @Autowired
    public UsefulDataForPlaceholderReplacementFactory(MovimentiService movimentiService, AmministrazioniService amministrazioniService,
	    ConfigurazioneService configurazioneservice, IstanzestradarioService istanzeStradarioService, IstanzeareeService istanzeAreeService,
	    IstanzemappaliDAO istanzeMappaliService, AlberoprocService alberoprocService, Dyn2CampiService dynCampiService,
	    ComuniassociatisoftwareService comuniAssociatiSoftwareService, IstanzeprocedimentiService istanzeProcedimentiService,
	    IstanzerichiedentiDAO istanzeRichiedentiDao,
	    /*IstanzecollegateService istanzecollegateService, DomandestcService domandestcService,*/ Istanzedyn2datiService istanzeDynDatiService) {

	super();
	this.movimentiService = movimentiService;
	this.amministrazioniService = amministrazioniService;
	this.configurazioneservice = configurazioneservice;
	this.istanzeStradarioService = istanzeStradarioService;
	this.istanzeAreeService = istanzeAreeService;
	this.istanzeMappaliService = istanzeMappaliService;
	this.alberoprocService = alberoprocService;
	this.dynCampiService = dynCampiService;
	this.comuniAssociatiSoftwareService = comuniAssociatiSoftwareService;
	this.istanzeProcedimentiService = istanzeProcedimentiService;
	this.istanzeRichiedentiDao = istanzeRichiedentiDao;
	//this.istanzecollegateService = istanzecollegateService;
	//this.domandestcService = domandestcService;
	this.istanzeDynDatiService = istanzeDynDatiService;
    }

    @Override
    public IUsefulDataForPlaceholderReplacement create(Istanze istanza, Movimenti movimento) {

	return new UsefulDataForPlaceholderReplacement(movimentiService, amministrazioniService, configurazioneservice, istanzeStradarioService,
		istanzeAreeService, istanzeMappaliService, alberoprocService, dynCampiService, comuniAssociatiSoftwareService,
		istanzeProcedimentiService, istanzeRichiedentiDao, /*istanzecollegateService,
								   domandestcService,*/ istanzeDynDatiService, istanza, movimento);
    }
}
