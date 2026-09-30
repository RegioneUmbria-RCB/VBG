package it.gruppoinit.pal.gp.core.domain.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.protocollo.schemas.messages.AllegatoType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;

public class ProtocollazioneCommand extends BaseCommand {

    public static final String FLUSSO_ARRIVO = "A";
    public static final String FLUSSO_INTERNO = "I";
    public static final String FLUSSO_PARTENZA = "P";
    public static final String PROVENIENZA_ISTANZA = "I";
    public static final String PROVENIENZA_MOVIMENTI = "M";
    public static final String PROVENIENZA_AUTORIZZAZIONI = "A";
    public static final String PROVENIENZA_PEC = "P";
    public static final String PROVENIENZA_AZIONIPROTOCOLLO = "AZP";
    public static final String PROVENIENZA_COMUNICAZIONI_MASSIVE = "CM";
    private Istanze entity;
    private Movimenti movimento;
    private PecInbox pec;
    private PECCommand pecCommand;
    private String numeroProtocolloMittente;
    private Date dataProtocolloMittente;
    private String oggetto;
    private String oggettoProtocolloMail;
    private String corpoProtocolloMail;
    private String registroDocEr;
    private String classifica;
    private String flusso;
    private String tipoDocumento;
    private String smistamento;
    private String numeroFascicolo;
    private Date dataFascicolo;
    private String classificaFascicolo;
    private String oggettoFascicolo;
    private Integer annoFascicolo;
    private String provenienza;
    private Comuni comune;
    private Software protSoftware;
    private boolean registrazioneParticolareDocEr;
    private String classificaFascicoloAlberoIntervento;
    private String numeroFascicoloAlberoIntervento;
    private String annoFascicoloProtocolloRicerca;
    private boolean forzaNonFascicolareInProtocollazioneXML = false;
    private List<ProtocolloSoggettoCommand> mittentis = new ArrayList<ProtocolloSoggettoCommand>();
    private List<ProtocolloSoggettoCommand> destinataris = new ArrayList<ProtocolloSoggettoCommand>();
    private ProtocolloSoggettoCommand mittente;
    private ProtocolloSoggettoCommand destinatario;
    private List<AllegatoType> allegatiGenerici = new ArrayList<AllegatoType>();
    private DocumentiHelper documentiHelper;
    private String token;
    private DatiProtocolloResponseType datiProtocollo;
    private String motivoAnnullamento;
    private String noteAnnullamento;
    private String stampante;
    private Integer numeroCopie;
    private Boolean mettiAllaFirma;
    private String documentoPrincipale;
    // Pamaetro che mi permette di specificare se l'inserimento è fatto
    // auomaticamnete senza passare per la maschera web 
    private Boolean isInserimentoAutomatico;
    // Proprietà per la gestione dell'invio dei documenti come link in fase di invio PEC
    private boolean isMostraSezioneConfigurazione;
    private Boolean flgProtocollalinkall;
    private Letteretipo letteraTipoAllegati;
    private Boolean flgProtocollaZipLogico;
    private List<MetadatiBean> metadati;

    public ProtocollazioneCommand() {

	this.entity = new Istanze();
	this.movimento = new Movimenti();
	this.mittente = ProtocolloSoggettoCommand.fromGeneric();
	this.destinatario = ProtocolloSoggettoCommand.fromGeneric();
	this.datiProtocollo = new DatiProtocolloResponseType();
	this.comune = new Comuni();
	this.protSoftware = new Software();
	this.isMostraSezioneConfigurazione = false;
	this.letteraTipoAllegati = new Letteretipo();
	this.metadati = new ArrayList<MetadatiBean>(0);
    }

    public Istanze getEntity() {

	return entity;
    }

    public void setEntity(Istanze entity) {

	this.entity = entity;
    }

    public Movimenti getMovimento() {

	return movimento;
    }

    public void setMovimento(Movimenti movimento) {

	this.movimento = movimento;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }
    
