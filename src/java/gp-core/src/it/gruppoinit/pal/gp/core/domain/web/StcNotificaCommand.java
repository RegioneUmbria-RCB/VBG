package it.gruppoinit.pal.gp.core.domain.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.StcNotificaBean;
import it.init.sigepro.rte.types.DettaglioPraticaType;

public class StcNotificaCommand extends BaseCommand {

    /**
     * modalità inserimento dati
     */
    public static final int PRATICA_STORICA = 5;
    private boolean overridePraticaStorica = false;
    private boolean trovataPraticaCollegata = false;
    private DettaglioPraticaType praticaCollegata;
    private Integer codiceMovimento;
    private Integer codiceIstanza;
    private String stcIddocumento;
    private String stcIdallegato;
    private Movimenti movimentoPerRiferimentiProt;
    private List<Movimenti> listMovimentiRifProto = new ArrayList<Movimenti>();
    private boolean rifProto = false;
    private Boolean flagTrasmettiZipLogico;

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getStcIddocumento() {

	return stcIddocumento;
    }

    public void setStcIddocumento(String stcIddocumento) {

	this.stcIddocumento = stcIddocumento;
    }

    public String getStcIdallegato() {

	return stcIdallegato;
    }

    public void setStcIdallegato(String stcIdallegato) {

	this.stcIdallegato = stcIdallegato;
    }

    public StcNotificaCommand() {

	this.entity = new StcNotificaBean();
	this.movimentoPerRiferimentiProt = new Movimenti();
    }

    private StcNotificaBean entity;

    public void setEntity(StcNotificaBean entity) {

	this.entity = entity;
    }

    public StcNotificaBean getEntity() {

	return entity;
    }

    /**
     * Mappa da utilizzare nelle jsp con EL esempio <code>oggettoDiDominio.displayConstants.NEW</code>
     * 
     * @return la mappa con i valori delle costanti di diplay
     */
    public Map<String, Integer> getDisplayConstants() {

	Map<String, Integer> constants = super.getDisplayConstants();
	constants.put("PRATICA_STORICA", PRATICA_STORICA);
	return constants;
    }

    public boolean isOverridePraticaStorica() {

	return overridePraticaStorica;
    }

    public void setOverridePraticaStorica(boolean overridePraticaStorica) {

	this.overridePraticaStorica = overridePraticaStorica;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    public void setPraticaCollegata(DettaglioPraticaType praticaCollegata) {

	if (null != praticaCollegata) {
	    setTrovataPraticaCollegata(true);
	} else {
	    setTrovataPraticaCollegata(false);
	}
	this.praticaCollegata = praticaCollegata;
    }

    public DettaglioPraticaType getPraticaCollegata() {

	if (null != praticaCollegata) {
	    setTrovataPraticaCollegata(true);
	} else {
	    setTrovataPraticaCollegata(false);
	}
	return praticaCollegata;
    }

    private void setTrovataPraticaCollegata(boolean trovataPraticaCollegata) {

	this.trovataPraticaCollegata = trovataPraticaCollegata;
    }

    public boolean isTrovataPraticaCollegata() {

	return trovataPraticaCollegata;
    }

    public Movimenti getMovimentoPerRiferimentiProt() {

	return movimentoPerRiferimentiProt;
    }

    public void setMovimentoPerRiferimentiProt(Movimenti movimentoPerRiferimentiProt) {

	this.movimentoPerRiferimentiProt = movimentoPerRiferimentiProt;
    }

    public List<Movimenti> getListMovimentiRifProto() {

	return listMovimentiRifProto;
    }

    public void setListMovimentiRifProto(List<Movimenti> listMovimentiRifProto) {

	this.listMovimentiRifProto = listMovimentiRifProto;
    }

    public boolean isRifProto() {

	return rifProto;
    }

    public void setRifProto(boolean rifProto) {

	this.rifProto = rifProto;
    }

    public Boolean getFlagTrasmettiZipLogico() {

	return this.flagTrasmettiZipLogico;
    }

    public void setFlagTrasmettiZipLogico(Boolean flagTrasmettiZipLogico) {

	this.flagTrasmettiZipLogico = flagTrasmettiZipLogico;
    }
}
