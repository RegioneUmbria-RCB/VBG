package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

@Entity
@Table(name = "NATURAENDOBASE")
public class Naturaendobase {

    private Integer id;
    private String natura;
    private Integer binariodipendenze;
    private String modalitaApertura;
    private String descrizioneEstesa;
    // campo boolean transiet che mi permette di capire se una natura endo deve essere selezionata (regola delle binario
    // dipendenze)
    // Usata nella funzionalità inserimenti di un istanza procedimento in un istanza
    private Boolean transietFlagBinariodipendenze;
    private String naturecompatitibili;
    private String naturabase;

    @Id
    @Column(name = "CODICENATURA", columnDefinition = "id", unique = true, nullable = false, precision = 10, scale = 0)
    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    @NotEmpty
    @Length(max = 50)
    @Column(name = "NATURA", length = 50)
    public String getNatura() {

	return natura;
    }

    public void setNatura(String natura) {

	this.natura = natura;
    }

    @Column(name = "BINARIODIPENDENZE", precision = 10, scale = 0)
    public Integer getBinariodipendenze() {

	return binariodipendenze;
    }

    public void setBinariodipendenze(Integer binariodipendenze) {

	this.binariodipendenze = binariodipendenze;
    }

    @NotEmpty
    @Length(max = 50)
    @Column(name = "MODALITA_APERTURA", length = 50)
    public String getModalitaApertura() {

	return modalitaApertura;
    }

    public void setModalitaApertura(String modalitaApertura) {

	this.modalitaApertura = modalitaApertura;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa) {

	this.descrizioneEstesa = descrizioneEstesa;
    }

    @Transient
    public Boolean getTransietFlagBinariodipendenze() {

	return transietFlagBinariodipendenze;
    }

    public void setTransietFlagBinariodipendenze(Boolean transietFlagBinariodipendenze) {

	this.transietFlagBinariodipendenze = transietFlagBinariodipendenze;
    }

    @Transient
    public String getDescrizioneEstesa() {

	this.descrizioneEstesa = "";
	if (StringUtils.isNotBlank(getNatura())) {
	    this.descrizioneEstesa = getNatura() + " (" + getId() + ")";
	}
	return this.descrizioneEstesa;
    }

    @Transient
    public String getNaturecompatitibili() {

	return naturecompatitibili;
    }

    public void setNaturecompatitibili(String naturecompatitibili) {

	this.naturecompatitibili = naturecompatitibili;
    }

    @Column(name = "NATURABASE", length = 20)
    public String getNaturabase() {

	return naturabase;
    }

    public void setNaturabase(String naturabase) {

	this.naturabase = naturabase;
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

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Naturaendobase other = (Naturaendobase) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    @Override
    public String toString() {

	ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
	toStringBuilder.append("id", this.id);
	toStringBuilder.append("natura", this.natura);
	toStringBuilder.append("binariodipendenze", this.binariodipendenze);
	toStringBuilder.append("modalitaApertura", this.modalitaApertura);
	return toStringBuilder.toString();
    }
}
