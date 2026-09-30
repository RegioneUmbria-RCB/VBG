package it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.paevolution.ws.pagamenti_types.DettaglioStatoPosizioneType;
import com.paevolution.ws.pagamenti_types.StatoPosizioneType;

import it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti.StatoPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DatiPagamento;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.Rata;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class VerificaStatoPosizioniDebitorie {

    private BigInteger idPosizioneDebitoria;
    private String iuv;
    private String codiceAvviso;
    private Date dataRegistrazione;
    private Date dataScadenza;
    private String descrizioneCausale;
    private String qrCode;
    private String uuid;
    private List<StatoPosizioneDebitoria> stati;
    private DatiPagamento datiPagamento;

    public BigInteger getIdPosizioneDebitoria() {

	return idPosizioneDebitoria;
    }

    public List<StatoPosizioneDebitoria> getStati() {

	return stati;
    }

    public StatoPosizioneDebitoria getStatoAttuale() {

	if (this.stati.isEmpty()) {
	    return null;
	}
	return this.stati.get(0);
    }

    public String getIuv() {

	return iuv;
    }

    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public Date getDataRegistrazione() {

	return dataRegistrazione;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public String getDescrizioneCausale() {

	return descrizioneCausale;
    }

    public String getQrCode() {

	return qrCode;
    }

    public DatiPagamento getDatiPagamento() {

	return datiPagamento;
    }

    public String getUuid() {

	return uuid;
    }

    public static VerificaStatoPosizioniDebitorie fromStatoPosizioneType(IStatoPosizionePerVerifica statoPosizionePerVerifica) {

	StatoPosizioneType statoPosizione = statoPosizionePerVerifica.getStatoPosizioneType();
	int idModalitaPagamento = statoPosizionePerVerifica.getIdModalitaPagamento();
	VerificaStatoPosizioniDebitorie verificaStato = new VerificaStatoPosizioniDebitorie();
	verificaStato.idPosizioneDebitoria = statoPosizione.getIdPosizione();
	verificaStato.iuv = statoPosizione.getIUV();
	verificaStato.codiceAvviso = statoPosizione.getCodiceAvviso();
	verificaStato.descrizioneCausale = statoPosizione.getDescrizioneCausale();
	verificaStato.qrCode = statoPosizione.getQrCode();
	verificaStato.uuid = statoPosizione.getUuid();
	if (statoPosizione.getDataRegistrazione() != null) {
	    verificaStato.dataRegistrazione = Utilities.getDate(statoPosizione.getDataRegistrazione());
	}
	if (statoPosizione.getDataScadenza() != null) {
	    verificaStato.dataScadenza = Utilities.getDate(statoPosizione.getDataScadenza());
	}
	verificaStato.stati = new ArrayList<StatoPosizioneDebitoria>();
	List<DettaglioStatoPosizioneType> cronologia = statoPosizione.getCronologiaStatiPosizione();
	for (DettaglioStatoPosizioneType dettaglioStatoPosizioneType : cronologia) {
	    String codiceStato = dettaglioStatoPosizioneType.getStato().value();
	    String descrizioneStato = dettaglioStatoPosizioneType.getDescrizioneStato();
	    verificaStato.stati
		    .add(new StatoPosizioneDebitoria(codiceStato, descrizioneStato, Utilities.getDate(dettaglioStatoPosizioneType.getDataStato())));
	}
	if (statoPosizione.getDatiPagamento() != null) {
	    verificaStato.datiPagamento = new DatiPagamento();
	    verificaStato.datiPagamento.setCausale(statoPosizione.getDatiPagamento().getDescrizioneCausale());
	    if (statoPosizione.getDatiPagamento().getDataOraPagamento() != null) {
		verificaStato.datiPagamento.setDataPagamento(Utilities.getDate(statoPosizione.getDatiPagamento().getDataOraPagamento()));
	    }
	    verificaStato.datiPagamento.setIdModalitaPagamento(idModalitaPagamento);
	    verificaStato.datiPagamento.setIdPosizioneDebitoria(statoPosizione.getIdPosizione().intValue());
	    verificaStato.datiPagamento.setRiferimentoPagamento(statoPosizione.getDatiPagamento().getRiferimentiPagamento());
	    verificaStato.datiPagamento.getRate().add(Rata.fromPagamentoDaNodoPagamenti(statoPosizione.getDatiPagamento().getImportoPagato()));
	}
	return verificaStato;
    }

    public VerificaStatoPosizioniDebitorie() {

	super();
    }

    public VerificaStatoPosizioniDebitorie(BigInteger idPosizioneDebitoria, String codiceStato, String descrizioneStato, Date dataRiferimentoStato) {

	super();
	this.idPosizioneDebitoria = idPosizioneDebitoria;
	this.stati = new ArrayList<StatoPosizioneDebitoria>();
	this.stati.add(new StatoPosizioneDebitoria(codiceStato, descrizioneStato, dataRiferimentoStato));
    }
}
