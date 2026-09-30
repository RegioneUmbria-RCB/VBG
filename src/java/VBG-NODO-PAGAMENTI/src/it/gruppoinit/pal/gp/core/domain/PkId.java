package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

// Generated 28-lug-2008 15.46.51 by Hibernate Tools 3.2.2.GA
/**
 * Chiave primaria per tutte le entity con chiave doppia formata da IDCOMUNE e seconda colonna numerica
 */
public class PkId implements Serializable {

    public static final String TO_STRING_ID_SEPARATOR = "_";
    public static final String TO_STRING_ID_REGEX_PATTERN = "^([a-zA-Z\\d]+)_(\\d+)$";
    private static final long serialVersionUID = -7212410626183983707L;
    private String idcomune;
    private Integer codice;

    /**
     * Costruttore che setta la proprietà interna idcomune=ORMHelper.getIdcomune()
     */
    public PkId() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    /**
     * Costruttore che setta la proprietà interna idcomune=ORMHelper.getIdcomune()
     * 
     * @param codice
     */
    public PkId(Integer codice) {

	this.idcomune = ORMHelper.getIdcomune();
	this.codice = codice;
    }

    public PkId(String idcomune, Integer codice) {

	this.idcomune = idcomune;
	this.codice = codice;
    }

    public String getIdcomune() {

	return this.idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodice() {

	return this.codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public boolean equals(Object other) {

	if ((this == other))
	    return true;
	if ((other == null))
	    return false;
	if (!(other instanceof PkId))
	    return false;
	PkId castOther = (PkId) other;
	return ((this.getIdcomune() == castOther.getIdcomune())
		|| (this.getIdcomune() != null && castOther.getIdcomune() != null && this.getIdcomune().equals(castOther.getIdcomune())))
		&& ((this.getCodice() == castOther.getCodice())
			|| (this.getCodice() != null && castOther.getCodice() != null && this.getCodice().equals(castOther.getCodice())));
    }

    public int hashCode() {

	int result = 17;
	result = 37 * result + (getIdcomune() == null ? 0 : this.getIdcomune().hashCode());
	result = 37 * result + (getCodice() == null ? 0 : this.getCodice().intValue());
	return result;
    }

    @Override
    public String toString() {

	ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
	toStringBuilder.append("idcomune", this.idcomune);
	toStringBuilder.append("codice", this.codice);
	return toStringBuilder.toString();
    }

    public static String toStringId(PkId id) {

	StringBuilder sb = new StringBuilder(id.getIdcomune()).append(TO_STRING_ID_SEPARATOR).append(id.getCodice());
	return sb.toString();
    }

    public static PkId fromStringId(String strId) {

	PkId id = null;
	Pattern ptrn = Pattern.compile(TO_STRING_ID_REGEX_PATTERN);
	Matcher matcher = ptrn.matcher(StringUtils.defaultString(strId));
	if (matcher.matches()) {
	    String idComune = matcher.group(1);
	    Integer codice = Integer.parseInt(matcher.group(2));
	    id = new PkId(idComune, codice);
	}
	return id;
    }
}
