package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.segnaposto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoDTO;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.NumeroDataProtocolloZipLogico;
import it.gruppoinit.pal.gp.core.features.segnaposto.ISegnapostoMailResolver;
import it.gruppoinit.pal.gp.core.features.segnaposto.SegnapostoTemplateMailWrapper;

public class ZipLogicoTabellaHashMailResolver implements ISegnapostoMailResolver {

    public static final String TAG = "[ZIPLOGICO_TABELLA_HASH]";
    private MovimentiZipLogicoService zipLogicoService;
    private SegnapostoTemplateMailWrapper templateWrapper;
    private Integer codiceMovimento;

    public ZipLogicoTabellaHashMailResolver(SegnapostoTemplateMailWrapper templateWrapper, MovimentiZipLogicoService zipLogicoService,
	    Integer codiceMovimento) {

	if (templateWrapper == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare ZipLogicoTabellaHashHTMLResolver senza passare il service che rilegge il template");
	}
	if (zipLogicoService == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare ZipLogicoTabellaHashHTMLResolver senza passare il service per la sostituzione");
	}
	this.templateWrapper = templateWrapper;
	this.zipLogicoService = zipLogicoService;
	this.codiceMovimento = codiceMovimento;
    }

    @Override
    public String sostituisci() {

	if (this.codiceMovimento == null) {
	    return "";
	}
	//1. Recupero la lista degli allegati degli zip logici
	List<MovimentiZipLogicoDTO> zipLogico = this.zipLogicoService.findMovimentiZipLogicoDTOByMovimento(this.codiceMovimento);
	//2. Recupero il template
	String template = this.templateWrapper.getTemplate(TAG.substring(1, TAG.length() - 1));
	//3. Estrapolo la parte di template da sostituire
	String templateDettaglio = this.getTemplateDettaglio(template);
	//4. Costruisco la parte dinamica
	StringBuilder dettaglio = new StringBuilder();
	for (MovimentiZipLogicoDTO movimentiZipLogico : zipLogico) {
	    String testo = templateDettaglio.replace(ZipLogicoSegnapostoConstants.NOME_FILE, movimentiZipLogico.getNomeFile());
	    testo = testo.replace(ZipLogicoSegnapostoConstants.DESCRIZIONE, movimentiZipLogico.getDescrizione());
	    testo = testo.replace(ZipLogicoSegnapostoConstants.SHA_256,
		    this.zipLogicoService.insertOrGetSHA256(movimentiZipLogico.getCodiceOggetto()));
	    NumeroDataProtocolloZipLogico ndp = this.zipLogicoService.getNumeroDataProtocolloZipLogico(movimentiZipLogico);
	    testo = testo.replace(ZipLogicoSegnapostoConstants.NUMERO_PROTOCOLLO, StringUtils.defaultString(ndp.getNumeroProtocollo()));
	    testo = testo.replace(ZipLogicoSegnapostoConstants.DATA_PROTOCOLLO, StringUtils.defaultString(ndp.getDataProtocolloFormattata()));
	    dettaglio.append(testo);
	}
	//5. Sostituisco la parte di template fissa con il dettaglio
	return template.replace(templateDettaglio, dettaglio.toString());
    }

    private String getTemplateDettaglio(String template) {

	String testo = template.substring(template.indexOf("<TBODY>") + 7);
	testo = testo.substring(0, testo.indexOf("</TBODY>"));
	testo = testo.substring(testo.indexOf("<TR>"));
	testo = testo.substring(0, testo.indexOf("</TR>") + 5);
	return testo;
    }
}
