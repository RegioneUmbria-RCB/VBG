package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CFG_METADATI_CMIS")
public class CfgMetadatiCmis {

    private CfgMetadatiCmisId id;
    private String mappingCmis;
    private String tipoDatoCmis;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkMetadatoBase", column = @Column(name = "FK_METADATO_BASE", nullable = false, precision = 6, scale = 0)) })
    public CfgMetadatiCmisId getId() {

	return id;
    }

    public void setId(CfgMetadatiCmisId id) {

	this.id = id;
    }

    @Column(name = "MAPPING_CMIS", nullable = false, length = 100)
    public String getMappingCmis() {

	return mappingCmis;
    }

    public void setMappingCmis(String mappingCmis) {

	this.mappingCmis = mappingCmis;
    }

    @Column(name = "TIPO_DATO_CMIS", nullable = false, length = 20)
    public String getTipoDatoCmis() {

	return tipoDatoCmis;
    }

    public void setTipoDatoCmis(String tipoDatoCmis) {

	this.tipoDatoCmis = tipoDatoCmis;
    }
}
