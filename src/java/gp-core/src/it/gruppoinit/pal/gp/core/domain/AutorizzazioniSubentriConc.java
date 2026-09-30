package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;

@Entity
@Table(name = "AUTORIZZAZIONI_SUBENTRI_CONC")
public class AutorizzazioniSubentriConc implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4503819777274439391L;
    private PkId id;
    private AutorizzazioniSubentri autorizzazioniSubentri;
    private AutorizzazioniSubentri autorizzazioniSubentriByFkAutconcAutcoll;
    private Mercati mercati;
    private MercatiUso mercatiUso;
    private Autorizzazioni autorizzazioniByFkAutconcAutatt;
    private Concessionitipi concessionitipi;
    private MercatiD mercatiD;
    private String stagionaleda;
    private String stagionalea;
    private String transientEstremiConcessione;

    public AutorizzazioniSubentriConc() {

	this.id = new PkId();
	this.autorizzazioniSubentriByFkAutconcAutcoll = new AutorizzazioniSubentri();
	this.mercati = new Mercati();
	this.mercatiUso = new MercatiUso();
	this.autorizzazioniByFkAutconcAutatt = new Autorizzazioni();
	this.concessionitipi = new Concessionitipi();
	this.mercatiD = new MercatiD();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "AUTORIZZAZIONI_SUBENTRI_CONC.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_AUTSUB_ID", referencedColumnName = "ID", insertable = false, updatable = false) })
    public AutorizzazioniSubentri getAutorizzazioniSubentri() {

	return this.autorizzazioniSubentri;
    }

    public void setAutorizzazioniSubentri(AutorizzazioniSubentri autorizzazioniSubentri) {

	this.autorizzazioniSubentri = autorizzazioniSubentri;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer autorizzazioniSubentriId;

    @Column(name = "FK_AUTSUB_ID")
    @SuppressWarnings("unused")
    private Integer getAutorizzazioniSubentriId() {

	if (null != this.getAutorizzazioniSubentri()) {
	    if (null != this.getAutorizzazioniSubentri().getId()) {
		this.autorizzazioniSubentriId = getAutorizzazioniSubentri().getId().getCodice();
		return this.autorizzazioniSubentriId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAutorizzazioniSubentriId(Integer autorizzazioniSubentriId) {

	if (null != this.getAutorizzazioniSubentri()) {
	    if (null != this.getAutorizzazioniSubentri().getId()) {
		this.autorizzazioniSubentriId = getAutorizzazioniSubentri().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_IDAUTSUB_AUTCOLL", referencedColumnName = "ID", insertable = false, updatable = false) })
    public AutorizzazioniSubentri getAutorizzazioniSubentriByFkAutconcAutcoll() {

	return autorizzazioniSubentriByFkAutconcAutcoll;
    }

    public void setAutorizzazioniSubentriByFkAutconcAutcoll(AutorizzazioniSubentri autorizzazioniSubentriByFkAutconcAutcoll) {

	this.autorizzazioniSubentriByFkAutconcAutcoll = autorizzazioniSubentriByFkAutconcAutcoll;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer autorizzazioniSubentriByFkAutconcAutcollId;

    @Column(name = "FK_IDAUTSUB_AUTCOLL")
    @SuppressWarnings("unused")
    private Integer getAutorizzazioniSubentriByFkAutconcAutcollId() {

	if (null != this.getAutorizzazioniSubentriByFkAutconcAutcoll()) {
	    if (null != this.getAutorizzazioniSubentriByFkAutconcAutcoll().getId()) {
		this.autorizzazioniSubentriByFkAutconcAutcollId = getAutorizzazioniSubentriByFkAutconcAutcoll().getId().getCodice();
		return this.autorizzazioniSubentriByFkAutconcAutcollId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAutorizzazioniSubentriByFkAutconcAutcollId(Integer autorizzazioniByFkAutconcAutcollId) {

	if (null != this.getAutorizzazioniSubentriByFkAutconcAutcoll()) {
	    if (null != this.getAutorizzazioniSubentriByFkAutconcAutcoll().getId()) {
		this.autorizzazioniSubentriByFkAutconcAutcollId = getAutorizzazioniSubentriByFkAutconcAutcoll().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_CODICEMERCATO", referencedColumnName = "CODICEMERCATO", insertable = false, updatable = false) })
    public Mercati getMercati() {

	return this.mercati;
    }

    public void setMercati(Mercati mercati) {

	this.mercati = mercati;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatiId;

    @Column(name = "FK_CODICEMERCATO")
    @SuppressWarnings("unused")
    private Integer getMercatiId() {

	if (null != this.getMercati()) {
	    if (null != this.getMercati().getId()) {
		this.mercatiId = getMercati().getId().getCodice();
		return this.mercatiId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMercatiId(Integer mercatiId) {

	if (null != this.getMercati()) {
	    if (null != this.getMercati().getId()) {
		this.mercatiId = getMercati().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_IDMERCATIUSO", referencedColumnName = "ID", insertable = false, updatable = false) })
    public MercatiUso getMercatiUso() {

	return this.mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatiUsoId;

    @Column(name = "FK_IDMERCATIUSO")
    @SuppressWarnings("unused")
    private Integer getMercatiUsoId() {

	if (null != this.getMercatiUso()) {
	    if (null != this.getMercatiUso().getId()) {
		this.mercatiUsoId = getMercatiUso().getId().getCodice();
		return this.mercatiUsoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMercatiUsoId(Integer mercatiUsoId) {

	if (null != this.getMercatiUso()) {
	    if (null != this.getMercatiUso().getId()) {
		this.mercatiUsoId = getMercatiUso().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_IDAUT_ATTUALE", referencedColumnName = "ID", insertable = false, updatable = false) })
    public Autorizzazioni getAutorizzazioniByFkAutconcAutatt() {

	return this.autorizzazioniByFkAutconcAutatt;
    }

    public void setAutorizzazioniByFkAutconcAutatt(Autorizzazioni autorizzazioniByFkAutconcAutatt) {

	this.autorizzazioniByFkAutconcAutatt = autorizzazioniByFkAutconcAutatt;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer autorizzazioniByFkAutconcAutattId;

    @Column(name = "FK_IDAUT_ATTUALE")
    @SuppressWarnings("unused")
    private Integer getAutorizzazioniByFkAutconcAutattId() {

	if (null != this.getAutorizzazioniByFkAutconcAutatt()) {
	    if (null != this.getAutorizzazioniByFkAutconcAutatt().getId()) {
		this.autorizzazioniByFkAutconcAutattId = getAutorizzazioniByFkAutconcAutatt().getId().getCodice();
		return this.autorizzazioniByFkAutconcAutattId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAutorizzazioniByFkAutconcAutattId(Integer autorizzazioniByFkAutconcAutattId) {

	if (null != this.getAutorizzazioniByFkAutconcAutatt()) {
	    if (null != this.getAutorizzazioniByFkAutconcAutatt().getId()) {
		this.autorizzazioniByFkAutconcAutattId = getAutorizzazioniByFkAutconcAutatt().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TIPOCONCESSIONE")
    public Concessionitipi getConcessionitipi() {

	return this.concessionitipi;
    }

    public void setConcessionitipi(Concessionitipi concessionitipi) {

	this.concessionitipi = concessionitipi;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_IDPOSTEGGIO", referencedColumnName = "IDPOSTEGGIO", insertable = false, updatable = false) })
    public MercatiD getMercatiD() {

	return this.mercatiD;
    }

    public void setMercatiD(MercatiD mercatiD) {

	this.mercatiD = mercatiD;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatiDId;

    @Column(name = "FK_IDPOSTEGGIO")
    @SuppressWarnings("unused")
    private Integer getMercatiDId() {

	if (null != this.getMercatiD()) {
	    if (null != this.getMercatiD().getId()) {
		this.mercatiDId = getMercatiD().getId().getCodice();
		return this.mercatiDId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMercatiDId(Integer mercatiDId) {

	if (null != this.getMercatiD()) {
	    if (null != this.getMercatiD().getId()) {
		this.mercatiDId = getMercatiD().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @Pattern(regex = "((([0-2][0-9])|([3-3][0-1]))(([0-0][0-9])|([1-1][0-2]))(()|(([1-1][9-9][0-9][0-9])|([2-2][0-0][0-9][0-9]))))*", message = "{validator.period}")
    @Column(name = "STAGIONALEDA", length = 4)
    public String getStagionaleda() {

	return this.stagionaleda;
    }

    public void setStagionaleda(String stagionaleda) {

	this.stagionaleda = stagionaleda;
    }

    @Pattern(regex = "((([0-2][0-9])|([3-3][0-1]))(([0-0][0-9])|([1-1][0-2]))(()|(([1-1][9-9][0-9][0-9])|([2-2][0-0][0-9][0-9]))))*", message = "{validator.period}")
    @Column(name = "STAGIONALEA", length = 4)
    public String getStagionalea() {

	return this.stagionalea;
    }

    public void setStagionalea(String stagionalea) {

	this.stagionalea = stagionalea;
    }

    /**
     * Proprietà utilizzata per visualizzare il periodo nella forma gg/MM/yyyy oppure gg/MM
     * 
     */
    @Transient
    public String getStagionaledaTransient() {

	String answer = null;
	if (this.stagionaleda != null && this.stagionaleda.length() == 4) {
	    answer = this.stagionaleda.substring(0, 2) + "/" + this.stagionaleda.substring(2, 4);
	}
	if (this.stagionaleda != null && this.stagionaleda.length() == 8) {
	    answer = this.stagionaleda.substring(0, 2) + "/" + this.stagionaleda.substring(2, 4) + "/" + this.stagionaleda.substring(4, 8);
	}
	return answer;
    }

    public void setStagionaledaTransient(String stagionaledaTransient) {

	if (stagionaledaTransient != null) {
	    this.setStagionaleda(stagionaledaTransient.replace("/", ""));
	} else {
	    this.stagionaleda = null;
	}
    }

    /**
     * Proprietà utilizzata per visualizzare il periodo nella forma gg/MM/yyyy oppure gg/MM
     * 
     */
    @Transient
    public String getStagionaleaTransient() {

	String answer = null;
	if (this.stagionalea != null && this.stagionalea.length() == 4) {
	    answer = this.stagionalea.substring(0, 2) + "/" + this.stagionalea.substring(2, 4);
	}
	if (this.stagionalea != null && this.stagionalea.length() == 8) {
	    answer = this.stagionalea.substring(0, 2) + "/" + this.stagionalea.substring(2, 4) + "/" + this.stagionalea.substring(4, 8);
	}
	return answer;
    }

    public void setStagionaleaTransient(String stagionaleaTransient) {

	if (stagionaleaTransient != null) {
	    this.setStagionalea(stagionaleaTransient.replace("/", ""));
	} else {
	    this.stagionalea = null;
	}
    }

    @Transient
    public String getTransientEstremiConcessione() {

	StringBuffer estremiConcess = new StringBuffer();
	if (EntityUtils.getNestedProperty(this.autorizzazioniSubentri, "id.codice") != null) {
	    estremiConcess = estremiConcess.append(this.autorizzazioniSubentri.getTransientEstremiAut());
	}
	estremiConcess = estremiConcess.append(" - (");
	if (EntityUtils.getNestedProperty(this.getMercati(), "id.codice") != null) {
	    estremiConcess = estremiConcess.append(this.getMercati().getDescrizione()).append(", ");
	}
	if (EntityUtils.getNestedProperty(this.getMercatiUso(), "id.codice") != null) {
	    estremiConcess = estremiConcess.append(this.getMercatiUso().getDescrizione());
	}
	if (EntityUtils.getNestedProperty(this.getMercatiD(), "id.codice") != null) {
	    estremiConcess = estremiConcess.append(" [Posteggio:").append(this.getMercatiD().getCodiceposteggio()).append("]");
	}
	estremiConcess = estremiConcess.append(")");
	this.transientEstremiConcessione = estremiConcess.toString();
	return this.transientEstremiConcessione;
    }

    public void setTransientEstremiConcessione(String transientEstremiConcessione) {

	this.transientEstremiConcessione = transientEstremiConcessione;
    }
}
