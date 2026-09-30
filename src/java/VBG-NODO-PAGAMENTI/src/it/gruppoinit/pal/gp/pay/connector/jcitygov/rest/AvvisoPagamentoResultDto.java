package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AvvisoPagamentoResultDto {

    @XmlElement
    private ChiaveDebitoDto chiaveDebitoDto;

    @XmlElement
    private Esito esito;

    @XmlElement
    private NumeroAvvisoDto numeroAvvisoDto;

	public ChiaveDebitoDto getChiaveDebitoDto() {
		return chiaveDebitoDto;
	}

	public void setChiaveDebitoDto(ChiaveDebitoDto chiaveDebitoDto) {
		this.chiaveDebitoDto = chiaveDebitoDto;
	}

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}

	public NumeroAvvisoDto getNumeroAvvisoDto() {
		return numeroAvvisoDto;
	}

	public void setNumeroAvvisoDto(NumeroAvvisoDto numeroAvvisoDto) {
		this.numeroAvvisoDto = numeroAvvisoDto;
	}

    
}
