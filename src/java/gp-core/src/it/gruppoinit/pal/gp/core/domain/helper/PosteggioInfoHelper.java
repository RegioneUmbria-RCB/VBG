package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PosteggioInfoHelper {

    /**
     * 
     * <pre>
     *     IDCOMUNE 
     *     IDPOSTEGGIO 
     *     CODICEPOSTEGGIO
     *     LARGHEZZA  
     *     LUNGHEZZA 
     *     SUPERFICIE 
     *     DISABILITATO 
     *     NOTE
     *     COORDINATE                      
     *     PESO                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      COORDINATE                      PESO STRADARIOPOSTEGGIO                                                                                                                                 
     *     TIPOSPAZIO 
     *     STRADARIOPOSTEGGIO 
     *     FKCODICEMERCATO
     *     SETTOREPOSTEGGIO
     * </pre>
     */
    private PkId id;
    private String codiceposteggio;
    private BigDecimal larghezza;
    private BigDecimal lunghezza;
    private BigDecimal superficie;
    private Boolean disabilitato;
    private String note;
    private String coordinate;
    private Integer peso;
    private String tipospazio;
    private String descrizioneStradario;
    private Integer cociceMercato;
    private Integer posizione;
    private String settoreposteggio;
    private List<MercatiDAvvisiHelper> mercatiDAvvisiHelper = new ArrayList<MercatiDAvvisiHelper>();

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public String getCodiceposteggio() {

	return codiceposteggio;
    }

    public void setCodiceposteggio(String codiceposteggio) {

	this.codiceposteggio = codiceposteggio;
    }

    public BigDecimal getLarghezza() {

	return larghezza;
    }

    public void setLarghezza(BigDecimal larghezza) {

	this.larghezza = larghezza;
    }

    public BigDecimal getLunghezza() {

	return lunghezza;
    }

    public void setLunghezza(BigDecimal lunghezza) {

	this.lunghezza = lunghezza;
    }

    public BigDecimal getSuperficie() {

	return superficie;
    }

    public void setSuperficie(BigDecimal superficie) {

	this.superficie = superficie;
    }

    public Boolean getDisabilitato() {

	return disabilitato;
    }

    public void setDisabilitato(Boolean disabilitato) {

	this.disabilitato = disabilitato;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public String getCoordinate() {

	return coordinate;
    }

    public void setCoordinate(String coordinate) {

	this.coordinate = coordinate;
    }

    public Integer getPeso() {

	return peso;
    }

    public void setPeso(Integer peso) {

	this.peso = peso;
    }

    public String getTipospazio() {

	return tipospazio;
    }

    public void setTipospazio(String tipospazio) {

	this.tipospazio = tipospazio;
    }

    public String getDescrizioneStradario() {

	return descrizioneStradario;
    }

    public void setDescrizioneStradario(String descrizioneStradario) {

	this.descrizioneStradario = descrizioneStradario;
    }

    public Integer getCociceMercato() {

	return cociceMercato;
    }

    public void setCociceMercato(Integer cociceMercato) {

	this.cociceMercato = cociceMercato;
    }

    public Integer getPosizione() {

	return posizione;
    }

    public void setPosizione(Integer posizione) {

	this.posizione = posizione;
    }

    public String getSettoreposteggio() {

	return settoreposteggio;
    }

    public void setSettoreposteggio(String settoreposteggio) {

	this.settoreposteggio = settoreposteggio;
    }

    public List<MercatiDAvvisiHelper> getMercatiDAvvisiHelper() {

	return mercatiDAvvisiHelper;
    }

    public void setMercatiDAvvisiHelper(List<MercatiDAvvisiHelper> mercatiDAvvisiHelper) {

	this.mercatiDAvvisiHelper = mercatiDAvvisiHelper;
    }
}
