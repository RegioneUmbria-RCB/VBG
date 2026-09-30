package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeMassiveDIstanze;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoAllegatiDelDettaglioElaborati;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoMovimentiInseriti;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IComunicazioniMassiveGenDAO;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class GenMovimentiServiceImpl implements IWorkFlowStep<ConfigurazioniComunicazioneGen>{
    
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired 
    private IstanzeService istanzeService;
    @Autowired
    private TipiMovimentoService tipimovimentoService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private IComunicazioniMassiveGenDAO comunicazioniMassiveGenDAO;
    @Autowired
    private IEventPublisher publisher;
    @Autowired
    private
    GenGenerazioneAllegatiStepServiceImpl generazioneAllegatiStepServiceImpl;
    
    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione) {

	List<IstanzeMassiveDIstanze> istanzedettaglio = comunicazioniMassiveGenDAO.findIstanzeMassiveDIstanze(idDettaglioComunicazione);
	if(istanzedettaglio == null || istanzedettaglio.isEmpty()){
	    generazioneAllegatiStepServiceImpl.elabora(idDettaglioComunicazione, configurazione);
	    return;
	}
	
	if(!istanzedettaglio.get(0).getIstanzeMassiveD().getFlagMovimento()){
	    generazioneAllegatiStepServiceImpl.elabora(idDettaglioComunicazione, configurazione);
	    return;
	}
	
	Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	Tipimovimento tipomovimento = tipimovimentoService.findById(new TipimovimentoId(istanzedettaglio.get(0).getIstanzeMassiveD().getFktipimovimento()));
	Amministrazioni amministrazione = amministrazioniService.findById(new PkId(istanzedettaglio.get(0).getIstanzeMassiveD().getFkcodiceamministrazione()));
	
	for(IstanzeMassiveDIstanze dettaglioI : istanzedettaglio){
	    Istanze istanza = istanzeService.findById(new PkId(dettaglioI.getFkcodiceistanza()));
	    comunicazioniMassiveGenDAO.saveMovimento(dettaglioI, tipomovimento, amministrazione, responsabile, istanza);
	}
	
	publisher.publish(new EventoMovimentiInseriti(configurazione.getContesto(), idDettaglioComunicazione));

	
    }
}
