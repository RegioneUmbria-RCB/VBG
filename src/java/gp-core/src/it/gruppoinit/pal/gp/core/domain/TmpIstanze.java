package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TMP_ISTANZE")
public class TmpIstanze implements java.io.Serializable {

    private static final long serialVersionUID = 4037371361197406400L;
    private TmpIstanzeId id;

    public TmpIstanze() {

	this.id = new TmpIstanzeId();
    }

    public TmpIstanze(TmpIstanzeId id) {

	this.id = id;
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "sessionid", column = @Column(name = "SESSIONID", nullable = false, length = 60)),
	    @AttributeOverride(name = "uuid", column = @Column(name = "UUID", nullable = false, length = 60)) })
    public TmpIstanzeId getId() {

	return this.id;
    }

    public void setId(TmpIstanzeId id) {

	this.id = id;
    }
}
