package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Massive;
import it.gruppoinit.pal.gp.core.domain.Dyn2MassiveFiltri;
import it.gruppoinit.pal.gp.core.domain.Dyn2Massiveschede;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.RigaElaborazioneModel;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.TestataDettagliataModel;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.TestataModel;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.ws.client.ElaborazioneMassivaWsClient;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.ws.model.EsitoElaborazioneMassivaSchede;
import it.gruppoinit.pal.gp.core.service.AlberoprocRuoliService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class ElaborazioneMassivaServiceImpl implements IElaborazioneMassivaService {

    @Autowired
    private AlberoprocRuoliService alberoprocRuoliService;
    @Autowired
    private IElaborazioneMassivaDAO elaborazioneMassivaDAO;

    @Override
    public List<TestataModel> findAll() {

	return elaborazioneMassivaDAO.findAll();
    }

    @Override
    public TestataDettagliataModel findById(int idTestata) {

	Dyn2Massive m = elaborazioneMassivaDAO.findMassivaById(idTestata);
	if (m == null) {
	    throw new BusinessValidationException("Elaborazione non trovata con id " + idTestata);
	}
	List<Dyn2Massiveschede> schedeByElaborazione = elaborazioneMassivaDAO.getSchedeByElaborazione(idTestata);
	List<Dyn2MassiveFiltri> filtriByElaborazione = elaborazioneMassivaDAO.getFiltriByElaborazione(idTestata);
	Set<RigaElaborazioneModel> istanzeByElaborazione = elaborazioneMassivaDAO.getRigheForElaborazione(idTestata, null, null);
	return TestataDettagliataModel.build(m, schedeByElaborazione, filtriByElaborazione, istanzeByElaborazione);
    }

    @Override
    public int creaElaborazione(CreaTestataRequest request) {

	return elaborazioneMassivaDAO.creaElaborazione(request);
    }

    @Override
    public EsitoElaborazioneMassivaSchede elabora(int idTestata) {

	return new ElaborazioneMassivaWsClient().elabora(ORMHelper.getToken(), idTestata);
    }

    @Override
    public List<Integer> findRighePerElaborazione(CreaTestataRequest request) {

	return elaborazioneMassivaDAO.findRighePerElaborazione(request);
    }

    @Override
    public void eliminaElaborazioneMassiveRiga(Integer idElaborazione) {

	elaborazioneMassivaDAO.eliminaElaborazioniMassiveRiga(idElaborazione);
    }

    @Override
    public List<IdentificativoDescrizioneBean> findSchedeDinamiche(CreaTestataRequest filtri) {

	return elaborazioneMassivaDAO.findSchedeDinamiche(filtri);
    }

    @Override
    public List<TestataModel> findAllByRuoliResponsabile(Integer codiceResponsabile) {

	List<TestataModel> result = new ArrayList<TestataModel>();
	List<TestataModel> list = elaborazioneMassivaDAO.findAll();
	Set<Integer> vociAlberoPerRuoli = alberoprocRuoliService.trovaVociPerRuoliDelResponsabile(codiceResponsabile);
	for (TestataModel tm : list) {
	    List<Dyn2MassiveFiltri> codiciIntervento = elaborazioneMassivaDAO.getFiltriByElaborazione(tm.getId(),
		    ElaborazioniMassiveFiltriEnum.CODICI_INTERVENTO);
	    boolean inserisci = true;
	    if (!codiciIntervento.isEmpty()) {
		// Ritorna una stringa tipo 123,124,125
		inserisci = responsabileHaPermessiSuVociAlbero(vociAlberoPerRuoli,
			StringUtils.defaultString(codiciIntervento.get(0).getValore()).trim());
	    }
	    if (inserisci) {
		result.add(tm);
	    }
	}
	return result;
    }

    private boolean responsabileHaPermessiSuVociAlbero(Set<Integer> vociAlbero, String codiciInterventoStr) {

	if (StringUtils.isBlank(codiciInterventoStr)) {
	    // non ci sono voci salvate nei filtri
	    return true;
	}
	if (vociAlbero.isEmpty()) {
	    return false;
	}
	Set<Integer> cint = new HashSet<Integer>();
	String[] ci = codiciInterventoStr.split(",");
	for (String codiceIntervento : ci) {
	    codiceIntervento = codiceIntervento.trim();
	    if (Utilities.isInteger(codiceIntervento)) {
		cint.add(Integer.parseInt(codiceIntervento));
	    }
	}
	for (Integer scId : cint) {
	    if (vociAlbero.contains(scId)) {
		return true; // SE NE CONTIENE ALMENO UNA
	    }
	}
	return false;
    }
}
