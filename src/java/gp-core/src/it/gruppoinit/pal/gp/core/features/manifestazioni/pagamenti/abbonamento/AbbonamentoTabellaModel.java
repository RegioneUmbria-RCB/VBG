package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.TipoEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;

public class AbbonamentoTabellaModel extends AbbonamentoModel {

    private String uuid;
    private String nominativo;
    private String stato;
    private Date dataCreazione;
    private BigDecimal creditoResiduo;
    private Integer idAnagrafe;
    private List<BorsellinoMovimentiLight> movimenti;
    private List<BorsellinoAutorizzazioniModel> autorizzazioni;
    private List<BorsellinoAutorizzazioniHistModel> autorizzazionihistory;

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	if (nominativo == null) {
	    this.nominativo = new String();
	}
	this.nominativo = nominativo;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public Date getDataCreazione() {

	return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {

	this.dataCreazione = dataCreazione;
    }

    public BigDecimal getCreditoResiduo() {

	return creditoResiduo;
    }

    public void setCreditoResiduo(BigDecimal creditoResiduo) {

	this.creditoResiduo = creditoResiduo;
    }

    public Integer getIdAnagrafe() {

	return idAnagrafe;
    }

    public void setIdAnagrafe(Integer idAnagrafe) {

	this.idAnagrafe = idAnagrafe;
    }

    public List<BorsellinoMovimentiLight> getMovimenti() {

	return movimenti;
    }

    public void setMovimenti(List<BorsellinoMovimentiLight> movimenti) {

	this.movimenti = movimenti;
    }

    public List<BorsellinoAutorizzazioniModel> getAutorizzazioni() {

	if (this.autorizzazioni == null) {
	    this.autorizzazioni = new ArrayList<BorsellinoAutorizzazioniModel>();
	}
	return autorizzazioni;
    }

    public void setAutorizzazioni(List<BorsellinoAutorizzazioniModel> autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }    
    
    public List<BorsellinoAutorizzazioniHistModel> getAutorizzazionihistory() {
    
        return autorizzazionihistory;
    }
    
    public void setAutorizzazionihistory(List<BorsellinoAutorizzazioniHistModel> autorizzazionihistory) {
    
        this.autorizzazionihistory = autorizzazionihistory;
    }

    public static AbbonamentoTabellaModel fromBorsellino(Borsellino borsellino) {

	if (borsellino == null || borsellino.getId() == null) {
	    return null;
	}
	AbbonamentoTabellaModel model = new AbbonamentoTabellaModel();
	model.setUuid(borsellino.getUuid());
	model.setId(borsellino.getId().getCodice());
	model.setDescrizione(borsellino.getDescrizione());
	model.setIdAnagrafe(borsellino.getAnagrafeID());
	model.setStato(borsellino.getStato());
	model.setNominativo(borsellino.getAnagrafe().getDescrizioneRichiedente());
	model.setDataCreazione(borsellino.getDataCreazione());
	BigDecimal credito = new BigDecimal(0);
	Set<BorsellinoMovimenti> borsellinoMovimenti = borsellino.getBorsellinoMovimenti();
	List<BorsellinoMovimentiLight> listaMovimenti = new ArrayList<BorsellinoMovimentiLight>();
	for (BorsellinoMovimenti bm : borsellinoMovimenti) {
	    if (verificaCreditoDelMovimento(bm)) {
		credito = credito.add(bm.getImporto());
	    }
	    listaMovimenti.add(BorsellinoMovimentiLight.fromBorsellinoMovimenti(bm));
	}
	Set<BorsellinoAutorizzazioni> auts = borsellino.getBorsellinoAutorizzazioni();
	for (BorsellinoAutorizzazioni ba : auts) {
	    model.getAutorizzazioni().add(BorsellinoAutorizzazioniModel.fromBorsellinoAutorizzazione(ba));
	}
	Collections.sort(listaMovimenti);
	model.setCreditoResiduo(credito);
	model.setMovimenti(listaMovimenti);
	return model;
    }

    public static boolean verificaCreditoDelMovimento(BorsellinoMovimenti bm) {

	//	System.out.println(
	//		bm.getTipo() + "\t" + new String(bm.getImporto().toString()).replace(".", ",") + "\t" + (bm.getDettPosizioneDebitoria() == null ? "" : bm.getDettPosizioneDebitoria().getStato()));
	if ((!bm.getTipo().equalsIgnoreCase(TipoEnum.RICARICA.name())) || bm.getDettPosizioneDebitoria() == null) {
	    return true; // non è una ricarica o la posizione è nulla (ricarica manuale)
	}
	return new StatiPosizioniDebitorieConverter().isStatoChiusoPositivamente(bm.getDettPosizioneDebitoria().getStato());
    }

    public static AbbonamentoTabellaModel fromAbbonamentoTabellaModelCompleta(AbbonamentoTabellaModelCompleta entity) {

	AbbonamentoTabellaModel model = new AbbonamentoTabellaModel();
	model.setUuid(entity.getUuid());
	model.setId(entity.getId());
	model.setDescrizione(entity.getDescrizione());
	model.setIdAnagrafe(entity.getIdAnagrafe());
	model.setDataCreazione(entity.getDataCreazione());
	model.setStato(entity.getStato());
	model.setCreditoResiduo(entity.getCreditoResiduo());
	String nominativo = settaNominativo(entity);
	model.setNominativo(nominativo);
	return model;
    }

    private static String settaNominativo(AbbonamentoTabellaModelCompleta entity) {

	String nominativo = "";
	if (entity.getTipoanagrafe() == null) {
	    return entity.getCognome();
	}
	String tNominativo = entity.getCognome() == null ? "" : entity.getCognome();
	if (entity.getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
	    nominativo = tNominativo + " " + (entity.getNome() == null ? "" : entity.getNome());
	    if (StringUtils.isNotBlank(entity.getCf())) {
		nominativo += " CF: " + entity.getCf();
	    }
	} else {
	    nominativo = tNominativo;
	    if (entity.getFormagiuridica() != null) {
		nominativo += " " + entity.getFormagiuridica();
	    }
	    nominativo += entity.getPiva() != null ? " P. Iva: " + entity.getPiva() : "";
	    nominativo += entity.getCf() != null ? " CF: " + entity.getCf() : "";
	}
	nominativo += entity.getTipoanagrafe() != null ? " [P." + entity.getTipoanagrafe() + ".]" : "";
	return nominativo;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
