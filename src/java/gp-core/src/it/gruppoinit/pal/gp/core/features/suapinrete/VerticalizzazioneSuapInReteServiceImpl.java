package it.gruppoinit.pal.gp.core.features.suapinrete;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;

@Service
public class VerticalizzazioneSuapInReteServiceImpl implements IVerticalizzazioneSuapInRete {

    @Autowired
    private VerticalizzazioniService service;
    @Autowired
    private AmministrazioniService amministrazioniService;
    private static final String NOME_VERTICALIZZAZIONE = "FVG_SUAP_IN_RETE";
    private static final String PAR_ALIAS_CONSOLE_SOL = "ALIAS_CONSOLE_SOL";
    private static final String PAR_ALLINEA_SCHEDE = "ALLINEA_SCHEDE";
    private static final String PAR_ALLINEA_SOLO_ENDO = "ALLINEA_SOLO_ENDO";
    private static final String PAR_COD_AMMINISTRAZIONE = "COD_AMMINISTRAZIONE";
    private static final String PAR_COD_CLASS_INSIEL_PROC = "COD_CLASS_INSIEL_PROC";
    private static final String PAR_COD_PROC_GENERICO = "COD_PROC_GENERICO";
    private static final String PAR_COLLEGA_PRATICHE_SPACCHETTATE = "COLLEGA_PRATICHE_SPACCHETTATE";
    private static final String PAR_COLL_SCHEDE_ISTAN_IN_ATTIVITA = "COLL_SCHEDE_ISTAN_IN_ATTIVITA";
    private static final String PAR_DESC_TIPO_CLASSIFICAZIONE = "DESC_TIPO_CLASSIFICAZIONE";
    private static final String PAR_ELABORA_DOCUMENTI_XML = "ELABORA_DOCUMENTI_XML";
    private static final String PAR_NLA_NODO_SUAP_INRETE = "NLA_NODO_SUAP_INRETE";
    private static final String PAR_NOME_FILE_COMUNIC_SUAP = "NOME_FILE_COMUNIC_SUAP";
    private static final String PAR_NOME_FILE_GENERA_PRATICA = "NOME_FILE_GENERA_PRATICA";
    private static final String PAR_PREFISSO_COD_PRATICA_TEL = "PREFISSO_COD_PRATICA_TEL";
    private static final String PAR_PREFIX_MAPPING_PEOPLE = "PREFIX_MAPPING_PEOPLE";
    private static final String PAR_SPACCHETTA_PRATICA = "SPACCHETTA_PRATICA";
    private static final String PAR_SW_RECUPERO_SCHEDE_CONSOLE = "SW_RECUPERO_SCHEDE_CONSOLE";
    private static final String PAR_TEMPISTICHE = "TEMPISTICHE";
    private static final String PAR_URL_CONSOLE_SOL = "URL_CONSOLE_SOL";
    private static final String PAR_URL_WSDL_INSIEL = "URL_WSDL_INSIEL";
    private static final String PAR_WS_INSIEL_PASSWORD = "WS_INSIEL_PASSWORD";
    private static final String PAR_WS_INSIEL_USER = "WS_INSIEL_USER";

    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE);
    }

    @Override
    public String getAliasConsoleSol() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_ALIAS_CONSOLE_SOL);
    }

    @Override
    public Integer getAllineaSchede() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getInteger(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_ALLINEA_SCHEDE, 0);
    }

    @Override
    public boolean isAllineaSoloEndo() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_ALLINEA_SOLO_ENDO, "1", false);
    }

    @Override
    public Amministrazioni getAmministrazione() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	String idAmministrazione = this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_COD_AMMINISTRAZIONE);
	if (StringUtils.isEmpty(idAmministrazione)) {
	    return null;
	}
	return this.amministrazioniService.findById(new PkId(Integer.parseInt(idAmministrazione)));
    }

    @Override
    public String getCodClassInsielProc() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_COD_CLASS_INSIEL_PROC);
    }

    @Override
    public String getCodProcGenerico() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_COD_PROC_GENERICO);
    }

    @Override
    public boolean isCollegaPraticheSpacchettate() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_COLLEGA_PRATICHE_SPACCHETTATE, "1", false);
    }

    @Override
    public boolean isCollegaSchedeIstanzeInAttivita() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_COLL_SCHEDE_ISTAN_IN_ATTIVITA, "1", false);
    }

    @Override
    public String getDescrizioneTipoClassificazione() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_DESC_TIPO_CLASSIFICAZIONE);
    }

    @Override
    public boolean isElaboraDocumentiXML() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_ELABORA_DOCUMENTI_XML, "S", false);
    }

    @Override
    public String getNlaNodoSuapInRete() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_NLA_NODO_SUAP_INRETE);
    }

    @Override
    public String getNomeFileComunicazioniSuap() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_NOME_FILE_COMUNIC_SUAP);
    }

    @Override
    public String getNomeFileGeneraPratica() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_NOME_FILE_GENERA_PRATICA);
    }

    @Override
    public String getPrefissoCodicePraticaTelematica() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_PREFISSO_COD_PRATICA_TEL);
    }

    @Override
    public String getPrefissoMappingPeople() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_PREFIX_MAPPING_PEOPLE);
    }

    @Override
    public boolean isSpacchettaPratica() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_SPACCHETTA_PRATICA, "1", false);
    }

    @Override
    public String getSoftwareRecuperoSchedeConsole() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_SW_RECUPERO_SCHEDE_CONSOLE);
    }

    @Override
    public String getTempistiche() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_TEMPISTICHE);
    }

    @Override
    public String getUrlConsoleSol() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_URL_CONSOLE_SOL);
    }

    @Override
    public String getUrlWsdlInsiel() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_URL_WSDL_INSIEL);
    }

    @Override
    public String getWsInsielPassword() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_WS_INSIEL_PASSWORD);
    }

    @Override
    public String getWsInsielUser() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneSuapInReteServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneSuapInReteServiceImpl.PAR_WS_INSIEL_USER);
    }
}
