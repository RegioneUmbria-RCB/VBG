package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.pagamenti.InfoPagamentoTelematicoDtoV2;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InfoPagamentoDebitoResponse {
    
    @XmlElement
    private ChiaveDebitoDto chiaveDebitoDto;
    
    @XmlElement
    private String dataValuta;
    
    @XmlElement
    private String codiceServizio;
    
    @XmlElement
    private List<InfoPagamentoTelematicoDtoV2> listaInfoPagamentoTelematicoDtoV2;
    
    @XmlElement
    private MultibeneficiarioDto multibeneficiarioDto;
    
    
    
    //getters e setters

    public List<InfoPagamentoTelematicoDtoV2> getListaInfoPagamentoTelematicoDtoV2() {
        return listaInfoPagamentoTelematicoDtoV2;
    }

    public void setListaInfoPagamentoTelematicoDtoV2(
            List<InfoPagamentoTelematicoDtoV2> listaInfoPagamentoTelematicoDtoV2) {
        this.listaInfoPagamentoTelematicoDtoV2 = listaInfoPagamentoTelematicoDtoV2;
    }

    
    public String getDataValuta() {
    
        return dataValuta;
    }

    
    public void setDataValuta(String dataValuta) {
    
        this.dataValuta = dataValuta;
    }

    
    public ChiaveDebitoDto getChiaveDebitoDto() {
    
        return chiaveDebitoDto;
    }

    
    public void setChiaveDebitoDto(ChiaveDebitoDto chiaveDebitoDto) {
    
        this.chiaveDebitoDto = chiaveDebitoDto;
    }

    
    public String getCodiceServizio() {
    
        return codiceServizio;
    }

    
    public void setCodiceServizio(String codiceServizio) {
    
        this.codiceServizio = codiceServizio;
    }

    
    public MultibeneficiarioDto getMultibeneficiarioDto() {
    
        return multibeneficiarioDto;
    }

    
    public void setMultibeneficiarioDto(MultibeneficiarioDto multibeneficiarioDto) {
    
        this.multibeneficiarioDto = multibeneficiarioDto;
    }
}
