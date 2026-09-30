package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "records")
@XmlType(propOrder = {"nomeutente", "descrizionebollettazione", "totale", "recordList"})
public class SummaryBollettazione {
    
    private String nomeutente;
    private String descrizionebollettazione;
    private String totale;
    
    private List<SummaryBollettazioneRecord> recordList;

    @XmlElement(name = "nomeutente")   
    public String getNomeutente() {
    
        return nomeutente;
    }

    
    public void setNomeutente(String nomeutente) {
    
        this.nomeutente = nomeutente;
    }

    @XmlElement(name = "descrizionebollettazione")
    public String getDescrizionebollettazione() {
    
        return descrizionebollettazione;
    }

    
    public void setDescrizionebollettazione(String descrizionebollettazione) {
    
        this.descrizionebollettazione = descrizionebollettazione;
    }

    @XmlElement(name = "totale")
    public String getTotale() {
    
        return totale;
    }

    
    public void setTotale(String totale) {
    
        this.totale = totale;
    }

    @XmlElement(name = "record")
    public List<SummaryBollettazioneRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<SummaryBollettazioneRecord> recordList) {
        this.recordList = recordList;
    }
    
    
}
