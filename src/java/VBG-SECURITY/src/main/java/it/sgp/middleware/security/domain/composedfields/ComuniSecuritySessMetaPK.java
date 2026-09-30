package it.sgp.middleware.security.domain.composedfields;

import java.io.Serializable;
import java.util.Objects;

public class ComuniSecuritySessMetaPK implements Serializable{

	private String token;
	private String chiave;

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

	@Override
	public int hashCode() {
		return Objects.hash(chiave, token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComuniSecuritySessMetaPK other = (ComuniSecuritySessMetaPK) obj;
		return Objects.equals(chiave, other.chiave) && Objects.equals(token, other.token);
	}

}
