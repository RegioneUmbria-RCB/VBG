package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "gerarchia")
public class GerarchiaProcedimentiBean {

    @XmlElement(name = "lista")
    private List<ProcedimentoSimpleBean> lista = new ArrayList<ProcedimentoSimpleBean>();

    public List<ProcedimentoSimpleBean> getLista() {

	return lista;
    }

    public void setLista(List<ProcedimentoSimpleBean> lista) {

	this.lista = lista;
    }
}
