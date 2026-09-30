package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AlberoprocMetadati;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.features.alberoproc.metadati.AlberoprocMetadatiService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione.VerticalizzazioneWSAttiService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione.VerticalizzazioneWSAttiServiceImpl;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;

@Service
public class ConfigurazioneResolverServiceImpl implements IConfigurazioneResolverService {

    private VerticalizzazioneWSAttiService verticalizzazioniService;
    private AlberoprocMetadatiService metadatiService;
    private TipiMovimentoService tipiMovimentoService;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioneWSAttiService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setMetadatiService(AlberoprocMetadatiService metadatiService) {

	this.metadatiService = metadatiService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Override
    public ConfigurazioneWSAtti getConfigurazione(Integer idAlberoProc) {

	ConfigurazioneWSAtti config = new ConfigurazioneWSAtti();
	config.setClassifica(this.getClassifica(idAlberoProc));
	config.setCodiceDirigente(this.getCodiceDirigente(idAlberoProc));
	config.setCodiceProponente(this.getCodiceProponente(idAlberoProc));
	config.setRuolo(this.getRuolo(idAlberoProc));
	config.setTrattamento(this.getTrattamento(idAlberoProc));
	config.setTipiMovimento(this.getTipiMovimento(idAlberoProc));
	config.setMovimentoAttoCompletato(this.getMovimentoAttoCompletato(idAlberoProc));
	return config;
    }

    private Set<String> getTipiMovimento(Integer idAlberoProc) {

	String tipiMovimento = this.getString(VerticalizzazioneWSAttiServiceImpl.parMovPrecompilaDocAut, idAlberoProc,
		this.verticalizzazioniService.getMovPrecompilaDocAut());
	if (StringUtils.isBlank(tipiMovimento)) {
	    return new HashSet<String>();
	}
	String[] lista = tipiMovimento.split(",");
	Set<String> elenco = new HashSet<String>();
	for (String tipoMovimento : lista) {
	    elenco.add(tipoMovimento.trim());
	}
	return elenco;
    }

    private String getTrattamento(Integer idAlberoProc) {

	return this.getString(VerticalizzazioneWSAttiServiceImpl.parCodiceTrattamento, idAlberoProc, this.verticalizzazioniService.getTrattamento());
    }

    private String getRuolo(Integer idAlberoProc) {

	return this.getString(VerticalizzazioneWSAttiServiceImpl.parRuolo, idAlberoProc, this.verticalizzazioniService.getRuolo());
    }

    private String getCodiceProponente(Integer idAlberoProc) {

	return this.getString(VerticalizzazioneWSAttiServiceImpl.parCodiceProponente, idAlberoProc,
		this.verticalizzazioniService.getCodiceProponente());
    }

    private String getCodiceDirigente(Integer idAlberoProc) {

	return this.getString(VerticalizzazioneWSAttiServiceImpl.parCodiceDirigente, idAlberoProc,
		this.verticalizzazioniService.getCodiceDirigente());
    }

    private String getClassifica(Integer idAlberoProc) {

	return this.getString(VerticalizzazioneWSAttiServiceImpl.parClassifica, idAlberoProc, this.verticalizzazioniService.getClassifica());
    }

    private String getString(String parametro, Integer idAlberoProc, String defaultValue) {

	AlberoprocMetadati retVal = null;
	//1. Verifica presenza metadato nel formato VERTICALIZZAZIONE.PARAMETRO
	String chiave = VerticalizzazioneWSAttiServiceImpl.nomeVerticalizzazione + "." + parametro;
	retVal = this.metadatiService.findByInterventoRicorsivoEChiave(idAlberoProc, chiave);
	if (retVal != null) {
	    return retVal.getValore();
	}
	return defaultValue;
    }

    private Tipimovimento getMovimentoAttoCompletato(Integer idAlberoProc) {

	String tipoMovimento = this.getString(VerticalizzazioneWSAttiServiceImpl.parMovAttoCompletato, idAlberoProc,
		this.verticalizzazioniService.getMovimentoCompletamentoDetermina());
	if (StringUtils.isBlank(tipoMovimento)) {
	    return null;
	}
	return this.tipiMovimentoService.findById(new TipimovimentoId(tipoMovimento));
    }
}
