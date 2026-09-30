package it.gruppoinit.pal.gp.core.service.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ArchiviazioneMetadatiOggetto {

    private Integer codiceIstanza;
    private Integer codiceOggetto;
    private String nomeFile;
    private String descrizioneDocumento;
    private Date dataDocumento;
    private Integer dimensioneFile;
    private Boolean docPrincipale;
    private String origine;
    public final static String CLASSE_ALLEGATI = "ALLE144426";
    public final static String CLASSE_DOC_PRINCIPALE = "PROC144426";

    public Integer getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(Integer codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public String getDescrizioneDocumento() {

	return descrizioneDocumento;
    }

    public void setDescrizioneDocumento(String descrizioneDocumento) {

	this.descrizioneDocumento = descrizioneDocumento;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public Date getDataDocumento() {

	return dataDocumento;
    }

    public void setDataDocumento(Date dataDocumento) {

	this.dataDocumento = dataDocumento;
    }

    public Boolean getDocPrincipale() {

	return docPrincipale;
    }

    public void setDocPrincipale(Boolean docPrincipale) {

	this.docPrincipale = docPrincipale;
    }

    public Integer getDimensioneFile() {

	return dimensioneFile;
    }

    public void setDimensioneFile(Integer dimensioneFile) {

	this.dimensioneFile = dimensioneFile;
    }

    public String getClasseDocumentale() {

	if (BooleanUtils.isTrue(docPrincipale)) {
	    return CLASSE_DOC_PRINCIPALE;
	}
	return CLASSE_ALLEGATI;
    }

    /**
     * lista metadati obbligatori: data_documento
     * 
     * @return
     */
    public String getMetadatiObbligatoriMancanti(ArchiviazioneMetadatiIstanza metadatiIstanza) {

	String errorString = "";
	StringBuffer metadatiMancanti = new StringBuffer();
	if (dataDocumento == null) {
	    metadatiMancanti.append("data_documento");
	}
	if (metadatiMancanti.length() > 0) {
	    //TODO recuperare il numero istanza ed il software per il messaggio di errore
	    errorString = "I documenti dell'istanza " + metadatiIstanza.getCollegamentoPratiche() + " del modulo "
		    + metadatiIstanza.getTipologiaPratica() + " non sono archiviabili perchè nel documento " + nomeFile
		    + " mancano i seguenti metadati: " + metadatiMancanti.toString() + "<br />";
	}
	return errorString;
    }

    public String getFileIndiceXML(ArchiviazioneMetadatiIstanza metaIstanza, String fileNameIndexAttr) {

	Set<ArchiviazioneDocumentaleIndice> indici = new HashSet<ArchiviazioneDocumentaleIndice>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	if (BooleanUtils.isTrue(docPrincipale)) {
	    //indici per classe documentale DOCUMENTO_PRINCIPALE
	    //TIPOLOGIA_PRATICA
	    ArchiviazioneDocumentaleIndice indiceTipoPrat = new ArchiviazioneDocumentaleIndice("procedimento", "procedimento",
		    getTipologia(metaIstanza.getTipologiaPratica()));
	    indici.add(indiceTipoPrat);
	    //SOTTOTIPO
	    ArchiviazioneDocumentaleIndice indiceSottotipo = new ArchiviazioneDocumentaleIndice("sottotipo", "sottotipo",
		    getTipologia(metaIstanza.getTipologiaPratica()) + "_" + metaIstanza.getSottotipo());
	    indici.add(indiceSottotipo);
	    //N_PROTOCOLLO
	    ArchiviazioneDocumentaleIndice indiceNumProt = new ArchiviazioneDocumentaleIndice("num_prot", "numero protocollo",
		    metaIstanza.getNumeroProtocollo());
	    indici.add(indiceNumProt);
	    //DATA_PROTOCOLLO
	    if (metaIstanza.getDataProtocollo() != null) {
		ArchiviazioneDocumentaleIndice indiceDataProt = new ArchiviazioneDocumentaleIndice("data_prot", "data_prot", sdf.format(metaIstanza
			.getDataProtocollo()));
		indici.add(indiceDataProt);
	    }
	    //DATA_DOCUMENTO
	    if (dataDocumento != null) {
		ArchiviazioneDocumentaleIndice indiceDataDoc = new ArchiviazioneDocumentaleIndice("data_documento", "Data documento",
			sdf.format(dataDocumento));
		indici.add(indiceDataDoc);
	    }
	    //SOGGETTO_RICH
	    ArchiviazioneDocumentaleIndice indiceSoggRich = new ArchiviazioneDocumentaleIndice("soggetto_rich", "soggetto richiedente",
		    getSoggettoRichiedente(metaIstanza));
	    indici.add(indiceSoggRich);
	    //CODICE_FISCALE
	    ArchiviazioneDocumentaleIndice indiceCF = new ArchiviazioneDocumentaleIndice("codf_piva", "codice_fiscale partita_iva",
		    getCfPiva(metaIstanza));
	    indici.add(indiceCF);
	    //TECNICO/RAPPRESENTANTE
	    ArchiviazioneDocumentaleIndice indiceTecnico = new ArchiviazioneDocumentaleIndice("tec_rap", "tecnico rappresentante",
		    getTecnico(metaIstanza));
	    indici.add(indiceTecnico);
	    //INDIRIZZO
	    ArchiviazioneDocumentaleIndice indiceIndirizzo = new ArchiviazioneDocumentaleIndice("indirizzo", "indirizzo", metaIstanza.getIndirizzo());
	    indici.add(indiceIndirizzo);
	    //COLLEGAMENTO_PRATICA
	    ArchiviazioneDocumentaleIndice indiceCollPratica = new ArchiviazioneDocumentaleIndice("collegamento", "collegamento",
		    metaIstanza.getCollegamentoPratiche());
	    indici.add(indiceCollPratica);
	    //TODO TITOLARIO (al momento non utilizzato)
	    //ArchiviazioneDocumentaleIndice indiceTitolario = new ArchiviazioneDocumentaleIndice("titolario", "Titolario", metaIstanza.getTitolario());
	    //indici.add(indiceTitolario);
	    ArchiviazioneDocumentaleIndice indiceChiave = new ArchiviazioneDocumentaleIndice("__ldoc_chiave_allegati", "Numero generale",
		    getLdocChiaveAllegati(metaIstanza));
	    indici.add(indiceChiave);
	} else {
	    //indici per classe documentale ALLEGATI
	    if (dataDocumento != null) {
		ArchiviazioneDocumentaleIndice indiceDataDoc = new ArchiviazioneDocumentaleIndice("data_documento", "Data documento",
			sdf.format(dataDocumento));
		indici.add(indiceDataDoc);
	    }
	    ArchiviazioneDocumentaleIndice indiceDescDoc = new ArchiviazioneDocumentaleIndice("ogg_allegato", "Oggetto Allegato", nomeFile);
	    indici.add(indiceDescDoc);
	    ArchiviazioneDocumentaleIndice indiceChiave = new ArchiviazioneDocumentaleIndice("__ldoc_chiave_allegati", "N. Generale-Protocollo",
		    getLdocChiaveAllegati(metaIstanza));
	    indici.add(indiceChiave);
	}
	return ArchiviazioneDocumentaleXMLHelper.getFileIndice(fileNameIndexAttr, getClasseDocumentale(), indici);
    }

    public String getFileName(ArchiviazioneMetadatiIstanza metaIstanza, VerticalizzazioneArchiviazioneDocumentale vad) {

	String caratteriNonAmmessi = vad.getCaratteriNonAmmessi();
	String fileName = nomeFile;
	char c = "_".charAt(0);
	if (StringUtils.isNotBlank(caratteriNonAmmessi)) {
	    for (int i = 0; i < caratteriNonAmmessi.length(); i++) {
		fileName = fileName.replace(caratteriNonAmmessi.charAt(i), c);
	    }
	}
	if (vad.getMaxFileNameLength() != null) {
	    if (fileName.length() > vad.getMaxFileNameLength()) {
		fileName = normalizzaFileName(fileName, vad.getMaxFileNameLength());
	    }
	}
	return fileName;
    }

    private String normalizzaFileName(String fileName, Integer maxSize) {

	String preString = "";
	String postString = "";
	int preSize = maxSize / 2;
	int postSize = maxSize - preSize - 1;
	int preIndex = preSize;
	int postIndex = fileName.length() - postSize;
	if (preIndex > 0) {
	    preString = fileName.substring(0, preIndex);
	}
	if (postIndex < fileName.length()) {
	    postString = fileName.substring(postIndex);
	}
	fileName = preString + "_" + postString;
	return fileName;
    }

    private String getTipologia(String tipologia) {

	if ("SS".equals(tipologia)) {
	    return "SUAP";
	}
	if ("CE".equals(tipologia)) {
	    return "SUED";
	}
	return tipologia;
    }

    private String getCfPiva(ArchiviazioneMetadatiIstanza metaIstanza) {

	String cfPiva = StringUtils.defaultIfEmpty(metaIstanza.getRichiedenteCF(), "");
	if (StringUtils.isNotBlank(metaIstanza.getRichiedentePIVA())) {
	    cfPiva += " " + metaIstanza.getRichiedentePIVA();
	}
	return cfPiva;
    }

    private String getSoggettoRichiedente(ArchiviazioneMetadatiIstanza metaIstanza) {

	return metaIstanza.getRichiedenteCognome() + " " + metaIstanza.getRichiedenteNome();
    }

    private String getTecnico(ArchiviazioneMetadatiIstanza metaIstanza) {

	String cognome = StringUtils.defaultIfEmpty(metaIstanza.getProfessionistaCognome(), "");
	String nome = StringUtils.defaultIfEmpty(metaIstanza.getProfessionistaNome(), "");
	String cf = StringUtils.defaultIfEmpty(metaIstanza.getProfessionistaCF(), "");
	String piva = StringUtils.defaultIfEmpty(metaIstanza.getProfessionistaPIVA(), "");
	if (StringUtils.isEmpty(cognome) && StringUtils.isEmpty(nome) && StringUtils.isEmpty(cf) && StringUtils.isEmpty(piva)) {
	    return "";
	}
	return cognome + " " + nome + " (" + cf + " " + piva + ")";
    }

    private String getLdocChiaveAllegati(ArchiviazioneMetadatiIstanza metaIstanza) {

	StringBuffer b = new StringBuffer();
	String tipologia = getTipologia(metaIstanza.getTipologiaPratica());
	b.append(tipologia).append("_").append(metaIstanza.getIdcomune()).append("_").append(metaIstanza.getCodiceIstanza());
	return b.toString();
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codiceOggetto == null) ? 0 : codiceOggetto.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ArchiviazioneMetadatiOggetto other = (ArchiviazioneMetadatiOggetto) obj;
	if (codiceOggetto == null) {
	    if (other.codiceOggetto != null)
		return false;
	} else if (!codiceOggetto.equals(other.codiceOggetto))
	    return false;
	return true;
    }

    @Override
    public String toString() {

	ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
	toStringBuilder.append("codiceIstanza", this.codiceIstanza);
	toStringBuilder.append("codiceOggetto", this.codiceOggetto);
	toStringBuilder.append("nomeFile", this.nomeFile);
	toStringBuilder.append("dimensioneFile", this.dimensioneFile);
	return toStringBuilder.toString();
    }

    public String getOrigine() {

	return origine;
    }

    public void setOrigine(String origine) {

	this.origine = origine;
    }

    public static void main(String[] args) {

	Integer maxSize = 3;
	String fileName = "cia";
	String preString = "";
	String postString = "";
	int preSize = maxSize / 2;
	int postSize = maxSize - preSize - 1;
	int preIndex = preSize;
	int postIndex = fileName.length() - postSize;
	if (preIndex > 0) {
	    preString = fileName.substring(0, preIndex);
	}
	if (postIndex < fileName.length()) {
	    postString = fileName.substring(postIndex);
	}
	fileName = preString + "_" + postString;
	System.out.println(fileName);
    }
}
