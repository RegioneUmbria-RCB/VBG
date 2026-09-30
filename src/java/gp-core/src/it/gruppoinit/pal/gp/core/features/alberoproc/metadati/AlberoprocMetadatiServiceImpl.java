package it.gruppoinit.pal.gp.core.features.alberoproc.metadati;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AlberoprocMetadati;

@Service
public class AlberoprocMetadatiServiceImpl implements AlberoprocMetadatiService {

    private AlberoprocMetadatiDAO alberoprocMetadatiDAO;

    @Autowired
    public void setAlberoprocMetadatiDAO(AlberoprocMetadatiDAO alberoprocMetadatiDAO) {

	this.alberoprocMetadatiDAO = alberoprocMetadatiDAO;
    }

    @Override
    public void insert(MetadatoAlberoproc metadato) {

	this.alberoprocMetadatiDAO.insert(metadato.getCodiceIntervento(), metadato.getChiave(), metadato.getValore());
    }

    @Override
    public ConfigurazioneMetadati findByAlberoproc(Integer scId) {

	Set<MetadatoAlberoproc> metadatiRamo = new HashSet<MetadatoAlberoproc>();
	Set<MetadatoAlberoproc> metadatiRamiPadre = new HashSet<MetadatoAlberoproc>();
	Set<String> chiaviPresenti = new HashSet<String>();
	Set<AlberoprocMetadati> alberoMetadati = this.alberoprocMetadatiDAO.findAllByIntervento(scId);
	for (AlberoprocMetadati alberoprocMetadati : alberoMetadati) {
	    Integer codiceIntervento = alberoprocMetadati.getId().getFkScid();
	    String descrizioneIntervento = alberoprocMetadati.getAlberoproc().getDescrizioneCompleta();
	    String chiave = alberoprocMetadati.getId().getChiave();
	    if (codiceIntervento.equals(scId)) {
		metadatiRamo.add(new MetadatoAlberoproc(codiceIntervento, descrizioneIntervento, chiave, alberoprocMetadati.getValore()));
		chiaviPresenti.add(chiave);
	    } else {
		if (!chiaviPresenti.contains(chiave)) {
		    metadatiRamiPadre.add(new MetadatoAlberoproc(codiceIntervento, descrizioneIntervento, chiave, alberoprocMetadati.getValore()));
		    chiaviPresenti.add(chiave);
		}
	    }
	}
	return new ConfigurazioneMetadati(metadatiRamo, metadatiRamiPadre);
    }

    @Override
    public void delete(Integer codiceInterventoProc, String chiave) {

	this.alberoprocMetadatiDAO.delete(codiceInterventoProc, chiave);
    }

    @Override
    public AlberoprocMetadati findByInterventoRicorsivoEChiave(Integer codiceInterventoProc, String chiave) {

	return this.alberoprocMetadatiDAO.findByInterventoRicorsivoEChiave(codiceInterventoProc, chiave);
    }

    @Override
    public List<AlberoprocMetadati> findAllByChiave(String chiave) {

	return this.alberoprocMetadatiDAO.findAllByChiave(chiave);
    }

    @Override
    public boolean metadatiPresenti(List<String> chiavi) {

	return this.alberoprocMetadatiDAO.metadatiPresenti(chiavi);
    }

    @Override
    public List<String> listaMetadatiConfigurabili() {

	AlberoprocMetadatiEnum[] metadati = AlberoprocMetadatiEnum.values();
	List<String> result = new ArrayList<String>(metadati.length);
	for (AlberoprocMetadatiEnum m : metadati) {
	    result.add(m.value());
	}
	return result;
    }

    @Override
    public String findValoreByInterventoRicorsivoEChiave(Integer codiceInterventoProc, String chiave) {

	AlberoprocMetadati apmd = this.alberoprocMetadatiDAO.findByInterventoRicorsivoEChiave(codiceInterventoProc, chiave);
	if (apmd != null) {
	    return apmd.getValore();
	}
	return null;
    }
}
