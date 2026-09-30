package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BollCfgTipoMetadati;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;

public class FakeBollCfgTipoMetadatiService implements BollCfgTipoMetadatiService {

    private List<MetadatoBollettazione> elencoMetadati = null;

    public void aggiungiMetadato(String chiave, String valore) {

	if (this.elencoMetadati == null) {
	    this.elencoMetadati = new ArrayList<MetadatoBollettazione>();
	}
	MetadatoBollettazione metadato = new MetadatoBollettazione();
	metadato.setChiave(chiave);
	metadato.setValore(valore);
	elencoMetadati.add(metadato);
    }

    @Override
    public List<MetadatiBean> findMetadatiConfigurati(Integer codiceBollcfgTipo) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void updateMetadato(UpdateMetadatoRequest jsonRequest) {

	// TODO Auto-generated method stub
    }

    @Override
    public void deleteMetadato(DeleteMetadatoRequest jsonRequest) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<MetadatoBollettazione> elencoMetadati(Integer codiceBollcfgTipo) {

	return this.elencoMetadati;
    }

    @Override
    public int insertMetadato(InsertMetadatoRequest jsonRequest) {

	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public List<BollCfgTipoMetadati> findByBollCfgTipo(Integer bollcfgTipo, Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void delete(BollCfgTipoMetadati entity) {

	// TODO Auto-generated method stub
    }
}
