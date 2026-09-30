package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.domain.BollGestIstanzeoneri;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Istanze;

public class DettaglioRigaBollettazione {

    private String nominativo;
    private BigDecimal importoSenzaIVA;
    private Integer iva;
    private BigDecimal importoTotale;
    private String descrizione;
    private Boolean validato;
    private Boolean inserimentoAutomatico;
    private Integer idPosizioneDebitoria;
    private String descrizioneStato;
    private Date dataUltimoStato;
    private String noteUtente;
    private String[] noteSistema;
    List<DettaglioRigaBollettazioneOnere> dettRigaBollOneri = new ArrayList<DettaglioRigaBollettazioneOnere>();

    public DettaglioRigaBollettazione(BollGestDettaglio riga) {

	this.nominativo = riga.getAnagrafe().getDescrizioneRichiedente();
	this.importoSenzaIVA = riga.getImportoSenzaIva();
	this.iva = riga.getIva();
	this.importoTotale = riga.getImportoTotale();
	this.descrizione = riga.getDescrizione();
	this.validato = riga.getFlagValidata();
	this.inserimentoAutomatico = riga.getFlagInsAuto();
	DettPosizioneDebitoria pos = riga.getDettPosizioneDebitoria();
	if (pos != null) {
	    this.idPosizioneDebitoria = pos.getIdPosizioneDebitoria();
	    this.descrizioneStato = pos.getDescStato();
	    this.dataUltimoStato = pos.getDataUltimoStato();
	}
	this.noteUtente = riga.getNoteUtente();
	if (riga.getNoteSistema() != null) {
	    this.noteSistema = riga.getNoteSistema().split("\\r\\n");
	}
	if (!riga.getBollGestIstanzeoneris().isEmpty()) {
	    //	    BollGestIstanzeoneri istanzeoneri = (BollGestIstanzeoneri) riga.getBollGestIstanzeoneris();
	    for (BollGestIstanzeoneri onere : riga.getBollGestIstanzeoneris()) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Istanze istanze = onere.getIstanzeoneri().getIstanza();
		String dateIstanza = format.format(istanze.getData());
		String istanza = "Istanza n. " + istanze.getNumeroistanza() + " del " + dateIstanza;
		String protocollo = "";
		if (istanze.getNumeroprotocollo() != null) {
		    String dateProtocollo = format.format(istanze.getDataprotocollo());
		    protocollo = "Protocollo n. " + istanze.getNumeroprotocollo() + " del " + dateProtocollo;
		}
		String intervento = istanze.getAlberoproc().getDescrizioneCompleta();
		DettaglioRigaBollettazioneOnere dettaglioIstanzeOnere = new DettaglioRigaBollettazioneOnere(istanza, protocollo, intervento);
		this.dettRigaBollOneri.add(dettaglioIstanzeOnere);
	    }
	}
    }

    public String getNominativo() {

	return nominativo;
    }

    public BigDecimal getImportoSenzaIVA() {

	return importoSenzaIVA;
    }

    public Integer getIva() {

	return iva;
    }

    public BigDecimal getImportoTotale() {

	return importoTotale;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public Boolean getValidato() {

	return validato;
    }

    public Boolean getInserimentoAutomatico() {

	return inserimentoAutomatico;
    }

    public Integer getIdPosizioneDebitoria() {

	return idPosizioneDebitoria;
    }

    public String getDescrizioneStato() {

	return descrizioneStato;
    }

    public Date getDataUltimoStato() {

	return dataUltimoStato;
    }

    public String getNoteUtente() {

	return noteUtente;
    }

    public String[] getNoteSistema() {

	return noteSistema;
    }

    public List<DettaglioRigaBollettazioneOnere> getDettRigaBollOneri() {

	return dettRigaBollOneri;
    }
}
