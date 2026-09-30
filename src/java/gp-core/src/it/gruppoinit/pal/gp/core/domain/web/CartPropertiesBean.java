/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.web;

/**
 * @author francescop
 * 
 */
public class CartPropertiesBean {

    private String end_point_url;
    private String end_point_username;
    private String end_point_password;
    private String spcoop_tipo_servizio;
    private String spcoop_servizio;
    private String spcoop_mittente;
    private String spcoop_pdd_location;
    private String id;

    public String getEnd_point_url() {

	return end_point_url;
    }

    public void setEnd_point_url(String endPointUrl) {

	end_point_url = endPointUrl;
    }

    public String getEnd_point_username() {

	return end_point_username;
    }

    public void setEnd_point_username(String endPointUsername) {

	end_point_username = endPointUsername;
    }

    public String getEnd_point_password() {

	return end_point_password;
    }

    public void setEnd_point_password(String endPointPassword) {

	end_point_password = endPointPassword;
    }

    public String getSpcoop_tipo_servizio() {

	return spcoop_tipo_servizio;
    }

    public void setSpcoop_tipo_servizio(String spcoopTipoServizio) {

	spcoop_tipo_servizio = spcoopTipoServizio;
    }

    public String getSpcoop_servizio() {

	return spcoop_servizio;
    }

    public void setSpcoop_servizio(String spcoopServizio) {

	spcoop_servizio = spcoopServizio;
    }

    public String getSpcoop_mittente() {

	return spcoop_mittente;
    }

    public void setSpcoop_mittente(String spcoopMittente) {

	spcoop_mittente = spcoopMittente;
    }

    public String getSpcoop_pdd_location() {

	return spcoop_pdd_location;
    }

    public void setSpcoop_pdd_location(String spcoopPddLocation) {

	spcoop_pdd_location = spcoopPddLocation;
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }
}
