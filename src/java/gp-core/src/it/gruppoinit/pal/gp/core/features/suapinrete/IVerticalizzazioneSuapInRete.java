package it.gruppoinit.pal.gp.core.features.suapinrete;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;

public interface IVerticalizzazioneSuapInRete {

    boolean isAttiva();

    String getAliasConsoleSol();

    Integer getAllineaSchede();

    boolean isAllineaSoloEndo();

    Amministrazioni getAmministrazione();

    String getCodClassInsielProc();

    String getCodProcGenerico();

    boolean isCollegaPraticheSpacchettate();

    boolean isCollegaSchedeIstanzeInAttivita();

    String getDescrizioneTipoClassificazione();

    boolean isElaboraDocumentiXML();

    String getNlaNodoSuapInRete();

    String getNomeFileComunicazioniSuap();

    String getNomeFileGeneraPratica();

    String getPrefissoCodicePraticaTelematica();

    String getPrefissoMappingPeople();

    boolean isSpacchettaPratica();

    String getSoftwareRecuperoSchedeConsole();

    String getTempistiche();

    String getUrlConsoleSol();

    String getUrlWsdlInsiel();

    String getWsInsielPassword();

    String getWsInsielUser();
}
