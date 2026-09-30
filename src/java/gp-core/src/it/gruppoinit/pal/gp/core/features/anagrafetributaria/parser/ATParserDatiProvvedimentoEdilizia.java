package it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import it.gruppoinit.pal.gp.core.features.anagrafetributaria.model.ATTipoRecordEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class ATParserDatiProvvedimentoEdilizia implements IATParserProvvedimento {

    private String numeroProvvedimento;
    private Date dataProvvedimento;
    private String dataProvvedimentoStr;
    private String cfPivaSoggetto;

    public ATParserDatiProvvedimentoEdilizia(String numeroProvvedimento, String dataProvvedimento, String cfPivaSoggetto) {

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

	ATTipoRecordEnum tipoRecord = ATTipoRecordEnum.fromValore(rigaTracciato.substring(0, 1));
	switch (tipoRecord) {
	case RECORD_1:
	    return fromRecord1(rigaTracciato);
	case RECORD_2:
	case RECORD_3:
	case RECORD_4:
	case RECORD_5:
	    return fromRecord(rigaTracciato);
	default:
	    break;
	}
	throw new IllegalArgumentException("Tipo record non implementato (" + rigaTracciato.substring(0, 1) + ")");
    }

    private static IATParserProvvedimento fromRecord(String rigaTracciato) {

	// cf 			da 2 a 17
	// numeroprovvedimento da 18-37 (20 caratteri)
	// data  provvedimento NON PRESENTE
	return new ATParserDatiProvvedimentoEdilizia(rigaTracciato.substring(17, 37).trim(), "", rigaTracciato.substring(1, 17).trim());
    }

    private static IATParserProvvedimento fromRecord1(String rigaTracciato) {

	// cf 			da 2 a 17
	// numeroprovvedimento da 149-168 (20 caratteri)
	// data  provvedimento da 170-177 (8 caratteri) formato GGMMAAAA
	return new ATParserDatiProvvedimentoEdilizia(rigaTracciato.substring(148, 168).trim(), rigaTracciato.substring(169, 177).trim(),
		rigaTracciato.substring(1, 17).trim());
    }

    public static void main(String[] args) {

	String rigaTracciato = "1SPRMNN73H23G888ENOTARO                    FABIO                    M10091967D403                                                                117709                 0040120170401201704012017Via F.lli Bandiera 21                                                                                                                                                         A";
	IATParserProvvedimento fromRigaTraccato = fromRigaTraccato(rigaTracciato);
	System.out.println(ReflectionToStringBuilder.toString(fromRigaTraccato));
	rigaTracciato = "2SPRMNN73H23G888E709                 SPRMNN73H23G888ENOTARO                    FABIO                    M10091967D403                                                                1                                                                                                                                                                                         A";
	fromRigaTraccato = fromRigaTraccato(rigaTracciato);
	System.out.println(ReflectionToStringBuilder.toString(fromRigaTraccato));
	rigaTracciato = "3SPRMNN73H23G888E709                 T   28   209                                                                                                                                                                                                                                                                                                                              A";
	fromRigaTraccato = fromRigaTraccato(rigaTracciato);
	System.out.println(ReflectionToStringBuilder.toString(fromRigaTraccato));
	rigaTracciato = "4SPRMNN73H23G888E709                 NVLCRL34M10E256M6            1                                                                                                                                                                                                                                                                                                            A";
	fromRigaTraccato = fromRigaTraccato(rigaTracciato);
	System.out.println(ReflectionToStringBuilder.toString(fromRigaTraccato));
	rigaTracciato = "5SPRMNN73H23G888E709                 06126150488NACCIALI COSTRUZIONI SRL                          F551                                                                                                                                                                                                                                                                         A";
	fromRigaTraccato = fromRigaTraccato(rigaTracciato);
	System.out.println(ReflectionToStringBuilder.toString(fromRigaTraccato));
    }

    @Override
    public String getHashChiaveProvvedimento() {

	return StringUtils.defaultString(numeroProvvedimento, "NON_DEFINITO") //
		.concat("_") // 
		.concat(StringUtils.defaultString(dataProvvedimentoStr, "NON_DEFINITO")) // 
		.concat("_") //
		.concat(StringUtils.defaultString(cfPivaSoggetto, "NON_DEFINITO"));
    }
}