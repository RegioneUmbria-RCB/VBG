package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchiviazioneMetadatiOggetto {

    private Integer codiceIstanza;
    private Integer codiceOggetto;
    private String nomeFile;
    private String descrizioneDocumento;
    private Date dataDocumento;
    private Integer dimensioneFile;
    private Boolean docPrincipale;
    private String origine;
    private String hashFile;
    public final static String CLASSE_DOC_PRINCIPALE = "proc144426";
    public static final Logger log = LoggerFactory.getLogger(ArchiviazioneMetadatiOggetto.class);

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

	return CLASSE_DOC_PRINCIPALE;
    }

    public String getOrigine() {

	return origine;
    }

    public void setOrigine(String origine) {

	this.origine = origine;
    }

    public String getHashFile() {

	return hashFile;
    }

    public void setHashFile(String hashFile) {

	this.hashFile = hashFile;
    }

    /**
     * lista metadati obbligatori: __data_documento_dt<br />
     * in questo metodo controllo solo la data, gli altri li controllo in
     * {@link ArchiviazioneMetadatiIstanza#getMetadatiObbligatoriMancanti()}
     * 
     * @return
     */
    public String getMetadatiObbligatoriMancanti(ArchiviazioneMetadatiIstanza metadatiIstanza) {

	String errorString = "";
	StringBuffer metadatiMancanti = new StringBuffer();
	if (dataDocumento == null) {
	    metadatiMancanti.append("__data_documento_dt");
	}
	if (metadatiMancanti.length() > 0) {
	    errorString = "I documenti dell'istanza " + metadatiIstanza.getCollegamentoPratiche() + " del modulo "
		    + metadatiIstanza.getTipologiaPratica() + " non sono archiviabili perchè nel documento " + nomeFile
		    + " mancano i seguenti metadati: " + metadatiMancanti.toString() + "<br />";
	}
	return errorString;
    }

    /**
     * metodo per la generazione del file di indice. lista degli indici ammessi<br />
     * __data_documento_dt Data documento <br />
     * ldoc_chiave_allegati_s Numero generale <br />
     * codf_piva_s codice_fiscale partita_iva <br />
     * collegamento_s collegamento <br />
     * data_prot_dt data_prot <br />
     * indirizzo_s indirizzo <br />
     * num_prot_s numero protocollo <br />
     * procedimento_s procedimento <br />
     * soggetto_rich_s soggetto richiedente <br />
     * sottotipo_s sottotipo <br />
     * tec_rap_s tecnico rappresentante <br />
     * titolario_s titolario
     *
     * @param metaIstanza
     * @param fileNameIndexAttr
     * @return
     */
    public String getFileIndiceXML(ArchiviazioneMetadatiIstanza metaIstanza, String fileNameIndexAttr, VerticalizzazioneArchiviazioneDocumentale vad) {

	Set<ArchiviazioneDocumentaleIndice> indici = new HashSet<ArchiviazioneDocumentaleIndice>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	// TIPOLOGIA_PRATICA
	String tipologiaPratica = bonificaStringaDaiCaratteriNonAmmessi(metaIstanza.getTipologiaPratica(), vad);
	ArchiviazioneDocumentaleIndice indiceTipoPrat = new ArchiviazioneDocumentaleIndice("procedimento_s", "procedimento",
		getTipologia(tipologiaPratica));
	indici.add(indiceTipoPrat);
	if (BooleanUtils.isTrue(docPrincipale)) {
	    //CHIAVE
	    ArchiviazioneDocumentaleIndice indiceChiave = new ArchiviazioneDocumentaleIndice("ldoc_chiave_allegati_s", "Numero generale",
		    getLdocChiaveAllegati(metaIstanza));
	    indici.add(indiceChiave);
	} else {
	    //CHIAVE
	    ArchiviazioneDocumentaleIndice indiceChiave = new ArchiviazioneDocumentaleIndice("ldoc_chiave_allegati_s", "Numero generale",
		    getLdocChiaveAllegati(metaIstanza));
	    indici.add(indiceChiave);
	}
	// SOTTOTIPO
	// DALLE PROVE EFFETTUATE NEL FILE INDEX SONO ACCETTATI I CARATTERI ACCENTATI
	//String sottoTipo = bonificaStringaDaiCaratteriNonAmmessi(metaIstanza.getSottotipo(), vad);
	//	ArchiviazioneDocumentaleIndice indiceSottotipo = new ArchiviazioneDocumentaleIndice("sottotipo_s", "sottotipo", metaIstanza.getSottotipo());
	String _indiceSottotipo = bonificaStringaDaiCaratteriNonAmmessi(metaIstanza.getSottotipo(), vad);
	ArchiviazioneDocumentaleIndice indiceSottotipo = new ArchiviazioneDocumentaleIndice("sottotipo_s", "sottotipo", _indiceSottotipo);
	indici.add(indiceSottotipo);
	// N_PROTOCOLLO
	ArchiviazioneDocumentaleIndice indiceNumProt = new ArchiviazioneDocumentaleIndice("num_prot_s", "numero protocollo",
		metaIstanza.getNumeroProtocollo());
	indici.add(indiceNumProt);
	// DATA_PROTOCOLLO
	if (metaIstanza.getDataProtocollo() != null) {
	    ArchiviazioneDocumentaleIndice indiceDataProt = new ArchiviazioneDocumentaleIndice("data_prot_dt", "data_prot", sdf.format(metaIstanza
		    .getDataProtocollo()));
	    indici.add(indiceDataProt);
	}
	// DATA_DOCUMENTO
	ArchiviazioneDocumentaleIndice indiceDataDoc = new ArchiviazioneDocumentaleIndice("__data_documento_dt", "Data documento",
		sdf.format(dataDocumento));
	indici.add(indiceDataDoc);
	// SOGGETTO_RICH
	String soggettoRichiedente = bonificaStringaDaiCaratteriNonAmmessi(getSoggettoRichiedente(metaIstanza), vad);
	ArchiviazioneDocumentaleIndice indiceSoggRich = new ArchiviazioneDocumentaleIndice("soggetto_rich_s", "soggetto richiedente",
		soggettoRichiedente);
	indici.add(indiceSoggRich);
	// CODICE_FISCALE
	ArchiviazioneDocumentaleIndice indiceCF = new ArchiviazioneDocumentaleIndice("codf_piva_s", "codice_fiscale partita_iva",
		getCfPiva(metaIstanza));
	indici.add(indiceCF);
	// TECNICO/RAPPRESENTANTE
	String _indiceTecnico = bonificaStringaDaiCaratteriNonAmmessi(getTecnico(metaIstanza), vad);
	ArchiviazioneDocumentaleIndice indiceTecnico = new ArchiviazioneDocumentaleIndice("tec_rap_s", "tecnico rappresentante", _indiceTecnico);
	indici.add(indiceTecnico);
	// INDIRIZZO
	String _indirizzo = bonificaStringaDaiCaratteriNonAmmessi(metaIstanza.getIndirizzo(), vad);
	ArchiviazioneDocumentaleIndice indiceIndirizzo = new ArchiviazioneDocumentaleIndice("indirizzo_s", "indirizzo", _indirizzo);
	indici.add(indiceIndirizzo);
	// COLLEGAMENTO_PRATICA
	//String _CollegamentoPratiche = bonificaStringaDaiCaratteriNonAmmessi(metaIstanza.getCollegamentoPratiche(), vad);
	String _CollegamentoPratiche = metaIstanza.getCollegamentoPratiche();
	ArchiviazioneDocumentaleIndice indiceCollPratica = new ArchiviazioneDocumentaleIndice("collegamento_s", "collegamento", _CollegamentoPratiche);
	indici.add(indiceCollPratica);
	// TODO TITOLARIO (al momento non utilizzato)
	// ArchiviazioneDocumentaleIndice indiceTitolario = new ArchiviazioneDocumentaleIndice("titolario_s", "titolario", metaIstanza.getTitolario());
	// indici.add(indiceTitolario);
	String indexXml = "";
	try {
	    indexXml = ArchiviazioneDocumentaleXMLHelper.getFileIndice(indici, vad);
	    log.debug("#ArchiviazioneMetadatiOggetto: bonifico il file indice dai caratteri non ammessi");
	} catch (Exception e) {
	    log.error("getFileIndiceXML# {} ", e);
	    throw new RuntimeException(e);
	}
	return indexXml;
	//return ArchiviazioneDocumentaleXMLHelper.getFileIndice(fileNameIndexAttr, getClasseDocumentale(), indici);
    }

    public String getFileName(ArchiviazioneMetadatiIstanza metaIstanza, VerticalizzazioneArchiviazioneDocumentale vad) {

	String fileName = nomeFile;
	//metto tutto in minuscolo (legaldocs non accetta estensioni in maiuscolo)
	fileName = fileName.toLowerCase();
	//aggiungo in testa il timestamp (legaldocs non accetta file con lo stesso nome nel pacchetto di archiviazione)
	fileName = "" + System.currentTimeMillis() + "_" + fileName;
	//rimuovo tutti i caratteri non ammessi (alcuni caratteri non sono ammessi nel file xml di indice)
	char c = "_".charAt(0);
	fileName = fileName.replaceAll("[^\\p{ASCII}]", "_");
	String caratteriNonAmmessi = vad.getCaratteriNonAmmessi();
	if (StringUtils.isNotBlank(caratteriNonAmmessi)) {
	    for (int i = 0; i < caratteriNonAmmessi.length(); i++) {
		fileName = fileName.replace(caratteriNonAmmessi.charAt(i), c);
	    }
	}
	//limito la lunghezza del nome del file (legaldocs non accetta nomi di file più lunghi di 76 caratteri)
	Integer maxFileNameLength = vad.getMaxFileNameLength();
	if (maxFileNameLength != null) {
	    if (fileName.length() > maxFileNameLength) {
		fileName = accorciaFileName(fileName, maxFileNameLength);
	    }
	}
	//modifico le estensioni tipo .pdf.p7m.p7m in .pdf.p7m (per poter archiviare anche questo tipo di file)
	if (vad.getFileExtensionsReplacement() != null) {
	    for (ChiaveValoreBean<String, String> cvb : vad.getFileExtensionsReplacement()) {
		if (fileName.indexOf(cvb.getChiave()) != -1) {
		    fileName = fileName.replaceFirst(cvb.getChiave(), cvb.getValore());
		    break;
		}
	    }
	}
	return fileName;
    }

    private String bonificaStringaDaiCaratteriNonAmmessi(String stringaDaBonificare, VerticalizzazioneArchiviazioneDocumentale vad) {

	String stringaBonificata = stringaDaBonificare;
	String caratteriNonAmmessi = vad.getCaratteriNonAmmessi();
	char c = "_".charAt(0);
	stringaDaBonificare = stringaDaBonificare.replaceAll("[^\\p{ASCII}]", "_");
	if (StringUtils.isNotBlank(caratteriNonAmmessi)) {
	    for (int i = 0; i < caratteriNonAmmessi.length(); i++) {
		stringaBonificata = stringaBonificata.replace(caratteriNonAmmessi.charAt(i), c);
	    }
	}
	return stringaBonificata;
    }

    private String accorciaFileName(String fileName, Integer maxSize) {

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
}
