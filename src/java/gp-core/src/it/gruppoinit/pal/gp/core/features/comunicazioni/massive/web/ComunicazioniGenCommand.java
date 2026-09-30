package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

public class ComunicazioniGenCommand extends ComunicazioniCommissioniCommand {

    //MERCATI
    private String tipoDestinatario;
    private String manifestazioneRadio;
    private String dallaDataM;
    private String allaDataM;
    private String tipoInvioMercato;
    private int[] idsmercato;
    private String tipoAnagrafe;
    
    //ISTANZE
    private boolean isIstanzeGroup;
    private boolean isRichiedente;
    private boolean isIntermediario;
    private String tipoInvioIstanze;
    
    //GEN
    private String oggettoEmail;
    private String bodyEmail;
    private boolean scegliMailChckN;
    private boolean scegliAppioChckN;
    private String scegliMovimento;
    private it.gruppoinit.pal.gp.core.domain.Movimenti movimento;
    private boolean codiciComuneUguale;
    private String codicecomuneComunicazione;
    

    public ComunicazioniGenCommand() {

	super();
	this.movimento = new it.gruppoinit.pal.gp.core.domain.Movimenti();
    }
 
    public String getScegliMovimento() {
    
        return scegliMovimento;
    }

    
    public void setScegliMovimento(String scegliMovimento) {
    
        this.scegliMovimento = scegliMovimento;
    }

    
    public it.gruppoinit.pal.gp.core.domain.Movimenti getMovimento() {
    
        return movimento;
    }

    
    public void setMovimento(it.gruppoinit.pal.gp.core.domain.Movimenti movimento) {
    
        this.movimento = movimento;
    }

    public boolean isScegliMailChckN() {
    
        return scegliMailChckN;
    }

    
    public void setScegliMailChckN(boolean scegliMailChckN) {
    
        this.scegliMailChckN = scegliMailChckN;
    }

    
    public boolean isScegliAppioChckN() {
    
        return scegliAppioChckN;
    }

    
    public void setScegliAppioChckN(boolean scegliAppioChckN) {
    
        this.scegliAppioChckN = scegliAppioChckN;
    }

    public String getTipoDestinatario() {
    
        return tipoDestinatario;
    }

    
    public void setTipoDestinatario(String tipoDestinatario) {
    
        this.tipoDestinatario = tipoDestinatario;
    }

    
    public String getManifestazioneRadio() {
    
        return manifestazioneRadio;
    }

    
    public void setManifestazioneRadio(String manifestazioneRadio) {
    
        this.manifestazioneRadio = manifestazioneRadio;
    }

    
    public String getDallaDataM() {
    
        return dallaDataM;
    }

    
    public void setDallaDataM(String dallaDataM) {
    
        this.dallaDataM = dallaDataM;
    }

    
    public String getAllaDataM() {
    
        return allaDataM;
    }

    
    public void setAllaDataM(String allaDataM) {
    
        this.allaDataM = allaDataM;
    }

    
    public String getTipoInvioMercato() {
    
        return tipoInvioMercato;
    }

    
    public void setTipoInvioMercato(String tipoInvioMercato) {
    
        this.tipoInvioMercato = tipoInvioMercato;
    }

    
    public int[] getIdsmercato() {
    
        return idsmercato;
    }

    
    public void setIdsmercato(int[] idsmercato) {
    
        this.idsmercato = idsmercato;
    }

    
    public String getOggettoEmail() {
    
        return oggettoEmail;
    }

    
    public void setOggettoEmail(String oggettoEmail) {
    
        this.oggettoEmail = oggettoEmail;
    }

    
    public String getBodyEmail() {
    
        return bodyEmail;
    }

    
    public void setBodyEmail(String bodyEmail) {
    
        this.bodyEmail = bodyEmail;
    }

    
    public String getTipoAnagrafe() {
    
        return tipoAnagrafe;
    }

    
    public void setTipoAnagrafe(String tipoAnagrafe) {
    
        this.tipoAnagrafe = tipoAnagrafe;
    }

    
    public boolean isIstanzeGroup() {
    
        return isIstanzeGroup;
    }

    
    public void setIstanzeGroup(boolean isIstanzeGroup) {
    
        this.isIstanzeGroup = isIstanzeGroup;
    }

    
    public boolean isRichiedente() {
    
        return isRichiedente;
    }

    
    public void setRichiedente(boolean isRichiedente) {
    
        this.isRichiedente = isRichiedente;
    }

    
    public boolean isIntermediario() {
    
        return isIntermediario;
    }

    
    public void setIntermediario(boolean isIntermediario) {
    
        this.isIntermediario = isIntermediario;
    }

    
    public String getTipoInvioIstanze() {
    
        return tipoInvioIstanze;
    }

    
    public void setTipoInvioIstanze(String tipoInvioIstanze) {
    
        this.tipoInvioIstanze = tipoInvioIstanze;
    }

    
    public boolean isCodiciComuneUguale() {
    
        return codiciComuneUguale;
    }

    
    public void setCodiciComuneUguale(boolean codiciComuneUguale) {
    
        this.codiciComuneUguale = codiciComuneUguale;
    }

    
    public String getCodicecomuneComunicazione() {
    
        return codicecomuneComunicazione;
    }

    
    public void setCodicecomuneComunicazione(String codicecomuneComunicazione) {
    
        this.codicecomuneComunicazione = codicecomuneComunicazione;
    }            
    
}
