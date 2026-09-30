package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class DatiPagamento {

    private int idDettPosizioneDebitoria;
    private int idPosizioneDebitoria;
    private String descrizione;
    private BigDecimal importo = BigDecimal.ZERO;
    private Set<Rata> rate = new TreeSet<Rata>();
    private Integer idModalitaPagamento;
    private Date dataPagamento;
    private String riferimentoPagamento;

    public Set<Rata> getRate() {

	return rate;
    }

    public int getIdDettPosizioneDebitoria() {

	return idDettPosizioneDebitoria;
    }

    public void setIdDettPosizioneDebitoria(int idDettPosizioneDebitoria) {

	this.idDettPosizioneDebitoria = idDettPosizioneDebitoria;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setCausale(String descrizione) {

	this.descrizione = descrizione;
    }

    public int getIdPosizioneDebitoria() {

	return idPosizioneDebitoria;
    }

    public void setIdPosizioneDebitoria(int idPosizioneDebitoria) {

	this.idPosizioneDebitoria = idPosizioneDebitoria;
    }

    public Integer getIdModalitaPagamento() {

	return idModalitaPagamento;
    }

    public void setIdModalitaPagamento(Integer idModalitaPagamento) {

	this.idModalitaPagamento = idModalitaPagamento;
    }

    public Date getDataPagamento() {

	return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {

	this.dataPagamento = dataPagamento;
    }

    public BigDecimal getImporto() {

	if (this.rate.isEmpty()) {
	    return BigDecimal.ZERO;
	}
	BigDecimal importoCalcolato = BigDecimal.ZERO;
	for (Rata rata : rate) {
	    importoCalcolato = importoCalcolato.add(rata.getImporto());
	}
	return importoCalcolato;
    }

    public String getRiferimentoPagamento() {

	return riferimentoPagamento;
    }

    public void setRiferimentoPagamento(String riferimentoPagamento) {

	this.riferimentoPagamento = riferimentoPagamento;
    }

    public static DatiPagamento fromDettPosizioneDebitoriaResponseType(DettPosizioneDebitoriaResponseType dettaglioPosizione) {

	if (dettaglioPosizione == null) {
	    throw new RuntimeException(
		    "Impossibile creare una nuova istanza della classe DatiPagamento senza passare il dettaglio della posizione debitoria");
	}
	DatiPagamento pagamento = new DatiPagamento();
	pagamento.setIdDettPosizioneDebitoria(dettaglioPosizione.getId());
	pagamento.setCausale(dettaglioPosizione.getDescrizione());
	pagamento.setIdPosizioneDebitoria(dettaglioPosizione.getIdPosizioneDebitoria());
	if (dettaglioPosizione.getImporti() == null || dettaglioPosizione.getImporti().getRate() == null) {
	    return pagamento;
	}
	for (RataResponseType rataImporto : dettaglioPosizione.getImporti().getRate()) {
	    for (OnereResponseType onere : rataImporto.getDettagli()) {
		Rata fromDettagli = Rata.fromDettagli(rataImporto.getNumero(), rataImporto.getDataScadenza(), onere.getRaggruppamento(),
			onere.getCausale(), onere.getImporto());
		pagamento.getRate().add(fromDettagli);
	    }
	}
	return pagamento;
    }
    
    @Override
    public String toString() {

	return "[idDettPosizioneDebitoria = " + idDettPosizioneDebitoria + //
	       ", idPosizioneDebitoria = " + idPosizioneDebitoria + //
	       ", descrizione = " + descrizione + //
	       ", dataPagamento = " + dataPagamento + //
	       ", idModalitaPagamento = " + idModalitaPagamento + //
	       ", riferimentoPagamento = " + riferimentoPagamento + "]";
    }
}
