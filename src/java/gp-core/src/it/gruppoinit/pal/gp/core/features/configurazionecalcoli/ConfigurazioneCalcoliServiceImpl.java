package it.gruppoinit.pal.gp.core.features.configurazionecalcoli;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneCalcoli;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class ConfigurazioneCalcoliServiceImpl extends BaseServiceImpl<ConfigurazioneCalcoli, PkId> implements IConfigurazioneCalcoliService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurazioneCalcoliServiceImpl.class);
    private IConfigurazioneCalcoliDAO configurazioneCalcoliDAO;
    private OggettiService oggettiService;

    @Autowired
    public void setConfigurazioneCalcoliDAO(IConfigurazioneCalcoliDAO configurazioneCalcoliDAO) {

	this.configurazioneCalcoliDAO = configurazioneCalcoliDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<ConfigurazioneCalcoli> getEntityClass() {

	return ConfigurazioneCalcoli.class;
    }

    @Override
    public void insert(ConfigurazioneCalcoli entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(ConfigurazioneCalcoli entity) {

	this.configurazioneCalcoliDAO.update(entity);
    }

    @Override
    public void delete(ConfigurazioneCalcoli entity) {

	this.configurazioneCalcoliDAO.delete(entity);
    }

    @Override
    public List<ConfigurazioneCalcoli> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public ConfigurazioneCalcoli findById(PkId id) {

	return this.configurazioneCalcoliDAO.findById(id);
    }

    @Override
    public List<CalcoloListItem> findAll() {

	List<ConfigurazioneCalcoli> elenco = this.configurazioneCalcoliDAO.findAll(null, null, DAOEnum.FIND_BY_IDCOMUNE, "descrizione",
		DAOOrderTypeEnum.ASC);
	List<CalcoloListItem> calcoli = new ArrayList<CalcoloListItem>();
	for (ConfigurazioneCalcoli dettaglio : elenco) {
	    calcoli.add(CalcoloListItem.fromConfigurazioneCalcoli(dettaglio));
	}
	return calcoli;
    }

    @Override
    public void deleteById(Integer codice) {

	logger.debug("Inizio cancellazione configurazione {}", codice);
	ConfigurazioneCalcoli calcolo = this.findById(new PkId(codice));
	PkId idJsonConfigurazione = calcolo.getJsonConfigurazione().getId();
	PkId idJsonMatrice = calcolo.getJsonMatrice().getId();
	this.delete(calcolo);
	if (idJsonConfigurazione != null && idJsonConfigurazione.getCodice() != null) {
	    logger.debug("Inizio cancellazione json {} della configurazione {}", idJsonConfigurazione, codice);
	    Oggetti oggettoDaCancellare = oggettiService.findById(idJsonConfigurazione);
	    oggettiService.delete(oggettoDaCancellare);
	    logger.debug("Fine cancellazione json {} della configurazione {}", idJsonConfigurazione, codice);
	}
	if (idJsonMatrice != null && idJsonMatrice.getCodice() != null) {
	    logger.debug("Inizio cancellazione json {} della configurazione {}", idJsonMatrice, codice);
	    Oggetti oggettoDaCancellare = oggettiService.findById(idJsonMatrice);
	    oggettiService.delete(oggettoDaCancellare);
	    logger.debug("Fine cancellazione json {} della configurazione {}", idJsonMatrice, codice);
	}
	logger.debug("Fine cancellazione configurazione {}", codice);
    }

    @Override
    public void aggiornaDescrizione(Integer codice, String descrizione) {

	ConfigurazioneCalcoli calcolo = this.findById(new PkId(codice));
	calcolo.setDescrizione(descrizione);
	this.update(calcolo);
    }
}
