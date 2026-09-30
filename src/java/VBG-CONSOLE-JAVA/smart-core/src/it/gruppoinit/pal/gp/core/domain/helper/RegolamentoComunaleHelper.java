package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.sigepro.cart.schema.rfcsuap.ElencoNormativeRegionali;

public class RegolamentoComunaleHelper {

    private String descrizioneAdempimento;
    private ElencoNormativeRegionali.NormativaRegionale normativaRegionale;
    private ElencoNormativeRegionali.NormativaRegionale normativaRegionaleEndo1;
    private String value;
    private String url;

    public String getValue() {

	return value;
    }

    public void setValue(String value) {

	this.value = value;
    }

    public String getUrl() {

	return url;
    }

    public void setUrl(String url) {

	this.url = url;
    }

    public String getDescrizioneAdempimento() {

	return descrizioneAdempimento;
    }

    public void setDescrizioneAdempimento(String descrizioneAdempimento) {

	this.descrizioneAdempimento = descrizioneAdempimento;
    }

    public ElencoNormativeRegionali.NormativaRegionale getNormativaRegionale() {

	return normativaRegionale;
    }

    public void setNormativaRegionale(ElencoNormativeRegionali.NormativaRegionale normativaRegionale) {

	this.normativaRegionale = normativaRegionale;
    }

    public ElencoNormativeRegionali.NormativaRegionale getNormativaRegionaleEndo1() {

	return normativaRegionaleEndo1;
    }

    public void setNormativaRegionaleEndo1(ElencoNormativeRegionali.NormativaRegionale normativaRegionaleEndo1) {

	this.normativaRegionaleEndo1 = normativaRegionaleEndo1;
    }
}
