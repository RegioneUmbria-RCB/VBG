package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class BlackListResultBean {
    
    private static NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.ITALY);
    
    
    private Integer idblacklistmotivi;
    private Integer iddettposizionedebitoria;
    private String iuv;
    private String descrizione;
    private String mercato;
    private Date data;
    private String nominativo;
    private String nome;
    private String codicefiscale;
    private BigDecimal importo;
    private Date dataInizioBlacklist;
    private Date dataAccertamento;
    private Date dataFineBl;
    
    
    public Integer getIdblacklistmotivi() {
    
        return idblacklistmotivi;
    }
    
    
    public void setIdblacklistmotivi(Integer idblacklistmotivi) {
    
        this.idblacklistmotivi = idblacklistmotivi;
    }

    
    public Integer getIddettposizionedebitoria() {
    
        return iddettposizionedebitoria;
    }

    
    public void setIddettposizionedebitoria(Integer iddettposizionedebitoria) {
    
        this.iddettposizionedebitoria = iddettposizionedebitoria;
    }

    public String getIuv() {
    
        return iuv;
    }
    
    public void setIuv(String iuv) {
    
        this.iuv = iuv;
    }
    
    public String getDescrizione() {
    
        return descrizione;
    }
    
    public void setDescrizione(String descrizione) {
    
        this.descrizione = descrizione;
    }
    
    public String getMercato() {
    
        return mercato;
    }
    
    public void setMercato(String mercato) {
    
        this.mercato = mercato;
    }
    
    public Date getData() {
    
        return data;
    }
    
    public void setData(Date data) {
    
        this.data = data;
    }
        
        
    public String getNominativo() {
    
        return nominativo;
    }


    
    public void setNominativo(String nominativo) {
    
        this.nominativo = nominativo;
    }


    
    public String getNome() {
    
        return nome;
    }


    
    public void setNome(String nome) {
    
        this.nome = nome;
    }


    
    public String getCodicefiscale() {
    
        return codicefiscale;
    }


    
    public void setCodicefiscale(String codicefiscale) {
    
        this.codicefiscale = codicefiscale;
    }


    public BigDecimal getImporto() {
    
        return importo;
    }
    
    public void setImporto(BigDecimal importo) {
    
        this.importo = importo;
    }
    
    public Date getDataInizioBlacklist() {
    
        return dataInizioBlacklist;
    }
    
    public void setDataInizioBlacklist(Date dataInizioBlacklist) {
    
        this.dataInizioBlacklist = dataInizioBlacklist;
    }            
    
    public Date getDataAccertamento() {
    
        return dataAccertamento;
    }
    
    public void setDataAccertamento(Date dataAccertamento) {
    
        this.dataAccertamento = dataAccertamento;
    }
    
    public Date getDataFineBl() {
    
        return dataFineBl;
    }
    
    public void setDataFineBl(Date dataFineBl) {
    
        this.dataFineBl = dataFineBl;
    }


    public String getDataStr() {
    
        return getStringByDate(data);
    }        

    
    public String getDataInizioBlacklistStr() {
    
	return getStringByDate(dataInizioBlacklist);
    }

   
    public String getImportoStr() {
    
        return importo != null ? formato.format(importo) : null;
    }

    public String getDataAccertamentoStr() {
	    
        return getStringByDate(dataAccertamento);
    }
    
    public String getDataFineBlStr(){
	return getStringByDate(dataFineBl);
    }

    public String getTitolare() {
    
	String titolare = nominativo;
	if(!StringUtils.isBlank(nome)){
	    titolare += " " + nome;
	}
	if(!StringUtils.isBlank(codicefiscale)){
	    titolare += " (" + codicefiscale + ")";
	}
        return titolare;
    }

    
    private static String getStringByDate(Date date) {
	if(date == null){
	    return null;
	}
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	return sdf.format(date);
    }
        
}
