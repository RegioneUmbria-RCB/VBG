package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PagamentoRequest {

	@XmlElement
    private BackUrlDto backUrlDto;
	@XmlElement
    private String codIpaRichiedente;
	@XmlElement
    private String codiceServizio;
	@XmlElement
    private List<Debito> debiti;
	@XmlElement
    private String idRichiestaPagamento;
	@XmlElement
    private boolean multibeneficiario;
	@XmlElement
    private DatiContribuente versante;

    // Getters and Setters
    public BackUrlDto getBackUrlDto() { return backUrlDto; }
    public void setBackUrlDto(BackUrlDto backUrlDto) { this.backUrlDto = backUrlDto; }

    public String getCodIpaRichiedente() { return codIpaRichiedente; }
    public void setCodIpaRichiedente(String codIpaRichiedente) { this.codIpaRichiedente = codIpaRichiedente; }

    public String getCodiceServizio() { return codiceServizio; }
    public void setCodiceServizio(String codiceServizio) { this.codiceServizio = codiceServizio; }

    public List<Debito> getDebiti() { return debiti; }
    public void setDebiti(List<Debito> debiti) { this.debiti = debiti; }

    public String getIdRichiestaPagamento() { return idRichiestaPagamento; }
    public void setIdRichiestaPagamento(String idRichiestaPagamento) { this.idRichiestaPagamento = idRichiestaPagamento; }

    public boolean isMultibeneficiario() { return multibeneficiario; }
    public void setMultibeneficiario(boolean multibeneficiario) { this.multibeneficiario = multibeneficiario; }

    public DatiContribuente getVersante() { return versante; }
    public void setVersante(DatiContribuente versante) { this.versante = versante; }
}

