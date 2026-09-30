package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"manifestazione", "data", "posteggio", "descrizione", "euro", "autorizzazione"})
public class SummaryBollettazioneRecord {
    
    private String manifestazione;
    private String data;
    private String posteggio;
    private String descrizione;
    private String euro;
    private String autorizzazione;

    @XmlElement(name = "Manifestazione")
    public String getManifestazione() {
        return manifestazione;
    }
    public void setManifestazione(String manifestazione) {
        this.manifestazione = manifestazione;
    }

    @XmlElement(name = "Data")
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    @XmlElement(name = "Posteggio")
    public String getPosteggio() {
        return posteggio;
    }
    public void setPosteggio(String posteggio) {
        this.posteggio = posteggio;
    }

    @XmlElement(name = "Descrizione")
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlElement(name = "Euro")
    public String getEuro() {
        return euro;
    }
    public void setEuro(String euro) {
        this.euro = euro;
    }
    
    @XmlElement(name = "Autorizzazione")
    public String getAutorizzazione() {
    
        return autorizzazione;
    }
    
    public void setAutorizzazione(String autorizzazione) {
    
        this.autorizzazione = autorizzazione;
    }
    
}
