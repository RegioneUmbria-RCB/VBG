package com.paevolution.appiogateway.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString(exclude = { "messaggi", "tipoConnettore" })
@AllArgsConstructor
@JsonIgnoreProperties(value = { "messaggi", "tipoConnettore" })
@Entity
@Table(name = "SERVIZI", uniqueConstraints = { @UniqueConstraint(columnNames = { "IDCOMUNE", "CODICECOMUNE", "SOFTWARE" }) })
public class Servizi implements Serializable {

    private static final long serialVersionUID = 4154860125766172609L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "ID_SERVIZIO", nullable = false, unique = true, length = 50)
    private String idServizio;
    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    @Size(max = 6, message = "IdComune deve avere al massimo 6 caratteri")
    private String idcomune;
    @Column(name = "CODICECOMUNE", nullable = false, length = 6)
    @Size(max = 6, message = "codiceComune deve avere al massimo 6 caratteri")
    private String codicecomune;
    @Column(name = "SOFTWARE", nullable = false, length = 6)
    @Size(max = 2, message = "software deve avere al massimo 6 caratteri")
    private String software;
    @Column(name = "NOME_SERVIZIO", length = 50)
    private String nomeServizio;
    @Column(name = "DIPARTIMENTO", length = 50)
    private String dipartimento;
    @Column(name = "ENTE", length = 50)
    private String ente;
    @Column(name = "CODICE_FISCALE_ENTE", length = 25)
    @Size(min = 25, max = 25, message = "codiceFiscaleEnte deve avere 25 caratteri")
    private String codiceFiscaleEnte;
    @Column(name = "CHIAVE_PRIMARIA", length = 32)
    @Size(min = 32, max = 32, message = "chiavePrimaria deve avere 32 caratteri")
    private String chiavePrimaria;
    @Column(name = "CHIAVE_SECONDARIA", length = 32)
    @Size(min = 32, max = 32, message = "chiaveSecondaria deve avere 32 caratteri")
    private String chiaveSecondaria;
    @Column(name = "CLIENT_ID", length = 32)
    private String clientId;
    @Column(name = "CLIENT_SECRET", length = 32)
    private String clientSecret;
    @Column(name = "CLIENT_REGISTRATION_ID", unique = true, length = 32)
    private String clientRegistrationId;
    @JsonBackReference
    @OneToMany(mappedBy = "servizi", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private List<Messaggi> messaggi = new ArrayList<>();
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "FK_TIPO_CONNETTORE")
    private TipoConnettore tipoConnettore;

    public Servizi() {

	this.tipoConnettore = new TipoConnettore();
    }

    @Override
    public boolean equals(Object o) {

	if (o == this)
	    return true;
	if (!(o instanceof Servizi))
	    return false;
	Servizi other = (Servizi) o;
	if (!other.canEqual((Object) this))
	    return false;
	if (this.getId() == null ? other.getId() != null : !this.getId().equals(other.getId()))
	    return false;
	if (areNotEqualsIdComuneAndCodiceComuneAndSoftware(other))
	    return false;
	return true;
    }

    @Override
    public int hashCode() {

	final int PRIME = 59;
	int result = 1;
	result = (result * PRIME) + (this.getId() == null ? 43 : this.getId().hashCode());
	result = (result * PRIME) + (this.getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = (result * PRIME) + (this.getCodicecomune() == null ? 0 : this.getCodicecomune().hashCode());
	result = (result * PRIME) + (this.getSoftware() == null ? 0 : this.getSoftware().hashCode());
	return result;
    }

    private boolean areNotEqualsIdComuneAndCodiceComuneAndSoftware(Servizi other) {

	return (this.getIdcomune() == null && this.getCodicecomune() == null && this.getSoftware() == null)
		? (other.getIdcomune() != null && other.getCodicecomune() != null && other.getSoftware() != null)
		: (!this.getIdcomune().equals(other.getIdcomune()) && !this.getCodicecomune().equals(other.getCodicecomune())
			&& !this.getSoftware().equals(other.getSoftware()));
    }

    protected boolean canEqual(Object other) {

	return other instanceof Servizi;
    }
}
