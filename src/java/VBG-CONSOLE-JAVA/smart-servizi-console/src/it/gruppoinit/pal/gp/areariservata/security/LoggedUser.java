package it.gruppoinit.pal.gp.areariservata.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.User;

public class LoggedUser extends User {

    private static final long serialVersionUID = 895252920503789296L;
    private Map<String, Object> impostazioniUtente = new HashMap<String, Object>();
    private String anagrafe;
    private String cf;
    private Integer codiceAnagrafe;
    private String email;

    public LoggedUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
	    boolean accountNonLocked, GrantedAuthority[] authorities) throws IllegalArgumentException {

	super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(Integer codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public String getAnagrafe() {

	return anagrafe;
    }

    public void setAnagrafe(String anagrafe) {

	this.anagrafe = anagrafe;
    }

    public void setImpostazioniUtente(Map<String, Object> impostazioniUtente) {

	this.impostazioniUtente = impostazioniUtente;
    }

    public Map<String, Object> getImpostazioniUtente() {

	return impostazioniUtente;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    @Override
    public boolean equals(Object arg0) {

	return super.equals(arg0);
    }

    @Override
    public int hashCode() {

	return super.hashCode();
    }

    public String getCf() {

	return cf;
    }

    public void setCf(String cf) {

	this.cf = cf;
    }
}
