package it.gruppoinit.pal.gp.core.domain.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LivelloServizioWizard {
    
    private int step;
    
    private List<GiornateDaConfigurare> giornateDaConfigurare = new ArrayList<GiornateDaConfigurare>();
    private ScegliLivelloDiServizio scegliLivelloDiServizio = new ScegliLivelloDiServizio();
    private Impostazioni impostazioni = new Impostazioni();
    
    //PER LO STEP FINALE
    private String step4Mercato;
    private String step4Giorni;
    private String step4LivelloServizio;
    private String step4Validita;
    private String step4Posteggi;
    private String step4UsaMqPosteggio;
    
    
    public int getStep() {
    
        return step;
    }

    public void setStep(int step) {
    
        this.step = step;
    }

    public List<GiornateDaConfigurare> getGiornateDaConfigurare() {
    
        return giornateDaConfigurare;
    }

    public void setGiornateDaConfigurare(List<GiornateDaConfigurare> giornateDaConfigurare) {
    
        this.giornateDaConfigurare = giornateDaConfigurare;
    }

    public ScegliLivelloDiServizio getScegliLivelloDiServizio() {
    
        return scegliLivelloDiServizio;
    }

    
    public void setScegliLivelloDiServizio(ScegliLivelloDiServizio scegliLivelloDiServizio) {
    
        this.scegliLivelloDiServizio = scegliLivelloDiServizio;
    }
    

    public Impostazioni getImpostazioni() {
    
        return impostazioni;
    }

    
    public void setImpostazioni(Impostazioni impostazioni) {
    
        this.impostazioni = impostazioni;
    }

    public String getStep4Mercato() {
    
        return step4Mercato;
    }

    
    public void setStep4Mercato(String step4Mercato) {
    
        this.step4Mercato = step4Mercato;
    }

    
    public String getStep4Giorni() {
    
        return step4Giorni;
    }

    
    public void setStep4Giorni(String step4Giorni) {
    
        this.step4Giorni = step4Giorni;
    }

    
    public String getStep4LivelloServizio() {
    
        return step4LivelloServizio;
    }

    
    public void setStep4LivelloServizio(String step4LivelloServizio) {
    
        this.step4LivelloServizio = step4LivelloServizio;
    }

    
    public String getStep4Validita() {
    
        return step4Validita;
    }

    
    public void setStep4Validita(String step4Validita) {
    
        this.step4Validita = step4Validita;
    }

    
    public String getStep4Posteggi() {
    
        return step4Posteggi;
    }

    
    public void setStep4Posteggi(String step4Posteggi) {
    
        this.step4Posteggi = step4Posteggi;
    }

    
    public String getStep4UsaMqPosteggio() {
    
        return step4UsaMqPosteggio;
    }

    
    public void setStep4UsaMqPosteggio(String step4UsaMqPosteggio) {
    
        this.step4UsaMqPosteggio = step4UsaMqPosteggio;
    }



    public static class GiornateDaConfigurare{
	
	private Integer id;
	private String descrizione;
	private boolean checked;
	
	public Integer getId() {
	
	    return id;
	}
	
	public void setId(Integer id) {
	
	    this.id = id;
	}
	
	public String getDescrizione() {
	
	    return descrizione;
	}

	public void setDescrizione(String descrizione) {
	
	    this.descrizione = descrizione;
	}

	public boolean isChecked() {
	
	    return checked;
	}
	
	public void setChecked(boolean checked) {
	
	    this.checked = checked;
	}

    }
    
    public static class ScegliLivelloDiServizio{
	
	//tipoLivelloDiServizio = associatiAlMercato oppure nuovoDaAssociare
	private String tipoLivelloDiServizio;
	private String livelloDiServizioM;
	private Map<String,String> livelloDiServizioMDD;
	private String descrizione;
	private String livelloDiServizioN;
	private String livelloDiServizioNid;
	private boolean attivo;
	private String tariffa;
	private String inizioValidita;
	private String fineValidita;
	
	public String getTipoLivelloDiServizio() {
	
	    return tipoLivelloDiServizio;
	}
	
	public void setTipoLivelloDiServizio(String tipoLivelloDiServizio) {
	
	    this.tipoLivelloDiServizio = tipoLivelloDiServizio;
	}

	public String getLivelloDiServizioM() {
	
	    return livelloDiServizioM;
	}
	
	public void setLivelloDiServizioM(String livelloDiServizioM) {
	
	    this.livelloDiServizioM = livelloDiServizioM;
	}
	
	public Map<String, String> getLivelloDiServizioMDD() {
	
	    return livelloDiServizioMDD;
	}

	public void setLivelloDiServizioMDD(Map<String, String> livelloDiServizioMDD) {
	
	    this.livelloDiServizioMDD = livelloDiServizioMDD;
	}

	public String getDescrizione() {
	
	    return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
	
	    this.descrizione = descrizione;
	}
	
	public String getLivelloDiServizioN() {
	
	    return livelloDiServizioN;
	}
	
	public void setLivelloDiServizioN(String livelloDiServizioN) {
	
	    this.livelloDiServizioN = livelloDiServizioN;
	}
	
	public String getLivelloDiServizioNid() {
	
	    return livelloDiServizioNid;
	}

	public void setLivelloDiServizioNid(String livelloDiServizioNid) {
	
	    this.livelloDiServizioNid = livelloDiServizioNid;
	}

	public boolean isAttivo() {
	
	    return attivo;
	}
	
	public void setAttivo(boolean attivo) {
	
	    this.attivo = attivo;
	}
	
	public String getTariffa() {
	
	    return tariffa;
	}
	
	public void setTariffa(String tariffa) {
	
	    this.tariffa = tariffa;
	}
	
	public String getInizioValidita() {
	
	    return inizioValidita;
	}
	
	public void setInizioValidita(String inizioValidita) {
	
	    this.inizioValidita = inizioValidita;
	}
	
	public String getFineValidita() {
	
	    return fineValidita;
	}
	
	public void setFineValidita(String fineValidita) {
	
	    this.fineValidita = fineValidita;
	}
	
	
    }
    
    public static class Impostazioni{
	
	private boolean mqposteggio;
	private String fattoremoltiplicativo;
	private String inizioValidita;
	private String fineValidita;
	
	public boolean isMqposteggio() {
	
	    return mqposteggio;
	}
	
	public void setMqposteggio(boolean mqposteggio) {
	
	    this.mqposteggio = mqposteggio;
	}
	
	public String getFattoremoltiplicativo() {
	
	    return fattoremoltiplicativo;
	}
	
	public void setFattoremoltiplicativo(String fattoremoltiplicativo) {
	
	    this.fattoremoltiplicativo = fattoremoltiplicativo;
	}
	
	public String getInizioValidita() {
	
	    return inizioValidita;
	}
	
	public void setInizioValidita(String inizioValidita) {
	
	    this.inizioValidita = inizioValidita;
	}
	
	public String getFineValidita() {
	
	    return fineValidita;
	}
	
	public void setFineValidita(String fineValidita) {
	
	    this.fineValidita = fineValidita;
	}
	
	
    }
    
    public static class MercatiLivelloServizioParziale{
	private String descrizione;
	private Boolean attivo;
	private Integer fkservizio;
	private BigDecimal tariffa;
	private Date iniziovalidita;
	private Date finevalidita;
	private String note;
	
	public MercatiLivelloServizioParziale(String descrizione, Boolean attivo, Integer fkservizio, BigDecimal tariffa, Date iniziovalidita, Date finevalidita,
		String note) {
	    
	    this.descrizione = descrizione;
	    this.attivo = attivo;
	    this.fkservizio = fkservizio;
	    this.tariffa = tariffa;
	    this.iniziovalidita = iniziovalidita;
	    this.finevalidita = finevalidita;
	    this.note = note;
	}

	public String getDescrizione() {
	
	    return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
	
	    this.descrizione = descrizione;
	}

	public Boolean getAttivo() {
	
	    return attivo;
	}

	public void setAttivo(Boolean attivo) {
	
	    this.attivo = attivo;
	}

	public Integer getFkservizio() {
	
	    return fkservizio;
	}
	
	public void setFkservizio(Integer fkservizio) {
	
	    this.fkservizio = fkservizio;
	}
	
	public BigDecimal getTariffa() {
	
	    return tariffa;
	}
	
	public void setTariffa(BigDecimal tariffa) {
	
	    this.tariffa = tariffa;
	}
	
	public Date getIniziovalidita() {
	
	    return iniziovalidita;
	}
	
	public void setIniziovalidita(Date iniziovalidita) {
	
	    this.iniziovalidita = iniziovalidita;
	}
	
	public Date getFinevalidita() {
	
	    return finevalidita;
	}
	
	public void setFinevalidita(Date finevalidita) {
	
	    this.finevalidita = finevalidita;
	}
	
	public String getNote() {
	
	    return note;
	}
	
	public void setNote(String note) {
	
	    this.note = note;
	}

	@Override
	public int hashCode() {
	    return 1;
	}

	@Override
	public boolean equals(Object obj) {

	    if (this == obj)
		return true;
	    if (obj == null)
		return false;
	    if (getClass() != obj.getClass())
		return false;
	    MercatiLivelloServizioParziale other = (MercatiLivelloServizioParziale) obj;
	    if (attivo == null) {
		if (other.attivo != null)
		    return false;
	    } else if (!attivo.equals(other.attivo))
		return false;
	    if (descrizione == null) {
		if (other.descrizione != null)
		    return false;
	    } else if (!descrizione.equals(other.descrizione))
		return false;
	    if (finevalidita == null) {
		if (other.finevalidita != null)
		    return false;
	    } else if (!finevalidita.equals(other.finevalidita))
		return false;
	    if (fkservizio == null) {
		if (other.fkservizio != null)
		    return false;
	    } else if (!fkservizio.equals(other.fkservizio))
		return false;
	    if (iniziovalidita == null) {
		if (other.iniziovalidita != null)
		    return false;
	    } else if (!iniziovalidita.equals(other.iniziovalidita))
		return false;
	    if (note == null) {
		if (other.note != null)
		    return false;
	    } else if (!note.equals(other.note))
		return false;
	    if (tariffa == null) {
		if (other.tariffa != null)
		    return false;
	    } else if (!tariffa.equals(other.tariffa))
		return false;
	    return true;
	}

    }
    
    
}
