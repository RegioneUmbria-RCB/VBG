package it.alveo.ricalcoloaree.entities;

import it.alveo.ricalcoloaree.entities.composefields.SequenceTabPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sequencetable")
public class SequenceTable {

    @EmbeddedId
    private SequenceTabPK pk;
    private Long currval;

    public SequenceTabPK getPk() {

	return pk;
    }

    public void setPk(SequenceTabPK pk) {

	this.pk = pk;
    }

    public Long getCurrval() {

	return currval;
    }

    public void setCurrval(Long currval) {

	this.currval = currval;
    }
}
