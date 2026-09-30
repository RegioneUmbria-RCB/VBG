package it.gruppoinit.pal.gp.core.features.oneri.regulus;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.IstanzeOneriRegulusDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzeOneriRegulus;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.regulus.gestoreincassi.RegulusEsitoConstants;
import it.gruppoinit.regulus.schema.billpaymentnotify.response.MSG.ESITO;

/**
 * @author francescop
 * 
 */
@Service
public class IstanzeOneriRegulusServiceImpl extends BaseServiceImpl<IstanzeOneriRegulus, PkId> implements IstanzeOneriRegulusService {

    private IstanzeOneriRegulusDAO oneriregulusDAO;

    @Autowired
    public void setOneriregulusDAO(IstanzeOneriRegulusDAO oneriregulusDAO) {

	this.oneriregulusDAO = oneriregulusDAO;
    }

    private IstanzeoneriService istanzeoneriService;

    @Autowired
    public void setIstanzeoneriService(IstanzeoneriService istanzeoneriService) {

	this.istanzeoneriService = istanzeoneriService;
    }

    private TipicausalioneriService tipicausalioneriService;

    @Autowired
    public void setTipicausalioneriService(TipicausalioneriService tipicausalioneriService) {

	this.tipicausalioneriService = tipicausalioneriService;
    }

    @Override
    protected Class<IstanzeOneriRegulus> getEntityClass() {

	return IstanzeOneriRegulus.class;
    }

    @Override
    public void delete(IstanzeOneriRegulus entity) {

	if (isDeleteAllowed(entity)) {
	    oneriregulusDAO.delete(entity);
	}
    }

    @Override
    public List<IstanzeOneriRegulus> findAll(Integer firstResult, Integer maxResult) {

	return oneriregulusDAO.findAll(null, null);
    }

    @Override
    public IstanzeOneriRegulus findById(PkId id) {

	return oneriregulusDAO.findById(id);
    }

    @Override
    public void insert(IstanzeOneriRegulus entity) {

	if (validateEntity(entity)) {
	    oneriregulusDAO.insert(entity);
	}
    }

    @Override
    public void update(IstanzeOneriRegulus entity) {

	if (validateEntity(entity)) {
	    oneriregulusDAO.update(entity);
	}
    }

