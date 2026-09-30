package it.gruppoinit.pal.gp.core.domain.web;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeHelper;

public class IAttivitaCommand extends BaseCommand {

    private IAttivita entity;
    private Istanze istanza;
    private Alberoproc albero;
    private IAttivitaFilter attivitaFilter;
    private Boolean visstorico;
    private Responsabili responsabile;
    private IstanzeHelper istanzeHelper;
    //Flag utilizzati per decidere se far vedere o no lo storico 
    // delle attività per ogni singola sezione (Localizzazioni,orari etc..)
    private boolean flagStoricoLocalizzazioni;
    private boolean flagStoricoSoggettiCollegati;
    private boolean flagStoricoDettaglioInfo;
    private boolean flagStoricoOrari;
    private boolean flagStoricoAutorizzazioni;
    private boolean flagStoricoConcessioni;
    private boolean flagStoricoEndoprocedimenti;
    private boolean flagStoricoOneri;
    // Flag per raggruppamento istanze
    private boolean flagRaggruppaAutPerIstanza;
    // Flag per raggruppamento delle concessioni
    private boolean flagRaggruppaConcPerIstanza;
    // Utilizzati per recuperare la coppia di informazioni (codiceistanza,ordineistanza) per la funzionalità di aggiornamento
    // ordine istanza.
    private String codiceIstanze;
    private String ordineIstanze;
    private Date dataEsportazione;
    //utilizzato per passare la scheda da inserire alle attività
    // nella funzionalità di inserimento massivo delle schede per le attività
    private Dyn2Modellit dyn2Modellit;
    private Integer codiceRicerca;
    private Esportazioni esportazioni;
    private String codAndIdcomuneesportazioni;

    public IAttivitaCommand() {

	this.attivitaFilter = new IAttivitaFilter();
	this.albero = new Alberoproc();
	this.responsabile = new Responsabili();
	this.istanzeHelper = new IstanzeHelper();
	this.dyn2Modellit = new Dyn2Modellit();
	this.esportazioni = new Esportazioni();
    }

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    public Alberoproc getAlbero() {

	return albero;
    }

    public void setAlbero(Alberoproc albero) {

	this.albero = albero;
    }

    public IAttivita getEntity() {

	return entity;
    }

    public void setEntity(IAttivita entity) {

	this.entity = entity;
    }

    public IAttivitaFilter getAttivitaFilter() {

	return attivitaFilter;
    }

    public void setAttivitaFilter(IAttivitaFilter attivitaFilter) {

	this.attivitaFilter = attivitaFilter;
    }

    public Boolean getVisstorico() {

	return visstorico;
    }

    public void setVisstorico(Boolean visstorico) {

	this.visstorico = visstorico;
    }

    public Responsabili getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(Responsabili responsabile) {

	this.responsabile = responsabile;
    }

    public IstanzeHelper getIstanzeHelper() {

	return istanzeHelper;
    }

    public void setIstanzeHelper(IstanzeHelper istanzeHelper) {

	this.istanzeHelper = istanzeHelper;
    }

    public boolean getFlagStoricoLocalizzazioni() {

	return flagStoricoLocalizzazioni;
    }

    public void setFlagStoricoLocalizzazioni(boolean flagStoricoLocalizzazioni) {

	this.flagStoricoLocalizzazioni = flagStoricoLocalizzazioni;
    }

    public boolean getFlagStoricoSoggettiCollegati() {

	return flagStoricoSoggettiCollegati;
    }

    public void setFlagStoricoSoggettiCollegati(boolean flagStoricoSoggettiCollegati) {

	this.flagStoricoSoggettiCollegati = flagStoricoSoggettiCollegati;
    }

    public boolean getFlagStoricoDettaglioInfo() {

	return flagStoricoDettaglioInfo;
    }

    public void setFlagStoricoDettaglioInfo(boolean flagStoricoDettaglioInfo) {

	this.flagStoricoDettaglioInfo = flagStoricoDettaglioInfo;
    }

    public boolean getFlagStoricoOrari() {

	return flagStoricoOrari;
    }

    public void setFlagStoricoOrari(boolean flagStoricoOrari) {

	this.flagStoricoOrari = flagStoricoOrari;
    }

    public boolean getFlagStoricoAutorizzazioni() {

	return flagStoricoAutorizzazioni;
    }

    public void setFlagStoricoAutorizzazioni(boolean flagStoricoAutorizzazioni) {

	this.flagStoricoAutorizzazioni = flagStoricoAutorizzazioni;
    }

    public boolean getFlagStoricoConcessioni() {

	return flagStoricoConcessioni;
    }

    public void setFlagStoricoConcessioni(boolean flagStoricoConcessioni) {

	this.flagStoricoConcessioni = flagStoricoConcessioni;
    }

    public boolean getFlagStoricoEndoprocedimenti() {

	return flagStoricoEndoprocedimenti;
    }

    public void setFlagStoricoEndoprocedimenti(boolean flagStoricoEndoprocedimenti) {

	this.flagStoricoEndoprocedimenti = flagStoricoEndoprocedimenti;
    }

    public boolean getFlagStoricoOneri() {

	return flagStoricoOneri;
    }

    public void setFlagStoricoOneri(boolean flagStoricoOneri) {

	this.flagStoricoOneri = flagStoricoOneri;
    }

    public boolean getFlagRaggruppaAutPerIstanza() {

	return flagRaggruppaAutPerIstanza;
    }

    public void setFlagRaggruppaAutPerIstanza(boolean flagRaggruppaAutPerIstanza) {

	this.flagRaggruppaAutPerIstanza = flagRaggruppaAutPerIstanza;
    }

    public boolean getFlagRaggruppaConcPerIstanza() {

	return flagRaggruppaConcPerIstanza;
    }

    public void setFlagRaggruppaConcPerIstanza(boolean flagRaggruppaConcPerIstanza) {

	this.flagRaggruppaConcPerIstanza = flagRaggruppaConcPerIstanza;
    }

    public String getCodiceIstanze() {

	return codiceIstanze;
    }

    public void setCodiceIstanze(String codiceIstanze) {

	this.codiceIstanze = codiceIstanze;
    }

    public String getOrdineIstanze() {

	return ordineIstanze;
    }

    public void setOrdineIstanze(String ordineIstanze) {

	this.ordineIstanze = ordineIstanze;
    }

    public Date getDataEsportazione() {

	return dataEsportazione;
    }

    public void setDataEsportazione(Date dataEsportazione) {

	this.dataEsportazione = dataEsportazione;
    }

    public Dyn2Modellit getDyn2Modellit() {

	return dyn2Modellit;
    }

    public void setDyn2Modellit(Dyn2Modellit dyn2Modellit) {

	this.dyn2Modellit = dyn2Modellit;
    }

    public Integer getCodiceRicerca() {

	return codiceRicerca;
    }

    public void setCodiceRicerca(Integer codiceRicerca) {

	this.codiceRicerca = codiceRicerca;
    }

    public Esportazioni getEsportazioni() {

	return esportazioni;
    }

    public void setEsportazioni(Esportazioni esportazioni) {

	this.esportazioni = esportazioni;
    }

    public String getCodAndIdcomuneesportazioni() {

	return codAndIdcomuneesportazioni;
    }

    public void setCodAndIdcomuneesportazioni(String codAndIdcomuneesportazioni) {

	this.codAndIdcomuneesportazioni = codAndIdcomuneesportazioni;
    }
}
