package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ValoriLivelloServizio;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.service.LivelloServizioService;
import it.gruppoinit.pal.gp.core.service.MercatiCfgContiService;

@Service
public class RecuperaInformazioniGiornataServiceImpl implements IRecuperaInformazioniGiornataService {

    @Autowired
    private LivelloServizioService livelloServizioService;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private MercatiCfgContiService mercatiCfgContiService;
    @Autowired
    private IVerticalizzazioneComportamentiMercatiService verticalizzazioneComportamentiMercatiService;

    @Override
    public List<ValoriLivelloServizio> livelliDiServizioConfiguratiPerGiornataEIdPosteggio(Integer idGiornata, Integer idPosteggio) {

	return this.mercatipresenzeDService.livelliDiServizioConfiguratiPerIdGiornataEIdPosteggio(idGiornata, idPosteggio);
    }

    @Override
    public List<LivelloServizio> livelliDiServizioElencoCompletoDisponibili() {

	return this.livelloServizioService.findServiziDisponibili();
    }

    @Override
    public Integer getIdContoAttivoDaFormulaEIdGiornata(MercatiFormuleCalcolo formula, Integer idGiornata) {

	MercatipresenzeT giornata = this.mercatipresenzeTService.findById(new PkId(idGiornata));
	Conti conto = formula.getContoAttivo(giornata.getDataRegistrazione()).getConti();
	return (conto != null) ? conto.getId().getCodice() : null;
    }

    @Override
    public BigDecimal getCoefficienteMercato(Integer idConto, Integer idGiornata, Integer idPosteggio) {

	return this.mercatiCfgContiService.getCoefficienteMercatoByContoGiornataEPosteggio(idConto, idGiornata, idPosteggio);
    }

    private Map<String, String> codistatPerAliasSoftwareMap = new HashMap<String, String>();

    private String getKeyCodistatPerAliasSoftwareMap() {

	return ORMHelper.getIdcomune() + "-" + ORMHelper.getSoftware();
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	codistatPerAliasSoftwareMap = new HashMap<String, String>();
    }

    @Override
    public boolean isBattitore(String codiceIstatPresenza) {

	String cIstatBattitore = codistatPerAliasSoftwareMap.get(getKeyCodistatPerAliasSoftwareMap());
	if (StringUtils.isBlank(cIstatBattitore)) {
	    cIstatBattitore = StringUtils.defaultString(verticalizzazioneComportamentiMercatiService.codiceIstatBattitori(), "n0n_d4f1n1t0");
	    codistatPerAliasSoftwareMap.put(getKeyCodistatPerAliasSoftwareMap(), cIstatBattitore);
	}
	return cIstatBattitore.indexOf(StringUtils.defaultString(codiceIstatPresenza, "vu0t0")) >= 0;
    }
}