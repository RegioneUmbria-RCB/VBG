package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class BlackListIdentificativi {

    public BlackListIdentificativi(Integer id) {

	this.id = id;
    }

    private Integer id;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    @Override
    public String toString() {

	return "[id: " + getId() + "]";
    }
}
