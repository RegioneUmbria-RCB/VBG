package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;


public class RicalcoloAreeBean {
    
    public static final String EMPTY = "";
    public static final String DEF_FAS = "fas fa-sync-alt ricaricaRicalcoloAree";
    public static final String OK_FAS = "fas fa-check-circle";
    public static final String KO_FAS = "fas fa-times-circle";
    public static final String NO_ACC_FAS = "fas fa-minus-circle";
    public static final String DISABLED_BUTTON = "checkatoButton";
    public static final String STATUS_COMPLETATA = "COMPLETATA";
    public static final String IN_ERRORE = "IN ERRORE";
    public static final String CON_SCARTI = "ELABORATO CON SCARTI";
    public static final String IN_CORSO = "IN CORSO";
    public static final String ID_RICALCOLO_AREE = "idRicalcoloAree";
    public static final String BOLD_STYLE = "font-weight: bold;";
    
    private String id;
    private String sessionid;
    private String descrizione;
    private String stato;
    private String datafine;
    private String dafare = EMPTY;
    private String fatti = EMPTY;
    private String totali = EMPTY;
    private String boldStyle = EMPTY;
    
    
    private String classDisabledButton = EMPTY;
    private String fontAwesomeButton = DEF_FAS;
    
    public String getId() {
    
        return id;
    }

    
    public void setId(String id) {
    
        this.id = id;
    }

    public String getSessionid() {
    
        return sessionid;
    }
    
    public void setSessionid(String sessionid) {
    
        this.sessionid = sessionid;
    }
    
    public String getDescrizione() {
    
        return descrizione;
    }
    
    public void setDescrizione(String descrizione) {
    
        this.descrizione = descrizione;
    }
    
    public String getStato() {
    
        return stato;
    }
    
    public void setStato(String stato) {
    
        this.stato = stato;
    }
    
    public String getDatafine() {
    
        return datafine;
    }
    
    public void setDatafine(String datafine) {
    
        this.datafine = datafine;
    }
    
    public String getDafare() {
    
        return dafare;
    }
    
    public void setDafare(String dafare) {
    
        this.dafare = dafare;
    }
    
    public String getFatti() {
    
        return fatti;
    }
    
    public void setFatti(String fatti) {
    
        this.fatti = fatti;
    }
    
    public String getTotali() {
    
        return totali;
    }
    
    public void setTotali(String totali) {
    
        this.totali = totali;
    }
    
    public String getClassDisabledButton() {
    
        return classDisabledButton;
    }
    
    public void setClassDisabledButton(String classDisabledButton) {
    
        this.classDisabledButton = classDisabledButton;
    }
    
    public String getFontAwesomeButton() {
    
        return fontAwesomeButton;
    }
    
    public void setFontAwesomeButton(String fontAwesomeButton) {
    
        this.fontAwesomeButton = fontAwesomeButton;
    }

    public String getBoldStyle() {
    
        return boldStyle;
    }

    public void setBoldStyle(String boldStyle) {
    
        this.boldStyle = boldStyle;
    }
 
}
