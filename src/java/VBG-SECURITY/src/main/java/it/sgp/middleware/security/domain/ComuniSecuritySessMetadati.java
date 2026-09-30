package it.sgp.middleware.security.domain;

import it.sgp.middleware.security.domain.composedfields.ComuniSecuritySessMetaPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;

@Entity
@Table(name = "COMUNISECURITY_SESS_METADATI")
@IdClass(ComuniSecuritySessMetaPK.class)
public class ComuniSecuritySessMetadati {
	
    @Column(name = "TOKEN")
    @Id
	private String token;
    
    @Column(name = "CHIAVE")
    @Id
	private String chiave;
    
    @Lob
    @Column(name = "VALORE")
	private String valore;
    
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getChiave() {
		return chiave;
	}
	public void setChiave(String chiave) {
		this.chiave = chiave;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	
	
}
