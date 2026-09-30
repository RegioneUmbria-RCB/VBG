package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi;

import java.math.BigDecimal;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoMovimentoInserito implements IEvent{
    
    private Integer idborsellino;
    private BigDecimal importomovimento;
    private BigDecimal creditoTotale;
    
    public EventoMovimentoInserito(Integer idborsellino, BigDecimal importomovimento, BigDecimal creditoTotale) {
	this.idborsellino = idborsellino;
	this.importomovimento = importomovimento;
	this.creditoTotale = creditoTotale;
    }

    
    public Integer getIdborsellino() {
    
        return idborsellino;
    }

    
    public void setIdborsellino(Integer idborsellino) {
    
        this.idborsellino = idborsellino;
    }

    
    public BigDecimal getImportomovimento() {
    
        return importomovimento;
    }

    
    public void setImportomovimento(BigDecimal importomovimento) {
    
        this.importomovimento = importomovimento;
    }

    
    public BigDecimal getCreditoTotale() {
    
        return creditoTotale;
    }


    
    public void setCreditoTotale(BigDecimal creditoTotale) {
    
        this.creditoTotale = creditoTotale;
    }                
    
}
