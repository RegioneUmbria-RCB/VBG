package it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import it.gruppoinit.pal.gp.core.utils.Utilities;

public class ATParserDatiProvvedimentoCommercio implements IATParserProvvedimento {

    private String numeroProvvedimento;
    private Date dataProvvedimento;
    private String dataProvvedimentoStr;
    private String cfPivaSoggetto;

    public ATParserDatiProvvedimentoCommercio(String numeroProvvedimento, String dataProvvedimento, String cfPivaSoggetto) {

	this.numeroProvvedimento = numeroProvvedimento;
	if (StringUtils.isNotBlank(dataProvvedimento)) {
	    dataProvvedimentoStr = dataProvvedimento;
	    this.dataProvvedimento = Utilities.parseDateString(dataProvvedimento, "ddMMyyyy");
	}
	this.cfPivaSoggetto = cfPivaSoggetto;
    }

    @Override
    public String getNumeroProvvedimento() {

	return numeroProvvedimento;
    }

    @Override
    public Date getDataProvvedimento() {

	return dataProvvedimento;
    }

    @Override
    public String getCfPivaSoggetto() {

	return cfPivaSoggetto;
    }

    @Override
    public boolean isDatiRicercabili() {

	return StringUtils.isNotBlank(cfPivaSoggetto) || StringUtils.isNotBlank(numeroProvvedimento);
    }

    public static IATParserProvvedimento fromRigaTraccato(String rigaTracciato) {

	// cf 			da 2 a 17
	// numeroprovvedimento da 117-132 (16 caratteri)
	// data  provvedimento da 133-140 (8 caratteri) formato GGMMAAAA	
	return new ATParserDatiProvvedimentoCommercio(rigaTracciato.substring(116, 132).trim(), rigaTracciato.substring(132, 140).trim(),
		rigaTracciato.substring(1, 17).trim());
    }

    public static void main(String[] args) {

	String rigaTracciato = "1BCCRCR73H23E256R                                                            PIACENZA                           PCD17224            180120173112299920170010000181                                     ";
	IATParserProvvedimento fromRigaTraccato = fromRigaTraccato(rigaTracciato);
	System.out.println(ReflectionToStringBuilder.toString(fromRigaTraccato));
    }

    @Override
    public String getHashChiaveProvvedimento() {

	return StringUtils.defaultString(numeroProvvedimento) //
		.concat("_") // 
		.concat(StringUtils.defaultString(dataProvvedimentoStr)) // 
		.concat("_") //
		.concat(StringUtils.defaultString(cfPivaSoggetto));
    }
}
