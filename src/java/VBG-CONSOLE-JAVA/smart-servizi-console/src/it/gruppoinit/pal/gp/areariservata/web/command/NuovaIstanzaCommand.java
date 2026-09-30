package it.gruppoinit.pal.gp.areariservata.web.command;

import it.gruppoinit.pal.gp.areariservata.domain.AltriSoggettiTypeHelper;
import it.gruppoinit.pal.gp.areariservata.domain.EstremiAttoTypeHelper;
import it.gruppoinit.pal.gp.areariservata.domain.Informativa;
import it.gruppoinit.pal.gp.areariservata.domain.ProcedimentiHelper;
import it.gruppoinit.pal.gp.areariservata.domain.ProcedimentoHelper;
import it.gruppoinit.pal.gp.areariservata.web.util.StepsHelper;
import it.gruppoinit.pal.gp.core.domain.DocumentoHelper;
import it.gruppoinit.pal.gp.core.domain.FoArjServizi;
import it.gruppoinit.pal.gp.core.domain.SchedaHelper;
import it.gruppoinit.pal.gp.core.domain.StcDomainHelper;
import it.init.sigepro.rte.types.AltriSoggettiType;
import it.init.sigepro.rte.types.InterventoType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.PDFSchedaDinamicaType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RiferimentoCatastaleType;
import it.init.sigepro.rte.types.RuoloType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class NuovaIstanzaCommand {

    private Integer id;
    private Integer stepId;
    private String idDomanda;
    private Informativa informativa;
    private RichiedenteType richiedente;
    private RuoloType ruoloRichiedente;
    private PersonaGiuridicaType aziendaRichiedente;
    private PersonaFisicaType intermediario;
    private AltriSoggettiType altroSoggettoPF;
    private AltriSoggettiType altroSoggettoPG;
    private AltriSoggettiTypeHelper altroSoggettoPFH;
    private AltriSoggettiTypeHelper altroSoggettoPGH;
    private Map<Integer, AltriSoggettiType> altriSoggetti;
    private List<AltriSoggettiTypeHelper> altriSoggettiHelper;
    private LocalizzazioneNelComuneType localizzazione;
    private RiferimentoCatastaleType catasto;
    private Map<Integer, RiferimentoCatastaleType> riferimentiCatastali;
    private InterventoType intervento;
    private String oggetto;
    private Integer interventoProcedimenti;
//    private DomandaOneriHelper domandaOneriHelper;
    private ProcedimentiHelper procedimentiHelper;
    private List<ProcedimentoHelper> procedimentiSelezionati;
    private List<EstremiAttoTypeHelper> estremiAtto;
    private List<ProcedimentoHelper> allegatiProcedimenti;
    private List<DocumentoHelper> allegatiProcedimentiCaricati;
    private List<DocumentoHelper> allegatiIntervento;
    private List<DocumentoHelper> allegatiInterventoCaricati;
    private List<SchedaHelper> schede;
    private List<PDFSchedaDinamicaType> listaPDFSchede;
    private boolean richiedenteConfirmed;
    private boolean tipoSoggettoConfirmed;
    private boolean aziendaRichiedenteConfirmed;
    private boolean intermediarioConfirmed;
    private boolean showDettaglioRichiedente;
    private boolean showRuoloRichiedente;
    private boolean showAziendaRichiedente;
    private boolean showDettaglioAziendaRichiedente;
    private boolean showAltriSoggetti;
    private boolean showCercaAltroSoggettoPF;
    private boolean showDettaglioAltroSoggettoPF;
    private boolean showCercaAltroSoggettoPG;
    private boolean showDettaglioAltroSoggettoPG;
    private boolean showCercaIntermediario;
    private boolean showDettaglioIntermediario;
    private String descrizioneRuoloAltri;
    private boolean showDescrizioneRuoloAltri;
    private CommonsMultipartFile file;
    private DocumentoHelper documentoCaricato;
    private Integer idRiepilogo;
    private StepsHelper stepsHelper;
    private boolean domandaDaIntervento;
    private FoArjServizi servizio;
    private String domicilioElettronico;
    private String domicilioElettronicoTemp;
    private String lineaComunecodiceTransazioneReport;
    private List<Integer> fileUploadDaCancellare;

    public NuovaIstanzaCommand(StepsHelper stepsHelper, boolean domandaDaIntervento, FoArjServizi servizio) {

	this.domandaDaIntervento = domandaDaIntervento;
	this.servizio = servizio;
	this.stepsHelper = stepsHelper;
	//
	this.informativa = new Informativa();
	//
	this.richiedente = StcDomainHelper.getNewRichiedenteType();
	this.ruoloRichiedente = new RuoloType();
	this.aziendaRichiedente = StcDomainHelper.getNewPersonaGiuridicaType();
	this.intermediario = StcDomainHelper.getNewPersonaFisicaType();
	this.altroSoggettoPF = StcDomainHelper.getNewAltriSoggettiType();
	this.altroSoggettoPG = StcDomainHelper.getNewAltriSoggettiType();
	this.altroSoggettoPFH = new AltriSoggettiTypeHelper(StcDomainHelper.getNewAltriSoggettiType());
	this.altroSoggettoPGH = new AltriSoggettiTypeHelper(StcDomainHelper.getNewAltriSoggettiType());
	this.altriSoggetti = new HashMap<Integer, AltriSoggettiType>();
	this.altriSoggettiHelper = new ArrayList<AltriSoggettiTypeHelper>();
	//
	this.localizzazione = StcDomainHelper.getNewLocalizzazioneNelComuneType();
	this.catasto = new RiferimentoCatastaleType();
	this.riferimentiCatastali = new HashMap<Integer, RiferimentoCatastaleType>();
	//
	this.intervento = new InterventoType();
	//
	this.procedimentiSelezionati = new ArrayList<ProcedimentoHelper>();
	this.setEstremiAtto(new ArrayList<EstremiAttoTypeHelper>());
	//
	this.allegatiProcedimenti = new ArrayList<ProcedimentoHelper>();
	this.allegatiProcedimentiCaricati = new ArrayList<DocumentoHelper>();
	this.documentoCaricato = new DocumentoHelper(StcDomainHelper.getNewDocumentiType());
	this.allegatiProcedimenti = new ArrayList<ProcedimentoHelper>();
	this.allegatiIntervento = new ArrayList<DocumentoHelper>();
	this.allegatiInterventoCaricati = new ArrayList<DocumentoHelper>();
	//
	this.schede = new ArrayList<SchedaHelper>();
	this.listaPDFSchede = new ArrayList<PDFSchedaDinamicaType>();
	this.fileUploadDaCancellare = new ArrayList<Integer>();
    }

    
    public List<PDFSchedaDinamicaType> getListaPDFSchede() {
    
        return listaPDFSchede;
    }

    
    public void setListaPDFSchede(List<PDFSchedaDinamicaType> listaPDFSchede) {
    
        this.listaPDFSchede = listaPDFSchede;
    }

    public Informativa getInformativa() {

	return informativa;
    }

    public void setInformativa(Informativa informativa) {

	this.informativa = informativa;
    }

    public RichiedenteType getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(RichiedenteType richiedente) {

	this.richiedente = richiedente;
    }

    public PersonaGiuridicaType getAziendaRichiedente() {

	return aziendaRichiedente;
    }

    public void setAziendaRichiedente(PersonaGiuridicaType aziendaRichiedente) {

	this.aziendaRichiedente = aziendaRichiedente;
    }

    public boolean isRichiedenteConfirmed() {

	return richiedenteConfirmed;
    }

    public void setRichiedenteConfirmed(boolean richiedenteConfirmed) {

	this.richiedenteConfirmed = richiedenteConfirmed;
    }

    public boolean isTipoSoggettoConfirmed() {

	return tipoSoggettoConfirmed;
    }

    public void setTipoSoggettoConfirmed(boolean tipoSoggettoConfirmed) {

	this.tipoSoggettoConfirmed = tipoSoggettoConfirmed;
    }

    public boolean isAziendaRichiedenteConfirmed() {

	return aziendaRichiedenteConfirmed;
    }

    public void setAziendaRichiedenteConfirmed(boolean aziendaRichiedenteConfirmed) {

	this.aziendaRichiedenteConfirmed = aziendaRichiedenteConfirmed;
    }

    public boolean isShowDettaglioRichiedente() {

	return showDettaglioRichiedente;
    }

    public void setShowDettaglioRichiedente(boolean showDettaglioRichiedente) {

	this.showDettaglioRichiedente = showDettaglioRichiedente;
    }

    public boolean isShowRuoloRichiedente() {

	return showRuoloRichiedente;
    }

    public void setShowRuoloRichiedente(boolean showRuoloRichiedente) {

	this.showRuoloRichiedente = showRuoloRichiedente;
    }

    public boolean isShowAziendaRichiedente() {

	return showAziendaRichiedente;
    }

    public void setShowAziendaRichiedente(boolean showAziendaRichiedente) {

	this.showAziendaRichiedente = showAziendaRichiedente;
    }

    public boolean isShowDettaglioAziendaRichiedente() {

	return showDettaglioAziendaRichiedente;
    }

    public void setShowDettaglioAziendaRichiedente(boolean showDettaglioAziendaRichiedente) {

	this.showDettaglioAziendaRichiedente = showDettaglioAziendaRichiedente;
    }

    public boolean isShowAltriSoggetti() {

	return showAltriSoggetti;
    }

    public void setShowAltriSoggetti(boolean showAltriSoggetti) {

	this.showAltriSoggetti = showAltriSoggetti;
    }

    public RuoloType getRuoloRichiedente() {

	return ruoloRichiedente;
    }

    public void setRuoloRichiedente(RuoloType ruoloRichiedente) {

	this.ruoloRichiedente = ruoloRichiedente;
    }

    public AltriSoggettiType getAltroSoggettoPF() {

	return altroSoggettoPF;
    }

    public void setAltroSoggettoPF(AltriSoggettiType altroSoggettoPF) {

	this.altroSoggettoPF = altroSoggettoPF;
    }

    public AltriSoggettiType getAltroSoggettoPG() {

	return altroSoggettoPG;
    }

    public void setAltroSoggettoPG(AltriSoggettiType altroSoggettoPG) {

	this.altroSoggettoPG = altroSoggettoPG;
    }

    public boolean isShowDettaglioAltroSoggettoPF() {

	return showDettaglioAltroSoggettoPF;
    }

    public void setShowDettaglioAltroSoggettoPF(boolean showDettaglioAltroSoggettoPF) {

	this.showDettaglioAltroSoggettoPF = showDettaglioAltroSoggettoPF;
    }

    public boolean isShowCercaAltroSoggettoPF() {

	return showCercaAltroSoggettoPF;
    }

    public void setShowCercaAltroSoggettoPF(boolean showCercaAltroSoggettoPF) {

	this.showCercaAltroSoggettoPF = showCercaAltroSoggettoPF;
    }

    public Map<Integer, AltriSoggettiType> getAltriSoggetti() {

	return altriSoggetti;
    }

    public void setAltriSoggetti(Map<Integer, AltriSoggettiType> altriSoggetti) {

	this.altriSoggetti = altriSoggetti;
    }

    public boolean isShowCercaAltroSoggettoPG() {

	return showCercaAltroSoggettoPG;
    }

    public void setShowCercaAltroSoggettoPG(boolean showCercaAltroSoggettoPG) {

	this.showCercaAltroSoggettoPG = showCercaAltroSoggettoPG;
    }

    public boolean isShowDettaglioAltroSoggettoPG() {

	return showDettaglioAltroSoggettoPG;
    }

    public void setShowDettaglioAltroSoggettoPG(boolean showDettaglioAltroSoggettoPG) {

	this.showDettaglioAltroSoggettoPG = showDettaglioAltroSoggettoPG;
    }

    public PersonaFisicaType getIntermediario() {

	return intermediario;
    }

    public void setIntermediario(PersonaFisicaType intermediario) {

	this.intermediario = intermediario;
    }

    public boolean isIntermediarioConfirmed() {

	return intermediarioConfirmed;
    }

    public void setIntermediarioConfirmed(boolean intermediarioConfirmed) {

	this.intermediarioConfirmed = intermediarioConfirmed;
    }

    public boolean isShowDettaglioIntermediario() {

	return showDettaglioIntermediario;
    }

    public void setShowDettaglioIntermediario(boolean showDettaglioIntermediario) {

	this.showDettaglioIntermediario = showDettaglioIntermediario;
    }

    public boolean isShowCercaIntermediario() {

	return showCercaIntermediario;
    }

    public void setShowCercaIntermediario(boolean showCercaIntermediario) {

	this.showCercaIntermediario = showCercaIntermediario;
    }

    public LocalizzazioneNelComuneType getLocalizzazione() {

	return localizzazione;
    }

    public void setLocalizzazione(LocalizzazioneNelComuneType localizzazione) {

	this.localizzazione = localizzazione;
    }

    public RiferimentoCatastaleType getCatasto() {

	return catasto;
    }

    public void setCatasto(RiferimentoCatastaleType catasto) {

	this.catasto = catasto;
    }

    public Map<Integer, RiferimentoCatastaleType> getRiferimentiCatastali() {

	return riferimentiCatastali;
    }

    public void setRiferimentiCatastali(Map<Integer, RiferimentoCatastaleType> riferimentiCatastali) {

	this.riferimentiCatastali = riferimentiCatastali;
    }

    public InterventoType getIntervento() {

	return intervento;
    }

    public void setIntervento(InterventoType intervento) {

	this.intervento = intervento;
    }

    public ProcedimentiHelper getProcedimentiHelper() {

	return procedimentiHelper;
    }

    public void setProcedimentiHelper(ProcedimentiHelper procedimentiHelper) {

	this.procedimentiHelper = procedimentiHelper;
    }

    public Integer getInterventoProcedimenti() {

	return interventoProcedimenti;
    }

    public void setInterventoProcedimenti(Integer interventoProcedimenti) {

	this.interventoProcedimenti = interventoProcedimenti;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public List<ProcedimentoHelper> getProcedimentiSelezionati() {

	return procedimentiSelezionati;
    }

    public void setProcedimentiSelezionati(List<ProcedimentoHelper> procedimentiSelezionati) {

	this.procedimentiSelezionati = procedimentiSelezionati;
    }

    public Integer getStepId() {

	return stepId;
    }

    public void setStepId(Integer stepId) {

	this.stepId = stepId;
    }

    public CommonsMultipartFile getFile() {

	return file;
    }

    public void setFile(CommonsMultipartFile file) {

	this.file = file;
    }

    public List<ProcedimentoHelper> getAllegatiProcedimenti() {

	return allegatiProcedimenti;
    }

    public void setAllegatiProcedimenti(List<ProcedimentoHelper> allegatiProcedimenti) {

	this.allegatiProcedimenti = allegatiProcedimenti;
    }

    public List<DocumentoHelper> getAllegatiProcedimentiCaricati() {

	return allegatiProcedimentiCaricati;
    }

    public void setAllegatiProcedimentiCaricati(List<DocumentoHelper> allegatiProcedimentiCaricati) {

	this.allegatiProcedimentiCaricati = allegatiProcedimentiCaricati;
    }

    public DocumentoHelper getDocumentoCaricato() {

	return documentoCaricato;
    }

    public void setDocumentoCaricato(DocumentoHelper documentoCaricato) {

	this.documentoCaricato = documentoCaricato;
    }

    public List<DocumentoHelper> getAllegatiIntervento() {

	return allegatiIntervento;
    }

    public void setAllegatiIntervento(List<DocumentoHelper> allegatiIntervento) {

	this.allegatiIntervento = allegatiIntervento;
    }

    public List<DocumentoHelper> getAllegatiInterventoCaricati() {

	return allegatiInterventoCaricati;
    }

    public void setAllegatiInterventoCaricati(List<DocumentoHelper> allegatiInterventoCaricati) {

	this.allegatiInterventoCaricati = allegatiInterventoCaricati;
    }

    public List<SchedaHelper> getSchede() {

	return schede;
    }

    public void setSchede(List<SchedaHelper> schede) {

	this.schede = schede;
    }

    public String getIdDomanda() {

	return idDomanda;
    }

    public void setIdDomanda(String idDomanda) {

	this.idDomanda = idDomanda;
    }

    public List<AltriSoggettiTypeHelper> getAltriSoggettiHelper() {

	return altriSoggettiHelper;
    }

    public void setAltriSoggettiHelper(List<AltriSoggettiTypeHelper> altriSoggettiHelper) {

	this.altriSoggettiHelper = altriSoggettiHelper;
    }

    public AltriSoggettiTypeHelper getAltroSoggettoPFH() {

	return altroSoggettoPFH;
    }

    public void setAltroSoggettoPFH(AltriSoggettiTypeHelper altroSoggettoPFH) {

	this.altroSoggettoPFH = altroSoggettoPFH;
    }

    public AltriSoggettiTypeHelper getAltroSoggettoPGH() {

	return altroSoggettoPGH;
    }

    public void setAltroSoggettoPGH(AltriSoggettiTypeHelper altroSoggettoPGH) {

	this.altroSoggettoPGH = altroSoggettoPGH;
    }

    public String getDescrizioneRuoloAltri() {

	return descrizioneRuoloAltri;
    }

    public void setDescrizioneRuoloAltri(String descrizioneRuoloAltri) {

	this.descrizioneRuoloAltri = descrizioneRuoloAltri;
    }

    public boolean isShowDescrizioneRuoloAltri() {

	return showDescrizioneRuoloAltri;
    }

    public void setShowDescrizioneRuoloAltri(boolean showDescrizioneRuoloAltri) {

	this.showDescrizioneRuoloAltri = showDescrizioneRuoloAltri;
    }

    public StepsHelper getStepsHelper() {

	return stepsHelper;
    }

    public void setStepsHelper(StepsHelper stepsHelper) {

	this.stepsHelper = stepsHelper;
    }

    public Integer getIdRiepilogo() {

	return idRiepilogo;
    }

    public void setIdRiepilogo(Integer idRiepilogo) {

	this.idRiepilogo = idRiepilogo;
    }

    //    public DomandaOneriHelper getDomandaOneriHelper() {
    //
    //	return domandaOneriHelper;
    //    }
    //
    //    public void setDomandaOneriHelper(DomandaOneriHelper domandaOneriHelper) {
    //
    //	this.domandaOneriHelper = domandaOneriHelper;
    //    }
    public boolean isDomandaDaIntervento() {

	return domandaDaIntervento;
    }

    public void setDomandaDaIntervento(boolean domandaDaIntervento) {

	this.domandaDaIntervento = domandaDaIntervento;
    }

    public FoArjServizi getServizio() {

	return servizio;
    }

    public List<EstremiAttoTypeHelper> getEstremiAtto() {

	return estremiAtto;
    }

    public void setEstremiAtto(List<EstremiAttoTypeHelper> estremiAtto) {

	this.estremiAtto = estremiAtto;
    }

    public String getDomicilioElettronico() {

	return domicilioElettronico;
    }

    public void setDomicilioElettronico(String domicilioElettronico) {

	this.domicilioElettronico = domicilioElettronico;
    }

    public String getDomicilioElettronicoTemp() {

	return domicilioElettronicoTemp;
    }

    public void setDomicilioElettronicoTemp(String domicilioElettronicoTemp) {

	this.domicilioElettronicoTemp = domicilioElettronicoTemp;
    }


    public String getLineaComunecodiceTransazioneReport() {

	return lineaComunecodiceTransazioneReport;
    }


    public void setLineaComunecodiceTransazioneReport(String lineaComunecodiceTransazioneReport) {

	this.lineaComunecodiceTransazioneReport = lineaComunecodiceTransazioneReport;
    }


    
    public List<Integer> getFileUploadDaCancellare() {
    
        return fileUploadDaCancellare;
    }


    
    public void setFileUploadDaCancellare(List<Integer> fileUploadDaCancellare) {
    
        this.fileUploadDaCancellare = fileUploadDaCancellare;
    }
}
