package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.CommissioniDettaglioDocumentiPratica;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.CommissioniDettaglioDocumentiPratica.RiferimentiDocumento;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.RiferimentiDocumentiSelezionati;

@Service
public class CommissioniDocumentiPraticheServiceImpl implements ICommissioniDocumentiPraticheService {

    @Autowired
    private ICommissioniDocumentiPraticheDAO commissioniDocumentiPraticheDAO;

    @Override
    public CommissioniDettaglioDocumentiPratica getByIdCommissioneRIdPratica(int idCommissioneR, int codiceIstanza) {

	Istanze istanza = commissioniDocumentiPraticheDAO.getIstanzaById(codiceIstanza);
	CommissioniedilizieT commissione = commissioniDocumentiPraticheDAO.getCommissioneByIdCommissioneR(idCommissioneR);
	CommissioniDettaglioDocumentiPratica.DatiPraticaBreve praticaModel = CommissioniDettaglioDocumentiPratica.DatiPraticaBreve.daIstanza(istanza);
	CommissioniDettaglioDocumentiPratica.DatiCommissioneBreve commissioneModel = CommissioniDettaglioDocumentiPratica.DatiCommissioneBreve
		.daCommissioneT(commissione, idCommissioneR);
	CommissioniDettaglioDocumentiPratica model = new CommissioniDettaglioDocumentiPratica(praticaModel, commissioneModel);
	// Documenti generali
	Collection<RiferimentiDocumento> docs = commissioniDocumentiPraticheDAO.getDocumentiIstanzaByCodiceIstanza(codiceIstanza);
	for (RiferimentiDocumento dto : docs) {
	    model.aggiungiDocumentoGenerale(dto);
	}
	// Documenti degli endo
	Collection<RaggruppamentoDocumenti> istAll = this.commissioniDocumentiPraticheDAO.getIstanzeAllegatiByCodiceIstanza(codiceIstanza);
	for (RaggruppamentoDocumenti allegatiEndo : istAll) {
	    model.aggiungiDocumentiEndo(allegatiEndo);
	}
	// Documenti dei movimenti
	Collection<RaggruppamentoDocumenti> movAll = this.commissioniDocumentiPraticheDAO.getMovimentiAllegatiByCodiceIstanza(codiceIstanza);
	for (RaggruppamentoDocumenti allegatiMovimento : movAll) {
	    model.aggiungiDocumentiMovimento(allegatiMovimento);
	}
	// Imposto i documenti già selezionati
	RiferimentiDocumentiSelezionati documentiSelezionati = this.commissioniDocumentiPraticheDAO
		.getDocumentiSelezionatiByIdCommissioneRCodiceIstanza(idCommissioneR, codiceIstanza);
	model.setDocumentiSelezionati(documentiSelezionati);
	return model;
    }

    @Override
    public void impostaDocumentiSelezionati(int idCommissioneR, int codiceIstanza, RiferimentiDocumentiSelezionati documentiSelezionati) {

	this.commissioniDocumentiPraticheDAO.impostaDocumentiSelezionati(idCommissioneR, codiceIstanza, documentiSelezionati);
    }

    @Override
    public int countDocumentiDellaCommissione(int idCommissioneR) {

	return this.commissioniDocumentiPraticheDAO.countDocumentiDellaCommissione(idCommissioneR);
    }

    @Override
    public boolean existsByMovimentiAllegati(int idMovimentiAllegati) {

	return commissioniDocumentiPraticheDAO.existsByMovimentiAllegati(idMovimentiAllegati);
    }

    @Override
    public boolean existsByDocumentiIstanza(int idDocumentiIstanza) {

	return commissioniDocumentiPraticheDAO.existsByDocumentiIstanza(idDocumentiIstanza);
    }

    @Override
    public boolean existsByIstanzeallegati(int idIstanzeAllegati) {

	return commissioniDocumentiPraticheDAO.existsByIstanzeallegati(idIstanzeAllegati);
    }
}
