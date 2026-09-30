package it.gruppoinit.pal.gp.core.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.User;

public class LoggedUser extends User {

    private static final long serialVersionUID = 895252920503789296L;
    private Map<String, Object> impostazioniUtente = new HashMap<String, Object>();
    private String responsabile;
    private Integer codiceResponsabile;
    private String email;
    private boolean amministratore;
    private boolean amministratoreSoftware;
    private boolean abilitaCancellazioneMasterSuSlave;
    private Integer codiceOggettoImmagine;

    public LoggedUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
	    boolean accountNonLocked, GrantedAuthority[] authorities) throws IllegalArgumentException {

	super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public void setImpostazioniUtente(Map<String, Object> impostazioniUtente) {

	this.impostazioniUtente = impostazioniUtente;
    }

    public Map<String, Object> getImpostazioniUtente() {

	return impostazioniUtente;
    }

    public String getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(String responsabile) {

	this.responsabile = responsabile;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public boolean isAmministratore() {

	return amministratore;
    }

    public void setAmministratore(boolean amministratore) {

	this.amministratore = amministratore;
    }

    public boolean getAmministratoreSoftware() {

	return amministratoreSoftware;
    }

    public void setAmministratoreSoftware(boolean amministratoreSoftware) {

	this.amministratoreSoftware = amministratoreSoftware;
    }

    public Integer getCodiceResponsabile() {

	return codiceResponsabile;
    }

    public void setCodiceResponsabile(Integer codiceResponsabile) {

	this.codiceResponsabile = codiceResponsabile;
    }

    public void setAbilitaCancellazioneMasterSuSlave(boolean abilitaCancellazioneMasterSuSlave) {

	this.abilitaCancellazioneMasterSuSlave = abilitaCancellazioneMasterSuSlave;
    }

    public boolean isAbilitaCancellazioneMasterSuSlave() {

	return abilitaCancellazioneMasterSuSlave;
    }

    public Integer getCodiceOggettoImmagine() {

	return codiceOggettoImmagine;
    }

    public void setCodiceOggettoImmagine(Integer codiceOggettoImmagine) {

	this.codiceOggettoImmagine = codiceOggettoImmagine;
    }

    @Override
    public boolean equals(Object arg0) {

	return super.equals(arg0);
    }

    @Override
    public int hashCode() {

	return super.hashCode();
    }
}
