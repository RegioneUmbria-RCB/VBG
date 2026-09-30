package it.gruppoinit.service.helper;

import java.util.ArrayList;
import java.util.List;

public class ContainerBean<T> {

    private List<T> elementi;

    public List<T> getElementi() {

	if (this.elementi == null) {
	    this.elementi = new ArrayList<T>();
	}
	return elementi;
    }

    public void setElementi(List<T> elementi) {

	this.elementi = elementi;
    }
}
