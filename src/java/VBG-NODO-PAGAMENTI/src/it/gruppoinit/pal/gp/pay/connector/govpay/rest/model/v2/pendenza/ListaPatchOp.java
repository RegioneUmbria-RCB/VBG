package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaPatchOp", propOrder = {})
public class ListaPatchOp {

    @XmlElement(name = "listaPatchOp")
    @XmlElementWrapper
    private List<PatchOp> listaPatchOp = new ArrayList<PatchOp>();

    public List<PatchOp> getListaPatchOp() {

	return listaPatchOp;
    }

    public void setListaPatchOp(List<PatchOp> listaPatchOp) {

	this.listaPatchOp = listaPatchOp;
    }
}
