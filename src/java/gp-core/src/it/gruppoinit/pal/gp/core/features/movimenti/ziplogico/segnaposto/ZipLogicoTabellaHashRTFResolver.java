package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoDTO;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.NumeroDataProtocolloZipLogico;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoResolver;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoTemplateWrapper;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;

public class ZipLogicoTabellaHashRTFResolver implements ISegnapostoResolver {

    public static final String TAG = "ZIPLOGICO_TABELLA_HASH";
    private MovimentiZipLogicoService zipLogicoService;
    private ISegnapostoTemplateWrapper templateWrapper;
    private Integer codiceMovimento;

    public ZipLogicoTabellaHashRTFResolver(ISegnapostoTemplateWrapper templateWrapper, MovimentiZipLogicoService zipLogicoService,
	    Integer codiceMovimento) {

	if (templateWrapper == null) {
	    throw new IllegalArgumentException(
		    "Il tag prevede un template ma non è stato passato il wrapper specifico per la sostituzione del template");
	}
	if (zipLogicoService == null) {
	    throw new IllegalArgumentException("Impossibile richiamare ZipLogicoTabellaHashResolver senza passare il service per la sostituzione");
	}
	if (codiceMovimento == null) {
	    throw new IllegalArgumentException("Impossibile richiamare ZipLogicoTabellaHashResolver senza passare il codiceMovimento");
	}
	this.zipLogicoService = zipLogicoService;
	this.templateWrapper = templateWrapper;
	this.codiceMovimento = codiceMovimento;
    }

    @Override
    public String sostituisci() {

	String template = this.templateWrapper.getTemplate(TAG);
	String testoRiga = "\\\\trowd";
	//1.Split sul tag \trowd 
	String[] righe = template.split(testoRiga);
	if (righe == null || righe.length == 0) {
	    return null;
	}
	//2. Cerco la riga che contiene le variabili da sostituire
	StringBuilder retVal = new StringBuilder();
	//il primo valore è da scartare per cui parto da 1
	for (int i = 0; i < righe.length; i++) {
	    if (this.rigaDaSostituire(righe[i])) {
		List<MovimentiZipLogicoDTO> zipLogico = this.zipLogicoService.findMovimentiZipLogicoDTOByMovimento(this.codiceMovimento);
		for (MovimentiZipLogicoDTO movimentiZipLogico : zipLogico) {
		    retVal.append(testoRiga);
		    String value = righe[i];
		    value = value.replace(ZipLogicoSegnapostoConstants.NOME_FILE,
			    FormatUtils.splitStringByNCharacters(FormatUtils.stringFormat(movimentiZipLogico.getNomeFile()), 20, " "));
		    value = value.replace(ZipLogicoSegnapostoConstants.DESCRIZIONE, FormatUtils.stringFormat(movimentiZipLogico.getDescrizione()));
		    value = value.replace(ZipLogicoSegnapostoConstants.SHA_256, FormatUtils
			    .splitStringByNCharacters(this.zipLogicoService.insertOrGetSHA256(movimentiZipLogico.getCodiceOggetto()), 20, " "));
		    NumeroDataProtocolloZipLogico ndp = this.zipLogicoService.getNumeroDataProtocolloZipLogico(movimentiZipLogico);
		    value = value.replace(ZipLogicoSegnapostoConstants.NUMERO_PROTOCOLLO, StringUtils.defaultString(ndp.getNumeroProtocollo()));
		    value = value.replace(ZipLogicoSegnapostoConstants.DATA_PROTOCOLLO, StringUtils.defaultString(ndp.getDataProtocolloFormattata()));
		    retVal.append(value);
		}
	    } else {
		if (i == 0) {
		    retVal.append(righe[i]);
		} else {
		    retVal.append(testoRiga).append(righe[i]);
		}
	    }
	}
	return retVal.toString();
    }

    private boolean rigaDaSostituire(String riga) {

	return riga.indexOf("@NOMEFILE@") >= 0;
    }
}
