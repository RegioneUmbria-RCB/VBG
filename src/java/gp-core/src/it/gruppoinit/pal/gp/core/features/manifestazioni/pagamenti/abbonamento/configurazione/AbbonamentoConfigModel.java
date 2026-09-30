package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigurazione;

public class AbbonamentoConfigModel {

    private Integer id;
    private String tipoInstallazione;
    private String msgNodoPagNonDisp;
    private boolean attivoFo;
    private InformativaModel informativa;
    private RicaricaModel ricarica;
    private TreeSet<ButtonComuneModel> comuni;
    private String destinatari;
    private BigDecimal importomassimo;

    public Integer getId() {

	return id;
    }

    public String getTipoInstallazione() {

	return tipoInstallazione;
    }

    public void setTipoInstallazione(String tipoInstallazione) {

	this.tipoInstallazione = tipoInstallazione;
    }

    public String getMsgNodoPagNonDisp() {

	return msgNodoPagNonDisp;
    }

    public void setMsgNodoPagNonDisp(String msgNodoPagNonDisp) {

	this.msgNodoPagNonDisp = msgNodoPagNonDisp;
    }

    public boolean isAttivoFo() {

	return attivoFo;
    }

    public void setAttivoFo(boolean attivoFo) {

	this.attivoFo = attivoFo;
    }

    public InformativaModel getInformativa() {

	if (this.informativa == null) {
	    this.setInformativa(new InformativaModel());
	}
	return informativa;
    }

    public void setInformativa(InformativaModel informativa) {

	this.informativa = informativa;
    }

    public RicaricaModel getRicarica() {

	if (this.ricarica == null) {
	    this.setRicarica(new RicaricaModel());
	}
	return ricarica;
    }

    public void setRicarica(RicaricaModel ricarica) {

	this.ricarica = ricarica;
    }

    public TreeSet<ButtonComuneModel> getComuni() {

	return comuni;
    }

    public void setComuni(TreeSet<ButtonComuneModel> comuni) {

	this.comuni = comuni;
    }
            
    public String getDestinatari() {
    
        return destinatari;
    }

    
    public void setDestinatari(String destinatari) {
    
        this.destinatari = destinatari;
    }
    
    public BigDecimal getImportomassimo() {
        return importomassimo;
    }
    
    public void setImportomassimo(BigDecimal importomassimo) {
        this.importomassimo = importomassimo;
    }
    
    
    
    private String importomassimoStr;
    public String getImportomassimoStr() {
	if(importomassimo == null){
	    importomassimoStr = null;
	    return importomassimoStr;
	}		
	this.importomassimoStr = String.format(Locale.US, "%.2f", importomassimo);;
	return importomassimoStr;
    }
    public void setImportomassimoStr(String importomassimoStr) {
	if(StringUtils.isBlank(importomassimoStr)){
	    this.importomassimo = null;
	    this.importomassimoStr = null;
	    return;
	}
	
	this.importomassimo = new BigDecimal(importomassimoStr);
        this.importomassimoStr = importomassimoStr;
    }

    public static AbbonamentoConfigModel fromBorsellinoConfigurazione(BorsellinoConfigurazione cfg) {

	if (cfg == null || cfg.getId() == null || cfg.getId().getCodice() == null) {
	    return new AbbonamentoConfigModel();
	}
	AbbonamentoConfigModel model = new AbbonamentoConfigModel();
	model.id = cfg.getId().getCodice();
	model.tipoInstallazione = cfg.getTipoInstallazione();
	model.msgNodoPagNonDisp = cfg.getMsgNodoPagNonDisp();
	model.attivoFo = cfg.getGestioneFO();
	model.comuni = new TreeSet<ButtonComuneModel>();
	model.setDestinatari(cfg.getAttivaPagamenti() != null ? cfg.getAttivaPagamenti() : DestinatariEnum.TUTTI.name());
	model.setImportomassimo(cfg.getImportomassimo());
	return model;
    }
}
