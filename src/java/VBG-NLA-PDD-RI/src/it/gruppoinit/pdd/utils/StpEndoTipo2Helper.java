package it.gruppoinit.pdd.utils;

public class StpEndoTipo2Helper {

    private Integer id;
    private String idComune;
    private Integer codiceInventario;
    private String descrizioneInventario;
    private String codiceEndoRegionale;
    // FK_SC_ID
    private Integer codiceIntervento;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getIdComune() {

	return idComune;
    }

    public void setIdComune(String idComune) {

	this.idComune = idComune;
    }

    public Integer getCodiceInventario() {

	return codiceInventario;
    }

    public void setCodiceInventario(Integer codiceInventario) {

	this.codiceInventario = codiceInventario;
    }

    public String getDescrizioneInventario() {

	return descrizioneInventario;
    }

    public void setDescrizioneInventario(String descrizioneInventario) {

	this.descrizioneInventario = descrizioneInventario;
    }

    public String getCodiceEndoRegionale() {

	return codiceEndoRegionale;
    }

    public void setCodiceEndoRegionale(String codiceEndoRegionale) {

	this.codiceEndoRegionale = codiceEndoRegionale;
    }

    public Integer getCodiceIntervento() {

	return codiceIntervento;
    }

    public void setCodiceIntervento(Integer codiceIntervento) {

	this.codiceIntervento = codiceIntervento;
    }
}
