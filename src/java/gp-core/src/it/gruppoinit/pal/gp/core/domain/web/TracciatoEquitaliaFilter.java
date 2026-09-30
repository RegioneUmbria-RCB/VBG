package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.EquitaliaTracciatiCfg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TracciatoEquitaliaFilter {

    private static final Integer NUMERO_MAX_ISTANZE = 100;
    private List<String> movimentisFatti = new ArrayList<String>();
    private String movimentoMessoARuolo;
    private Date istanzaDataDa;
    private Date istanzaDataA;
    private boolean flagFiltraSuDataAut;
    //. filtro utilizzato solo per fase di test
    private String codiciIstanza;
    private String codiceRegAutOrdinanza;
    private String dyn2CampiFiltroAmbito;
    private String valoredyn2CampiFiltroAmbito;
    private Integer numeroMaxIstanzeInPacchetto;

    public TracciatoEquitaliaFilter(EquitaliaTracciatiCfg equitaliaTracciatiCfg) {

	this.movimentisFatti.add(equitaliaTracciatiCfg.getCodiceMovAnnoDebito());
	this.movimentisFatti.add(equitaliaTracciatiCfg.getCodiceMovDataAtto());
	this.movimentisFatti.add(equitaliaTracciatiCfg.getCodiceMovDataNotificaAtto());
	this.movimentoMessoARuolo = equitaliaTracciatiCfg.getCodiceMovIstanzaARuolo();
	this.codiceRegAutOrdinanza = equitaliaTracciatiCfg.getCodiceRegAutOrdinanza();
	this.dyn2CampiFiltroAmbito = equitaliaTracciatiCfg.getDyn2CampiFiltroAmbito();
	this.numeroMaxIstanzeInPacchetto = NUMERO_MAX_ISTANZE;
    }

    public List<String> getMovimentisFatti() {

	return movimentisFatti;
    }

    public void setMovimentisFatti(List<String> movimentisFatti) {

	this.movimentisFatti = movimentisFatti;
    }

    public String getMovimentoMessoARuolo() {

	return movimentoMessoARuolo;
    }

    public void setMovimentoMessoARuolo(String movimentoMessoARuolo) {

	this.movimentoMessoARuolo = movimentoMessoARuolo;
    }

    public Date getIstanzaDataDa() {

	return istanzaDataDa;
    }

    public void setIstanzaDataDa(Date istanzaDataDa) {

	this.istanzaDataDa = istanzaDataDa;
    }

    public Date getIstanzaDataA() {

	return istanzaDataA;
    }

    public void setIstanzaDataA(Date istanzaDataA) {

	this.istanzaDataA = istanzaDataA;
    }

    public boolean getFlagFiltraSuDataAut() {

	return flagFiltraSuDataAut;
    }

    public void setFlagFiltraSuDataAut(boolean flagFiltraSuDataAut) {

	this.flagFiltraSuDataAut = flagFiltraSuDataAut;
    }

    public String getCodiciIstanza() {

	return codiciIstanza;
    }

    public void setCodiciIstanza(String codiciIstanza) {

	this.codiciIstanza = codiciIstanza;
    }

    public String getCodiceRegAutOrdinanza() {

	return codiceRegAutOrdinanza;
    }

    public void setCodiceRegAutOrdinanza(String codiceRegAutOrdinanza) {

	this.codiceRegAutOrdinanza = codiceRegAutOrdinanza;
    }

    public Integer getNumeroMaxIstanzeInPacchetto() {

	return numeroMaxIstanzeInPacchetto;
    }

    public void setNumeroMaxIstanzeInPacchetto(Integer numeroMaxIstanzeInPacchetto) {

	this.numeroMaxIstanzeInPacchetto = numeroMaxIstanzeInPacchetto;
    }

    public String getDyn2CampiFiltroAmbito() {

	return dyn2CampiFiltroAmbito;
    }

    public void setDyn2CampiFiltroAmbito(String dyn2CampiFiltroAmbito) {

	this.dyn2CampiFiltroAmbito = dyn2CampiFiltroAmbito;
    }

    public String getValoredyn2CampiFiltroAmbito() {

	return valoredyn2CampiFiltroAmbito;
    }

    public void setValoredyn2CampiFiltroAmbito(String valoredyn2CampiFiltroAmbito) {

	this.valoredyn2CampiFiltroAmbito = valoredyn2CampiFiltroAmbito;
    }
}
