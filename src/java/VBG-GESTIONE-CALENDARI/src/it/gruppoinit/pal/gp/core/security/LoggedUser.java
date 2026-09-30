package it.gruppoinit.pal.gp.core.security;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.User;

public class LoggedUser extends User {

    private static final long serialVersionUID = 895252920503789296L;
    private String responsabile;
    private Integer codiceResponsabile;
    private boolean amministratore;
    private Boolean readonly;

    public LoggedUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
	    boolean accountNonLocked, GrantedAuthority[] authorities) throws IllegalArgumentException {

	super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(String responsabile) {

	this.responsabile = responsabile;
    }

    public boolean isAmministratore() {

	return amministratore;
    }

    public void setAmministratore(boolean amministratore) {

	this.amministratore = amministratore;
    }

    public Integer getCodiceResponsabile() {

	return codiceResponsabile;
    }

    public void setCodiceResponsabile(Integer codiceResponsabile) {

	this.codiceResponsabile = codiceResponsabile;
    }

    public Boolean getReadonly() {

	return readonly;
    }

    public void setReadonly(Boolean readonly) {

	this.readonly = readonly;
    }
}
