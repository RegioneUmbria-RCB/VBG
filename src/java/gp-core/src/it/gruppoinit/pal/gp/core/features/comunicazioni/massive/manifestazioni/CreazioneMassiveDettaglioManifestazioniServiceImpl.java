package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDestinatari;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeDMassive;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.SceltaTipoMailAnagrafeResolver;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.model.DettaglioPresenzaComunicazioneModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.comunicazioni.massive.IMercatipresenzeDMassiveDAO;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.utils.ICurrentDateService;

@Service
public class CreazioneMassiveDettaglioManifestazioniServiceImpl implements ICreazioneMassiveDettaglioManifestazioniService {

    private MercatipresenzeDService mercatipresenzeDService;
    private ICurrentDateService currentDateService;
    private IComunicazioniMassiveDAO comunicazioniMassiveDAO;
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private AnagrafeService anagrafeService;
    private IMercatipresenzeDMassiveDAO mercatipresenzeDMassiveDAO;

    @Autowired
    public void setMercatipresenzeDService(MercatipresenzeDService mercatipresenzeDService) {

	this.mercatipresenzeDService = mercatipresenzeDService;
    }

    @Autowired
    public void setCurrentDateService(ICurrentDateService currentDateService) {

	this.currentDateService = currentDateService;
    }

    @Autowired
    public void setComunicazioniMassiveDettaglioDAO(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO) {

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setComunicazioniMassiveDAO(IComunicazioniMassiveDAO comunicazioniMassiveDAO) {

	this.comunicazioniMassiveDAO = comunicazioniMassiveDAO;
    }

    @Autowired
    public void setMercatipresenzeDMassiveDAO(IMercatipresenzeDMassiveDAO mercatipresenzeDMassiveDAO) {

	this.mercatipresenzeDMassiveDAO = mercatipresenzeDMassiveDAO;
    }

    @Override
    public void collegaRigheAComunicazioni(int idTestata, ConfigurazioneComunicazioniManifestazioni configurazione) {

	int idGiornata = configurazione.getIdGiornata();
	//1. Recupero la testata
	MassiveTestata testata = this.comunicazioniMassiveDAO.getTestataById(idTestata);
	//2. Recupero tutte le presenze che hanno avuto uno scalo dal borsellino
	List<DettaglioPresenzaComunicazioneModel> presenze = this.mercatipresenzeDService.findPresenzeScalateDalBorsellino(idGiornata);
	//3. Inserisco i dettagli per la comunicazione
	this.popolaMassivaDettaglio(presenze, testata, configurazione);
    }

    private void popolaMassivaDettaglio(List<DettaglioPresenzaComunicazioneModel> presenze, MassiveTestata testata,
	    ConfigurazioneComunicazioniManifestazioni configurazione) {

	for (DettaglioPresenzaComunicazioneModel presenza : presenze) {
	    //1. Massivedettaglio
	    MassiveDettaglio dettaglio = new MassiveDettaglio();
	    dettaglio.setMassiveTestata(testata);
	    dettaglio.setUltimoStatoCompletato(StatoComunicazioniManifestazioniEnum.PRONTA_PER_ELABORAZIONE.name());
	    dettaglio.setUltimoStatoData(currentDateService.getCurrentDate());
	    comunicazioniMassiveDettaglioDAO.insert(dettaglio);
	    //2. MassiveDettDestinatari
	    MassiveDettDestinatari dest = new MassiveDettDestinatari();
	    dest.setMassiveDettaglio(dettaglio);
	    dest.setAnagrafe(anagrafeService.findById(new PkId(presenza.getCodiceAnagrafe())));
	    dest.setMailDestinatario(new SceltaTipoMailAnagrafeResolver(configurazione.getSceltaTipoMailAnagrafe()).getIndirizzo(presenza.getPec(),
		    presenza.getEmail()));
	    comunicazioniMassiveDAO.saveEntity(dest);
	    dettaglio.setDestinatari(dest);
	    //3. Mercatipresenze_d_massive
	    MercatipresenzeDMassive massiva = new MercatipresenzeDMassive();
	    massiva.setMassiveDettaglio(dettaglio);
	    massiva.setMercatipresenzeD(new MercatipresenzeD(presenza.getIdPresenza()));
	    this.mercatipresenzeDMassiveDAO.insert(massiva);
	}
    }

    @Override
    public void collegaRigaAComunicazione(ConfigurazioneComunicazioniManifestazioni configurazione, int idPresenza, int idTestata) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	List<MercatipresenzeDMassive> presenze = mercatipresenzeDMassiveDAO.findByFilterTable(ft, 0, 1);
	if (!presenze.isEmpty()) {
	    // ci sono presenze già registrate non faccio niente vuol dire che già è stata aggiunta
	}
	// aggiungo la presenza alla comunicazione
	MercatipresenzeD pres = this.mercatipresenzeDService.findById(new PkId(idPresenza));
	DettaglioPresenzaComunicazioneModel model = DettaglioPresenzaComunicazioneModel.forOccupantefromMercatipresenzeD(pres);
	if (model != null) {
	    MassiveTestata testata = this.comunicazioniMassiveDAO.getTestataById(idTestata);
	    List<DettaglioPresenzaComunicazioneModel> list = new ArrayList<DettaglioPresenzaComunicazioneModel>(1);
	    list.add(model);
	    this.popolaMassivaDettaglio(list, testata, configurazione);
	}
    }
}