    @Override
    public ESITO insertOneriRegulus(List<IstanzeOneriRegulus> list, BigInteger importobollo) {

	ESITO esito = null;
	/*
	 * Aggiorno il pagamento del bollo
	 */
	if (importobollo != null) {
	    if (importobollo.compareTo(new BigInteger("0")) > 0) {
		Integer fkidistanzaoneri = list.get(0).getIstanzeoneri().getId().getCodice();
		Istanzeoneri onere = istanzeoneriService.findById(new PkId(fkidistanzaoneri));
		Tipicausalioneri tipicausalioneri = tipicausalioneriService.findById(onere.getTipicausalioneri().getId());
		if (tipicausalioneri.getCausalebollo().getId().getCodice() != null) {
		    String impBolloPag = importobollo.toString();
		    // L'importo viene ricevuto senza virgola. Le ultime due cifre vengono assunte come
		    // decimali.
		    CharSequence impBolloPrimaDellaVirgola = impBolloPag.subSequence(0, impBolloPag.length() - 2);
		    CharSequence impBolloPrimaDopoVirgola = impBolloPag.subSequence(impBolloPag.length() - 2, impBolloPag.length());
		    impBolloPag = impBolloPrimaDellaVirgola + "." + impBolloPrimaDopoVirgola;
		    BigDecimal importoBollo = new BigDecimal(impBolloPag);
		    List<Istanzeoneri> istanzeoneriBolloList = istanzeoneriService.getOnereBollo(tipicausalioneri.getId().getCodice(),
			    onere.getIstanza());
		    // Controllo se l'importo del bollo trasmesso da regulus corrisponde all'importo del bollo presente
		    // in IstanzeOneri.
		    if (istanzeoneriBolloList != null) {
			BigDecimal importBolloTotale = new BigDecimal(0);
			for (Istanzeoneri istanzeoneri : istanzeoneriBolloList) {
			    importBolloTotale = importBolloTotale.add(istanzeoneri.getPrezzo());
			}
			if (importBolloTotale.compareTo(importoBollo) == 0) {
			    for (Istanzeoneri istanzeoneriBollo : istanzeoneriBolloList) {
				istanzeoneriBollo.setDatapagamento(list.get(0).getDataordine());
				istanzeoneriService.update(istanzeoneriBollo);
				IstanzeOneriRegulus oneriregulusBollo = new IstanzeOneriRegulus();
				IstanzeOneriRegulus temp = list.get(0);
				oneriregulusBollo.setImpcommissioni(new BigDecimal(0));
				oneriregulusBollo.setIstanzeoneri(istanzeoneriBollo);
				oneriregulusBollo.setNrratepagate(istanzeoneriBollo.getNumerorata());
				oneriregulusBollo.setNumdocumento(istanzeoneriBollo.getNrDocumento());
				oneriregulusBollo.setImportototpagamento(istanzeoneriBollo.getPrezzo());
				oneriregulusBollo.setImportototratepagate(istanzeoneriBollo.getPrezzo());
				oneriregulusBollo.setAnnodocumento(temp.getAnnodocumento());
				oneriregulusBollo.setCanaleriscossione(temp.getCanaleriscossione());
				oneriregulusBollo.setCodiceente(temp.getCodiceente());
				oneriregulusBollo.setCodicefiscalepagante(temp.getCodicefiscalepagante());
				oneriregulusBollo.setCodicetributo(tipicausalioneri.getId().getCodice().toString());
				oneriregulusBollo.setDataordine(temp.getDataordine());
				oneriregulusBollo.setIdentificativorata(istanzeoneriBollo.getNumerorata().byteValue());
				oneriregulusBollo.setIdordine(temp.getIdordine());
				oneriregulusBollo.setMetodopagamento(temp.getMetodopagamento());
				oneriregulusBollo.setNominativopagante(temp.getNominativopagante());
				oneriregulusBollo.setNrratepagate(1);
				oneriregulusBollo.setNumdocumento(istanzeoneriBollo.getNrDocumento());
				oneriregulusBollo.setRagionesocialepagante(temp.getRagionesocialepagante());
				oneriregulusBollo.setSistpagamento(temp.getSistpagamento());
				oneriregulusBollo.setStatopagamento(temp.getStatopagamento());
				oneriregulusBollo.setTipocodiceente(temp.getTipocodiceente());
				oneriregulusBollo.setTipopagamento(temp.getTipopagamento());
				this.insert(oneriregulusBollo);
			    }
			} else {
			    esito = new ESITO();
			    esito.setRC(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO.toString());
			    esito.setTIPOM(RegulusEsitoConstants.map.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO).toString());
			    esito.setValue(RegulusEsitoConstants.mapDesc.get(RegulusEsitoConstants.W_NESSUN_ELEMENTO_TROVATO));
			}
		    }
		}
	    }
	}
	if (esito == null) {
	    for (IstanzeOneriRegulus oneriregulus : list) {
		/*
		 * AGGIORNO L'ISTANZAONERI CON LA DATA DEL PAGAMENTO.
		 */
		Istanzeoneri istanzeoneri = istanzeoneriService.findById(oneriregulus.getIstanzeoneri().getId());
		istanzeoneri.setDatapagamento(oneriregulus.getDataordine());
		istanzeoneriService.update(istanzeoneri);
		/*
		 * Inserisco ISTANZEONERIREGULUS
		 */
		this.insert(oneriregulus);
	    }
	}
	return esito;
    }

    @Override
    public void deleteByIdOnere(int istanzeOneriId) {

	this.oneriregulusDAO.deleteByIdOnere(istanzeOneriId);
    }

    @Override
    public List<IstanzeOneriRegulus> findByIdIstanzeOneri(Integer idIstanzeOneri) {

	return this.oneriregulusDAO.findByIdIstanzeOneri(idIstanzeOneri);
    }
}
