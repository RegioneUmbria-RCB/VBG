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

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((_reference == null) ? 0 : _reference.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	AlberoprocChildrenCommand other = (AlberoprocChildrenCommand) obj;
	if (_reference == null) {
	    return false;
	}
	return _reference.equals(other._reference);
    }
}
