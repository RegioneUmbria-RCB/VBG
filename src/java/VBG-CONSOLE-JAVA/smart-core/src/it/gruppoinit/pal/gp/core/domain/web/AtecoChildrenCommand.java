package it.gruppoinit.pal.gp.core.domain.web;

import java.io.Serializable;

public class AtecoChildrenCommand implements Serializable {

    private static final long serialVersionUID = -4176296567904788894L;
    private Integer _reference;

    public AtecoChildrenCommand() {

    }

    public void set_reference(Integer _reference) {

	this._reference = _reference;
    }

    public Integer get_reference() {

	return _reference;
    }
}
