package it.gruppoinit.pal.gp.core.domain.helper;

import it.eng.suap.xengine.model.modulistica.QuadroType;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Modulo186ListWrapper")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Modulo186ListWrapper", propOrder = { "quadro" })
public class Modulo186ListWrapper {

    @XmlElementWrapper(name = "quadro", required = false)
    private List<QuadroType> quadro = new ArrayList<QuadroType>();

    public List<QuadroType> getQuadro() {

	return quadro;
    }

    public void setQuadro(List<QuadroType> quadro) {

	this.quadro = quadro;
    }
}
