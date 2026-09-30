package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "MASSIVE_DETT_DESTINATARI")
public class MassiveDettDestinatari {

    private PkId id;
    private MassiveDettaglio massiveDettaglio;
    private Anagrafe anagrafe;
    private Responsabili responsabili;
    private Amministrazioni amministrazioni;
    private String mailDestinatario;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MASSIVE_DETT_DESTINATARI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 24)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_MASSIVE_D", referencedColumnName = "ID", insertable = false, updatable = false) })
    public MassiveDettaglio getMassiveDettaglio() {

	return this.massiveDettaglio;
    }

    public void setMassiveDettaglio(MassiveDettaglio massiveDettaglio) {

	this.massiveDettaglio = massiveDettaglio;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer massiveDettaglioId;

    @Column(name = "FKID_MASSIVE_D")
    @SuppressWarnings("unused")
    private Integer getMassiveDettaglioId() {

	if (null != this.getMassiveDettaglio()) {
	    if (null != this.getMassiveDettaglio().getId()) {
		this.massiveDettaglioId = getMassiveDettaglio().getId().getCodice();
		return this.massiveDettaglioId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMassiveDettaglioId(Integer massiveDettaglioId) {

	if (null != this.getMassiveDettaglio()) {
	    if (null != this.getMassiveDettaglio().getId()) {
		this.massiveDettaglioId = getMassiveDettaglio().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEANAGRAFE", referencedColumnName = "CODICEANAGRAFE", nullable = false, insertable = false, updatable = false) })
    public Anagrafe getAnagrafe() {

	return anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer anagrafeId;

    @Column(name = "CODICEANAGRAFE")
    private Integer getAnagrafeId() {

	if (null != this.getAnagrafe()) {
	    if (null != this.getAnagrafe().getId()) {
		this.anagrafeId = getAnagrafe().getId().getCodice();
		return this.anagrafeId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAnagrafeId(Integer anagrafeId) {

	if (null != this.getAnagrafe()) {
	    if (null != this.getAnagrafe().getId()) {
		this.anagrafeId = getAnagrafe().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICERESPONSABILE", referencedColumnName = "CODICERESPONSABILE", nullable = false, insertable = false, updatable = false) })
    public Responsabili getResponsabili() {

	return this.responsabili;
    }

    public void setResponsabili(Responsabili responsabili) {

	this.responsabili = responsabili;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer responsabiliId;

    @Column(name = "CODICERESPONSABILE")
    @SuppressWarnings("unused")
    private Integer getResponsibiliId() {

	if (null != this.getResponsabili()) {
	    if (null != this.getResponsabili().getId()) {
		this.responsabiliId = getResponsabili().getId().getCodice();
		return this.responsabiliId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setResponsibiliId(Integer responsabiliId) {

	if (null != this.getResponsabili()) {
	    if (null != this.getResponsabili().getId()) {
		this.responsabiliId = getResponsabili().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEAMMINISTRAZIONE", referencedColumnName = "CODICEAMMINISTRAZIONE", nullable = false, insertable = false, updatable = false) })
    public Amministrazioni getAmministrazioni() {

	return this.amministrazioni;
    }

    public void setAmministrazioni(Amministrazioni amministrazioni) {

	this.amministrazioni = amministrazioni;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer amministrazioniId;

    @Column(name = "CODICEAMMINISTRAZIONE")
    @SuppressWarnings("unused")
    private Integer getAmministrazioniId() {

	if (null != this.getAmministrazioni()) {
	    if (null != this.getAmministrazioni().getId()) {
		this.amministrazioniId = getAmministrazioni().getId().getCodice();
		return this.amministrazioniId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAmministrazioniId(Integer amministrazioniId) {

	if (null != this.getAmministrazioni()) {
	    if (null != this.getAmministrazioni().getId()) {
		this.amministrazioniId = getAmministrazioni().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @Column(name = "MAIL_DESTINATARIO", length = 100)
    public String getMailDestinatario() {

	return mailDestinatario;
    }

    public void setMailDestinatario(String mailDestinatario) {

	this.mailDestinatario = mailDestinatario;
    }

    @Override
    public String toString() {

	if (this.amministrazioni == null && this.responsabili == null && this.anagrafe == null) {
	    return null;
	}
	StringBuilder stringBuilder = new StringBuilder();
	if (this.anagrafe != null) {
	    stringBuilder.append(this.anagrafe.getDescrizioneRichiedente());
	}
	if (this.amministrazioni != null) {
	    stringBuilder.append(this.amministrazioni.toString());
	}
	if (this.responsabili != null) {
	    stringBuilder.append(this.responsabili.toString());
	}
	
	if(this.mailDestinatario != null){
                stringBuilder.append(" (");
		stringBuilder.append(this.mailDestinatario);
		stringBuilder.append(")");
	}
	return stringBuilder.toString();
    }
}