    public String getOggettoProtocolloMail() {
    
        return oggettoProtocolloMail;
    }

    
    public void setOggettoProtocolloMail(String oggettoProtocolloMail) {
    
        this.oggettoProtocolloMail = oggettoProtocolloMail;
    }

    public String getCorpoProtocolloMail() {
    
        return corpoProtocolloMail;
    }

    public void setCorpoProtocolloMail(String corpoProtocolloMail) {
    
        this.corpoProtocolloMail = corpoProtocolloMail;
    }

    public String getClassifica() {

	return classifica;
    }

    public void setClassifica(String classifica) {

	this.classifica = classifica;
    }

    public String getFlusso() {

	return flusso;
    }

    public void setFlusso(String flusso) {

	this.flusso = flusso;
    }

    public String getTipoDocumento() {

	return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {

	this.tipoDocumento = tipoDocumento;
    }

    public String getSmistamento() {

	return smistamento;
    }

    public void setSmistamento(String smistamento) {

	this.smistamento = smistamento;
    }

    public String getNumeroProtocolloMittente() {

	return numeroProtocolloMittente;
    }

    public void setNumeroProtocolloMittente(String numeroProtocolloMittente) {

	this.numeroProtocolloMittente = numeroProtocolloMittente;
    }

    public Date getDataProtocolloMittente() {

	return dataProtocolloMittente;
    }

    public void setDataProtocolloMittente(Date dataProtocolloMittente) {

	this.dataProtocolloMittente = dataProtocolloMittente;
    }

    public String getNumeroFascicolo() {

	return numeroFascicolo;
    }

    public void setNumeroFascicolo(String numeroFascicolo) {

	this.numeroFascicolo = numeroFascicolo;
    }

    public Date getDataFascicolo() {

	return dataFascicolo;
    }

    public void setDataFascicolo(Date dataFascicolo) {

	this.dataFascicolo = dataFascicolo;
    }

    public String getClassificaFascicolo() {

	return classificaFascicolo;
    }

    public void setClassificaFascicolo(String classificaFascicolo) {

	this.classificaFascicolo = classificaFascicolo;
    }

    public String getOggettoFascicolo() {

	return oggettoFascicolo;
    }

    public void setOggettoFascicolo(String oggettoFascicolo) {

	this.oggettoFascicolo = oggettoFascicolo;
    }

    public Integer getAnnoFascicolo() {

	return annoFascicolo;
    }

    public void setAnnoFascicolo(Integer annoFascicolo) {

	this.annoFascicolo = annoFascicolo;
    }

    public List<ProtocolloSoggettoCommand> getMittentis() {

	return mittentis;
    }

    public void setMittentis(List<ProtocolloSoggettoCommand> mittentis) {

	this.mittentis = mittentis;
    }

    public List<ProtocolloSoggettoCommand> getDestinataris() {

	return destinataris;
    }

    public void setDestinataris(List<ProtocolloSoggettoCommand> destinataris) {

	this.destinataris = destinataris;
    }

    public ProtocolloSoggettoCommand getMittente() {

	return mittente;
    }

    public void setMittente(ProtocolloSoggettoCommand mittente) {

	this.mittente = mittente;
    }

    public ProtocolloSoggettoCommand getDestinatario() {

	return destinatario;
    }

    public void setDestinatario(ProtocolloSoggettoCommand destinatario) {

	this.destinatario = destinatario;
    }

    public String getProvenienza() {

	return provenienza;
    }

    public void setProvenienza(String provenienza) {

	this.provenienza = provenienza;
    }

    //    public List<IstanzeallegatiValoreBean> getIstanzeAllegatiList() {
    //
    //	if (istanzeAllegatiList.size() == 0) {
    //	    if (this.getEntity() != null) {
    //		Istanze istanza = getEntity();
    //		Set<Istanzeallegati> istanzeallegatis = istanza.getIstanzeallegatis();
    //		int index = 0;
    //		for (Istanzeallegati istanzeallegati : istanzeallegatis) {
    //		    if (istanzeallegati.getOggetto() != null) {
    //			if (istanzeallegati.getOggetto().getId() != null) {
    //			    if (istanzeallegati.getOggetto().getId().getCodice() != null) {
    //				IstanzeallegatiValoreBean bean = new IstanzeallegatiValoreBean();
    //				bean.setChiave(istanzeallegati);
    //				bean.setValore(false);
    //				istanzeAllegatiList.add(index, bean);
    //				index++;
    //			    }
    //			}
    //		    }
    //		}
    //		Collections.sort(istanzeAllegatiList, new IstanzeallegatiAllegatoExtraComparator());
    //	    }
    //	}
    //	return istanzeAllegatiList;
    //    }
    //
    //    public void setIstanzeAllegatiList(List<IstanzeallegatiValoreBean> istanzeAllegatiList) {
    //
    //	this.istanzeAllegatiList = istanzeAllegatiList;
    //    }
    //
    //    public List<DocumentiistanzaValoreBean> getDocumentiistanzaList() {
    //
    //	if (documentiistanzaList.size() == 0) {
    //	    if (this.getEntity() != null) {
    //		Istanze istanza = this.getEntity();
    //		Set<Documentiistanza> Documentiistanzas = istanza.getDocumentiistanzas();
    //		int index = 0;
    //		for (Documentiistanza documentiistanza : Documentiistanzas) {
    //		    if (documentiistanza.getOggetto() != null) {
    //			if (documentiistanza.getOggetto().getId() != null) {
    //			    if (documentiistanza.getOggetto().getId().getCodice() != null) {
    //				DocumentiistanzaValoreBean bean = new DocumentiistanzaValoreBean();
    //				bean.setChiave(documentiistanza);
    //				bean.setValore(false);
    //				documentiistanzaList.add(index, bean);
    //				index++;
    //			    }
    //			}
    //		    }
    //		}
    //		Collections.sort(documentiistanzaList, new DocumentiistanzaDocumentoComparator());
    //	    }
    //	}
    //	return documentiistanzaList;
    //    }
    //
    //    public void setDocumentiistanzaList(List<DocumentiistanzaValoreBean> documentiistanzaList) {
    //
    //	this.documentiistanzaList = documentiistanzaList;
    //    }
    //
    //    public List<MovimentiallegatiValoreBean> getMovimentiallegatiList() {
    //
    //	if (movimentiallegatiList.size() == 0) {
    //	    if (this.getMovimento() != null) {
    //		Set<Movimentiallegati> Movimentiallegatis = movimento.getMovimentiallegatis();
    //		int index = 0;
    //		for (Movimentiallegati movimentiallegati : Movimentiallegatis) {
    //		    if (movimentiallegati.getOggetto() != null) {
    //			if (movimentiallegati.getOggetto().getId() != null) {
    //			    if (movimentiallegati.getOggetto().getId().getCodice() != null) {
    //				MovimentiallegatiValoreBean bean = new MovimentiallegatiValoreBean();
    //				bean.setChiave(movimentiallegati);
    //				bean.setValore(true);
    //				movimentiallegatiList.add(index, bean);
    //				index++;
    //			    }
    //			}
    //		    }
    //		}
    //		Collections.sort(movimentiallegatiList, new MovimentiallegatiDescrizioneComparator());
    //	    }
    //	}
    //	return movimentiallegatiList;
    //    }
    //
    //    public void setMovimentiallegatiList(List<MovimentiallegatiValoreBean> movimentiallegatiList) {
    //
    //	this.movimentiallegatiList = movimentiallegatiList;
    //    }
    //
    //    // Utilizzato per settare e recuperare la lista di allegati dei vari movimenti appartenenti all'istanza
    //    public List<Movimentiallegati> getAltrimovimentiallegatiList() {
    //
    //	return altrimovimentiallegatiList;
    //    }
    //
    //    public void setAltrimovimentiallegatiList(List<Movimentiallegati> altrimovimentiallegatiList) {
    //
    //	this.altrimovimentiallegatiList = altrimovimentiallegatiList;
    //    }
    //
    //    // Utilizzato per ritornarnare una lista di allegati degli altri movimenti nel  formato : "MovimentiallegatiValoreBean"
    //    public List<MovimentiallegatiValoreBean> getAltrimovimentiallegatiValoreBeanList() {
    //
    //	if (altrimovimentiallegatiValoreBeanList.size() == 0) {
    //	    if (this.getMovimento() != null) {
    //		List<Movimentiallegati> Movimentiallegatis = this.getAltrimovimentiallegatiList();
    //		int index = 0;
    //		for (Movimentiallegati movimentiallegati : Movimentiallegatis) {
    //		    if (movimentiallegati.getOggetto() != null) {
    //			if (movimentiallegati.getOggetto().getId() != null) {
    //			    if (movimentiallegati.getOggetto().getId().getCodice() != null) {
    //				MovimentiallegatiValoreBean bean = new MovimentiallegatiValoreBean();
    //				bean.setChiave(movimentiallegati);
    //				bean.setValore(false);
    //				altrimovimentiallegatiValoreBeanList.add(index, bean);
    //				index++;
    //			    }
    //			}
    //		    }
    //		}
    //		Collections.sort(altrimovimentiallegatiValoreBeanList, new MovimentiallegatiDescrizioneComparator());
    //	    }
    //	}
    //	return altrimovimentiallegatiValoreBeanList;
    //    }
    //
    //    public void setAltrimovimentiallegatiValoreBeanList(List<MovimentiallegatiValoreBean> altrimovimentiallegatiValoreBeanList) {
    //
    //	this.altrimovimentiallegatiValoreBeanList = altrimovimentiallegatiValoreBeanList;
    //    }
    public DocumentiHelper getDocumentiHelper() {

	return documentiHelper;
    }

    public void setDocumentiHelper(DocumentiHelper documentiHelper) {

	this.documentiHelper = documentiHelper;
    }

    public void resetSoggetti() {

	this.mittentis = new ArrayList<ProtocolloSoggettoCommand>();
	this.destinataris = new ArrayList<ProtocolloSoggettoCommand>();
	this.mittente = ProtocolloSoggettoCommand.fromGeneric();
	this.destinatario = ProtocolloSoggettoCommand.fromGeneric();
    }

    public String getToken() {

	return token;
    }

    public void setToken(String token) {

	this.token = token;
    }

    public DatiProtocolloResponseType getDatiProtocollo() {

	return datiProtocollo;
    }

    public void setDatiProtocollo(DatiProtocolloResponseType datiProtocollo) {

	this.datiProtocollo = datiProtocollo;
    }

    public String getMotivoAnnullamento() {

	return motivoAnnullamento;
    }

    public void setMotivoAnnullamento(String motivoAnnullamento) {

	this.motivoAnnullamento = motivoAnnullamento;
    }

    public String getNoteAnnullamento() {

	return noteAnnullamento;
    }

    public void setNoteAnnullamento(String noteAnnullamento) {

	this.noteAnnullamento = noteAnnullamento;
    }

    public String getStampante() {

	return stampante;
    }

    public void setStampante(String stampante) {

	this.stampante = stampante;
    }

    public Integer getNumeroCopie() {

	return numeroCopie;
    }

    public void setNumeroCopie(Integer numeroCopie) {

	this.numeroCopie = numeroCopie;
    }

    public void setMettiAllaFirma(Boolean mettiAllaFirma) {

	this.mettiAllaFirma = mettiAllaFirma;
    }

    public Boolean getMettiAllaFirma() {

	return mettiAllaFirma;
    }

    public String getDocumentoPrincipale() {

	return documentoPrincipale;
    }

    public void setDocumentoPrincipale(String documentoPrincipale) {

	this.documentoPrincipale = documentoPrincipale;
    }

    public Boolean getInserimentoAutomatico() {

	return isInserimentoAutomatico;
    }

    public void setInserimentoAutomatico(Boolean isInserimentoAutomatico) {

	this.isInserimentoAutomatico = isInserimentoAutomatico;
    }

    public PecInbox getPec() {

	return pec;
    }

    public void setPec(PecInbox pec) {

	this.pec = pec;
    }

    public PECCommand getPecCommand() {

	return pecCommand;
    }

    public void setPecCommand(PECCommand pecCommand) {

	this.pecCommand = pecCommand;
	this.pec = pecCommand.getPec();
    }

    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    public Software getProtSoftware() {

	return protSoftware;
    }

    public void setProtSoftware(Software protSoftware) {

	this.protSoftware = protSoftware;
    }

    public String getRegistroDocEr() {

	return registroDocEr;
    }

    public void setRegistroDocEr(String registroDocEr) {

	this.registroDocEr = registroDocEr;
    }

    public boolean isRegistrazioneParticolareDocEr() {

	return registrazioneParticolareDocEr;
    }

    public void setRegistrazioneParticolareDocEr(boolean registrazioneParticolareDocEr) {

	this.registrazioneParticolareDocEr = registrazioneParticolareDocEr;
    }

    public boolean getIsMostraSezioneConfigurazione() {

	return isMostraSezioneConfigurazione;
    }

    public void setMostraSezioneConfigurazione(boolean isMostraSezioneConfigurazione) {

	this.isMostraSezioneConfigurazione = isMostraSezioneConfigurazione;
    }

    public Boolean getFlgProtocollalinkall() {

	return flgProtocollalinkall;
    }

    public void setFlgProtocollalinkall(Boolean flgProtocollalinkall) {

	this.flgProtocollalinkall = flgProtocollalinkall;
    }

    public Letteretipo getLetteraTipoAllegati() {

	return letteraTipoAllegati;
    }

    public void setLetteraTipoAllegati(Letteretipo letteraTipoAllegati) {

	this.letteraTipoAllegati = letteraTipoAllegati;
    }

    public String getClassificaFascicoloAlberoIntervento() {

	return classificaFascicoloAlberoIntervento;
    }

    public void setClassificaFascicoloAlberoIntervento(String classificaFascicoloAlberoIntervento) {

	this.classificaFascicoloAlberoIntervento = classificaFascicoloAlberoIntervento;
    }

    public String getNumeroFascicoloAlberoIntervento() {

	return numeroFascicoloAlberoIntervento;
    }

    public void setNumeroFascicoloAlberoIntervento(String numeroFascicoloAlberoIntervento) {

	this.numeroFascicoloAlberoIntervento = numeroFascicoloAlberoIntervento;
    }

    public String getAnnoFascicoloProtocolloRicerca() {

	return annoFascicoloProtocolloRicerca;
    }

    public void setAnnoFascicoloProtocolloRicerca(String annoFascicoloProtocolloRicerca) {

	this.annoFascicoloProtocolloRicerca = annoFascicoloProtocolloRicerca;
    }

    public Boolean getFlgProtocollaZipLogico() {

	return flgProtocollaZipLogico;
    }

    public void setFlgProtocollaZipLogico(Boolean flgProtocollaZipLogico) {

	this.flgProtocollaZipLogico = flgProtocollaZipLogico;
    }

    public List<AllegatoType> getAllegatiGenerici() {

	if (this.allegatiGenerici == null) {
	    this.allegatiGenerici = new ArrayList<AllegatoType>();
	}
	return allegatiGenerici;
    }

    public void setAllegatiGenerici(List<AllegatoType> allegatiGenerici) {

	this.allegatiGenerici = allegatiGenerici;
    }

    public boolean isForzaNonFascicolareInProtocollazioneXML() {

	return forzaNonFascicolareInProtocollazioneXML;
    }

    public void setForzaNonFascicolareInProtocollazioneXML(boolean forzaNonFascicolareInProtocollazioneXML) {

	this.forzaNonFascicolareInProtocollazioneXML = forzaNonFascicolareInProtocollazioneXML;
    }

    public List<MetadatiBean> getMetadati() {

	if (this.metadati == null) {
	    this.metadati = new ArrayList<MetadatiBean>(0);
	}
	return metadati;
    }

    public void setMetadati(List<MetadatiBean> metadati) {

	this.metadati = metadati;
    }
}
