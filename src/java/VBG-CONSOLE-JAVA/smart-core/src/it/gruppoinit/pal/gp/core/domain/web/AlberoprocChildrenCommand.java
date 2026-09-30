package it.gruppoinit.pal.gp.core.domain.web;

import java.io.Serializable;

public class AlberoprocChildrenCommand implements Serializable {

    private static final long serialVersionUID = 3177463264223361110L;
    private Integer _reference;

    public AlberoprocChildrenCommand() {

    }

    public void set_reference(Integer _reference) {

	this._reference = _reference;
    }

    public Integer get_reference() {

	return _reference;
    }
}
