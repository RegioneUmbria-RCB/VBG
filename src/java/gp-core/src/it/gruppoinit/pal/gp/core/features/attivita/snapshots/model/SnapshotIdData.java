package it.gruppoinit.pal.gp.core.features.attivita.snapshots.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class SnapshotIdData {

    private Integer id;
    private Date data;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }
}
