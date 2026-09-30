package it.gruppoinit.pal.gp.core.service.helper;

import org.apache.commons.lang.StringUtils;

public class ArchiviazioneDocumentaleIndice {

    private String nome;
    private String label;
    private String valore;

    public ArchiviazioneDocumentaleIndice(String nome, String label, String valore) {

	this.nome = nome;
	this.label = label;
	this.valore = valore;
    }

    public String getNome() {

	return nome;
    }

    public String getLabel() {

	return label;
    }

    public String getValore() {

	return StringUtils.defaultIfEmpty(valore, "");
    }
}
