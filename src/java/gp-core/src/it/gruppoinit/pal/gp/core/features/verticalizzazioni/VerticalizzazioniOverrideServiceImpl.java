package it.gruppoinit.pal.gp.core.features.verticalizzazioni;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AlberoprocMetadati;
import it.gruppoinit.pal.gp.core.features.alberoproc.metadati.AlberoprocMetadatiService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione.VerticalizzazioneWSAttiServiceImpl;

@Service
public class VerticalizzazioniOverrideServiceImpl implements IVerticalizzazioniOverrideService {

    private AlberoprocMetadatiService alberoprocMetadatiService;

    @Autowired
    public void setAlberoprocMetadatiService(AlberoprocMetadatiService alberoprocMetadatiService) {

	this.alberoprocMetadatiService = alberoprocMetadatiService;
    }

    @Override
    public Map<String, IVerticalizzazioneConOverride> findVerticalizzazioniConOverride() {

	TreeMap<String, IVerticalizzazioneConOverride> elenco = new TreeMap<String, IVerticalizzazioneConOverride>();
	elenco.put(VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione, null);
	return elenco;
    }

    @Override
    public List<OverrideDelParametro> findOverride(String modulo, String comune, String parametro) {

	List<OverrideDelParametro> elenco = new ArrayList<OverrideDelParametro>();
	//1. Override da albero proc nel formato MODULO.COMUNE.PARAMETRO
	String chiave = modulo + "." + comune + "." + parametro;
	List<AlberoprocMetadati> metadatiAlbero = this.alberoprocMetadatiService.findAllByChiave(chiave);
	elenco.addAll(this.convert(metadatiAlbero));
	//2. Override da albero proc nel formato MODULO.PARAMETRO
	chiave = modulo + "." + parametro;
	metadatiAlbero = this.alberoprocMetadatiService.findAllByChiave(chiave);
	elenco.addAll(this.convert(metadatiAlbero));
	return elenco;
    }

    private List<OverrideDelParametro> convert(List<AlberoprocMetadati> metadatiAlbero) {

	List<OverrideDelParametro> elenco = new ArrayList<OverrideDelParametro>();
	for (AlberoprocMetadati metadati : metadatiAlbero) {
	    elenco.add(OverrideDelParametro.fromAlberoprocMetadati(metadati));
	}
	return elenco;
    }

    @Override
    public boolean parametroConOvverride(String modulo, String comune, String parametro) {

	List<String> chiavi = new ArrayList<String>();
	chiavi.add(modulo + "." + comune + "." + parametro);
	chiavi.add(modulo + "." + parametro);
	return this.alberoprocMetadatiService.metadatiPresenti(chiavi);
    }
}
