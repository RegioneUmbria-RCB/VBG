package it.gruppoinit.pal.gp.core.features.sistema;

import java.util.Set;

public interface IVerticalizzazioneTipoInstallazioneService {
    
    static final String NOME_VERTICALIZZAZIONE = "TIPO_INSTALLAZIONE";
    static final String PAR_OVERRIDE_MENU_STANDARD = "OVERRIDE_MENU_STANDARD";
    static final String PAR_PAGINA_ONERI = "PAGINA_ONERI";
    static final String PAR_PAGINA_SCHEDEDINAMICHE = "PAGINA_SCHEDEDINAMICHE";
    static final String PAR_PAGINA_STAMPE = "PAGINA_STAMPE";
    static final String PAR_PAGINA_STATISTICHE = "PAGINA_STATISTICHE";
    static final String PAR_STAMPE_DOCTIPO = "STAMPE_DOCTIPO";
    static final String PAR_TIPO = "TIPO";

    boolean isAttiva();
    
    boolean isInstallazioneEnterprise();
    
    boolean isPaginaEnterprise(String parametro);
    
    Set<String> overrideMenuStandard();
    
    TecnologiaPaginaEnum paginaOneri();
    
    TecnologiaPaginaEnum paginaSchedeDinamiche();
    
    TecnologiaPaginaEnum paginaStampe();
    
    TecnologiaPaginaEnum paginaStatistiche();
    
    TecnologiaPaginaEnum paginaStampeDocTipo();
    
    TipoInstallazioneEnum tipo();
}
