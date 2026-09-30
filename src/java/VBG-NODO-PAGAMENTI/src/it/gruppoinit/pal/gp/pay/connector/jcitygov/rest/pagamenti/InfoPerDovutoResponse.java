package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.pagamenti;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InfoPerDovutoResponse {

    private List<InfoPagamentoTelematicoDtoV2> listaInfoPagamentoTelematicoDtoV2;

    public List<InfoPagamentoTelematicoDtoV2> getListaInfoPagamentoTelematicoDtoV2() {
        return listaInfoPagamentoTelematicoDtoV2;
    }

    public void setListaInfoPagamentoTelematicoDtoV2(
            List<InfoPagamentoTelematicoDtoV2> listaInfoPagamentoTelematicoDtoV2) {
        this.listaInfoPagamentoTelematicoDtoV2 = listaInfoPagamentoTelematicoDtoV2;
    }
}
