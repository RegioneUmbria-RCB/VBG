package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollettazioneDAO;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public class DescrizioneRigaBollettazioneMercato implements DescrizioneRigaBollettazione {

    private Conti conto;
    private Integer idConto;
    private BollettazioneDAO bollettazioneDAO;
    private Integer idPosteggio;

    public DescrizioneRigaBollettazioneMercato(BollettazioneDAO bollettazioneDAO, Integer idConto, Integer idPosteggio) {

	this.bollettazioneDAO = bollettazioneDAO;
	this.idConto = idConto;
	this.idPosteggio = idPosteggio;
	validaRichiesta();
    }

    private void validaRichiesta() throws BusinessValidationException {

	if (this.bollettazioneDAO == null || this.idConto == null || this.idPosteggio == null) {
	    throw new BusinessValidationException("Non sono stati passati correttamente i parametri");
	}
    }

    @Override
    public String getDescrizione() {

	String descrizionePosteggio = bollettazioneDAO.findDescrizionePosteggio(idPosteggio);
	this.conto = this.bollettazioneDAO.getByIdForBollettazione(Conti.class, idConto);
	if (this.conto != null) {
	    return descrizionePosteggio = this.conto.getDescrizione() + " - " + descrizionePosteggio;
	}
	return StringUtils.left(descrizionePosteggio, 1000);
    }
}
