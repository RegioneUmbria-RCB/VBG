package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.math.BigDecimal;

public class RigaDettaglioCalcolo implements IRigaDettaglioCalcolo {

    private Integer idAnagrafe;
    private BigDecimal importoTotale;
    private String descrizione;
    private Integer idConto;
    private Integer idRiferimento;
    private boolean subentro;
    private BigDecimal importoSenzaIVA;
    private Integer iva;
    private Integer idAutorizzazioneConcessione;
    
    private Integer idUso;
    private Integer idPosteggio;
    private Integer idAutorizzazioniSubentri;
    

    public Integer getIdAnagrafe() {

	return this.idAnagrafe;
    }

    public void setIdAnagrafe(Integer idAnagrafe) {

	this.idAnagrafe = idAnagrafe;
    }

    public BigDecimal getImportoTotale() {

	return importoTotale;
    }

    public void setImportoTotale(BigDecimal importoTotale) {

	this.importoTotale = importoTotale;
    }

    public String getDescrizione() {

	return this.descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public void setDescrizione(DescrizioneRigaBollettazione descrizioneRigaBollettazione) {

	if (descrizioneRigaBollettazione != null) {
	    this.setDescrizione(descrizioneRigaBollettazione.getDescrizione());
	}
    }

    public Integer getIdConto() {

	return this.idConto;
    }

    public void setIdConto(Integer idConto) {

	this.idConto = idConto;
    }

    public Integer getIdRiferimento() {

	return this.idRiferimento;
    }

    public void setIdRiferimento(Integer idRiferimento) {

	this.idRiferimento = idRiferimento;
    }

    public BigDecimal getImportoSenzaIVA() {

	return importoSenzaIVA;
    }

    public void setImportoSenzaIVA(BigDecimal importoSenzaIVA) {

	this.importoSenzaIVA = importoSenzaIVA;
    }

    public Integer getIva() {

	return iva;
    }

    public void setIva(Integer iva) {

	this.iva = iva;
    }

    public boolean isSubentro() {

	return subentro;
    }

    public void setSubentro(boolean subentro) {

	this.subentro = subentro;
    }

    public Integer getIdAutorizzazioneConcessione() {

	return idAutorizzazioneConcessione;
    }

    public void setIdAutorizzazioneConcessione(Integer idAutorizzazioneConcessione) {

	this.idAutorizzazioneConcessione = idAutorizzazioneConcessione;
    }

    public String getChiaveRiferimentoAutorizzazione() {

	return new ChiaveCalcoloRiferimentoAutorizzazione(this.getIdRiferimento(), this.isSubentro(), this.getIdPosteggio(), this.getIdUso()).getChiave();
    }

    
    public Integer getIdUso() {
    
        return idUso;
    }

    
    public void setIdUso(Integer idUso) {
    
        this.idUso = idUso;
    }

    
    public Integer getIdPosteggio() {
    
        return idPosteggio;
    }

    
    public void setIdPosteggio(Integer idPosteggio) {
    
        this.idPosteggio = idPosteggio;
    }

    
    public Integer getIdAutorizzazioniSubentri() {
    
        return idAutorizzazioniSubentri;
    }

    
    public void setIdAutorizzazioniSubentri(Integer idAutorizzazioniSubentri) {
    
        this.idAutorizzazioniSubentri = idAutorizzazioniSubentri;
    }
}
