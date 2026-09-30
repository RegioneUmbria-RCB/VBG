package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTMassive;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.ICreazioneMassiveTestataService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.comunicazioni.massive.IMercatipresenzeTMassiveDAO;

@Component
public class CreazioneComunicazioneChiusuraGiornataServiceImpl implements ICreazioneComunicazioneManifestazioni {

    @Autowired
    private ICreazioneMassiveTestataService creazioneMassiveTestataService;
    @Autowired
    private IMercatipresenzeTMassiveDAO mercatipresenzeTMassiveDAO;
    @Autowired
    private ICreazioneMassiveDettaglioManifestazioniService creazioneMassiveDettaglioService;

    @Override
    public int creaNuovaComunicazione(ConfigurazioneComunicazioniManifestazioni configurazione) {

	int idTestata = this.creazioneMassiveTestataService.insert(configurazione);
	MercatipresenzeTMassive massiveT = new MercatipresenzeTMassive();
	massiveT.setMassiveTestata(new MassiveTestata(idTestata));
	massiveT.setMercatipresenzeT(new MercatipresenzeT(configurazione.getIdGiornata()));
	this.mercatipresenzeTMassiveDAO.insert(massiveT);
	this.creazioneMassiveDettaglioService.collegaRigheAComunicazioni(idTestata, configurazione);
	return idTestata;
    }
}
