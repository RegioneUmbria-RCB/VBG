package it.gruppoinit.pal.gp.core.domain.web;

import java.io.Serializable;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.VwAlberoproc;

public class AlberoprocCommand implements Serializable {

    private static final long serialVersionUID = 5570346207583295776L;
    private Integer id;
    private String name;
    private String codice;
    private String disabilitato;
    private String descrizioneEstesa;
    private Integer codiceProcedura;
    private String descrizioneProcedura;
    private Integer codiceResponsabileProc;
    private String descrizioneResponsabileProc;
    private Integer codiceResponsabileIstr;
    private String descrizioneResponsabileIstr;
    private String codiceTipomovimento;
    private String descrizioneTipomovimento;
    private Boolean endoPresenti;
    private Boolean padre;
    private Integer root;
    private String progressivoistanze;
    private Boolean scAttivo;
    private Integer scPubblica;
    private List<AlberoprocChildrenCommand> children;
    // campi aggiunti per la funzionalità di gestione degli endo procedimenti incompatibili in un procedimento
    private VwAlberoproc vwAlberoproc;
    private Alberoproc alberoproc;
    private Inventarioprocedimenti inventarioprocedimenti;
    // 1- la stringa che contiene tutti i codici (separati da virgole) degli endo procedimenti che vogliamo che
    // siano
    // incompatibili con un endo procedimento in esame.
    // 2- la stringa che contiene tutti i codici (separati da virgole) degli endo procedimenti configurati
    // per il procedimeto (escluso quello che stiamo considerando)
    private String listaDiCodiciDegliEndoprocedimentiIncompatibili;
    private String listaDiCodiciDeiEndoPerUnProcedimento;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getName() {

	return name;
    }

    public void setName(String name) {

	this.name = name;
    }

    public List<AlberoprocChildrenCommand> getChildren() {

	return children;
    }

    public void setChildren(List<AlberoprocChildrenCommand> children) {

	this.children = children;
    }

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getDisabilitato() {

	return disabilitato;
    }

    public void setDisabilitato(String disabilitato) {

	this.disabilitato = disabilitato;
    }

    public String getDescrizioneEstesa() {

	return descrizioneEstesa;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa) {

	this.descrizioneEstesa = descrizioneEstesa;
    }

    public Integer getCodiceProcedura() {

	return codiceProcedura;
    }

    public void setCodiceProcedura(Integer codiceProcedura) {

	this.codiceProcedura = codiceProcedura;
    }

    public String getDescrizioneProcedura() {

	return descrizioneProcedura;
    }

    public void setDescrizioneProcedura(String descrizioneProcedura) {

	this.descrizioneProcedura = descrizioneProcedura;
    }

    public Integer getCodiceResponsabileProc() {

	return codiceResponsabileProc;
    }

    public void setCodiceResponsabileProc(Integer codiceResponsabileProc) {

	this.codiceResponsabileProc = codiceResponsabileProc;
    }

    public String getDescrizioneResponsabileProc() {

	return descrizioneResponsabileProc;
    }

    public void setDescrizioneResponsabileProc(String descrizioneResponsabileProc) {

	this.descrizioneResponsabileProc = descrizioneResponsabileProc;
    }

    public Integer getCodiceResponsabileIstr() {

	return codiceResponsabileIstr;
    }

    public void setCodiceResponsabileIstr(Integer codiceResponsabileIstr) {

	this.codiceResponsabileIstr = codiceResponsabileIstr;
    }

    public String getDescrizioneResponsabileIstr() {

	return descrizioneResponsabileIstr;
    }

    public void setDescrizioneResponsabileIstr(String descrizioneResponsabileIstr) {

	this.descrizioneResponsabileIstr = descrizioneResponsabileIstr;
    }

    public String getCodiceTipomovimento() {

	return codiceTipomovimento;
    }

    public void setCodiceTipomovimento(String codiceTipomovimento) {

	this.codiceTipomovimento = codiceTipomovimento;
    }

    public String getDescrizioneTipomovimento() {

	return descrizioneTipomovimento;
    }

    public void setDescrizioneTipomovimento(String descrizioneTipomovimento) {

	this.descrizioneTipomovimento = descrizioneTipomovimento;
    }

    public Boolean getEndoPresenti() {

	return endoPresenti;
    }

    public void setEndoPresenti(Boolean endoPresenti) {

	this.endoPresenti = endoPresenti;
    }

    public Boolean getPadre() {

	return padre;
    }

    public void setPadre(Boolean padre) {

	this.padre = padre;
    }

    public String getProgressivoistanze() {

	return progressivoistanze;
    }

    public void setProgressivoistanze(String progressivoistanze) {

	this.progressivoistanze = progressivoistanze;
    }

    public Boolean getScAttivo() {

	return scAttivo;
    }

    public void setScAttivo(Boolean scAttivo) {

	this.scAttivo = scAttivo;
    }

    public VwAlberoproc getVwAlberoproc() {

	return vwAlberoproc;
    }

    public void setVwAlberoproc(VwAlberoproc vwAlberoproc) {

	this.vwAlberoproc = vwAlberoproc;
    }

    public Alberoproc getAlberoproc() {

	return alberoproc;
    }

    public void setAlberoproc(Alberoproc alberoproc) {

	this.alberoproc = alberoproc;
    }

    public Inventarioprocedimenti getInventarioprocedimenti() {

	return inventarioprocedimenti;
    }

    public void setInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti) {

	this.inventarioprocedimenti = inventarioprocedimenti;
    }

    public String getListaDiCodiciDegliEndoprocedimentiIncompatibili() {

	return listaDiCodiciDegliEndoprocedimentiIncompatibili;
    }

    public void setListaDiCodiciDegliEndoprocedimentiIncompatibili(String listaDiCodiciDegliEndoprocedimentiIncompatibili) {

	this.listaDiCodiciDegliEndoprocedimentiIncompatibili = listaDiCodiciDegliEndoprocedimentiIncompatibili;
    }

    public String getListaDiCodiciDeiEndoPerUnProcedimento() {

	return listaDiCodiciDeiEndoPerUnProcedimento;
    }

    public void setListaDiCodiciDeiEndoPerUnProcedimento(String listaDiCodiciDeiEndoPerUnProcedimento) {

	this.listaDiCodiciDeiEndoPerUnProcedimento = listaDiCodiciDeiEndoPerUnProcedimento;
    }

    public Integer getRoot() {

	return root;
    }

    public void setRoot(Integer root) {

	this.root = root;
    }

    public Integer getScPubblica() {

	return scPubblica;
    }

    public void setScPubblica(Integer scPubblica) {

	this.scPubblica = scPubblica;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	AlberoprocCommand other = (AlberoprocCommand) obj;
	if (id == null) {
	    return false;
	}
	return id.equals(other.id);
    }
}
