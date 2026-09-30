package it.gruppoinit.pal.gp.core.features.sistema;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

@Service
public class VerticalizzazioneTipoInstallazioneServiceImpl implements IVerticalizzazioneTipoInstallazioneService {

    @Autowired
    private VerticalizzazioniService service;


    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(IVerticalizzazioneTipoInstallazioneService.NOME_VERTICALIZZAZIONE);
    }
    
    @Override
    public boolean isInstallazioneEnterprise() {

	return this.service.isInstallazioneEnterprise();
    }
    
    public boolean isPaginaEnterprise(String parametro) {
	
	if(!this.isAttiva() ) {
	    return true;
	}
	
	return this.service.isAttivaAndParametroEqualsToValore(IVerticalizzazioneTipoInstallazioneService.NOME_VERTICALIZZAZIONE, parametro, TecnologiaPaginaEnum.MICROSOFT.toString() );
    }

    @Override
    public Set<String> overrideMenuStandard() {

	if (!this.isAttiva()) {
	    this.ThrowExceptionVerticalizzazioneNonAttiva();
	}
	String valori = this.service.getString(IVerticalizzazioneTipoInstallazioneService.NOME_VERTICALIZZAZIONE,
		IVerticalizzazioneTipoInstallazioneService.PAR_OVERRIDE_MENU_STANDARD);
	if (StringUtils.isBlank(valori)) {
	    return new HashSet<String>();
	}
	return new HashSet<String>(Arrays.asList(valori.split(",")));
    }

    @Override
    public TecnologiaPaginaEnum paginaOneri() {

	String tipologia = this.service.getString(IVerticalizzazioneTipoInstallazioneService.NOME_VERTICALIZZAZIONE,
		IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_ONERI, TecnologiaPaginaEnum.MICROSOFT.toString());
	return TecnologiaPaginaEnum.fromValue(tipologia);
    }

    @Override
    public TecnologiaPaginaEnum paginaSchedeDinamiche() {

	String tipologia = this.service.getString(IVerticalizzazioneTipoInstallazioneService.NOME_VERTICALIZZAZIONE,
		IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_SCHEDEDINAMICHE, TecnologiaPaginaEnum.MICROSOFT.toString());
	return TecnologiaPaginaEnum.fromValue(tipologia);
    }

    @Override
    public TecnologiaPaginaEnum paginaStampe() {

	String tipologia = this.service.getString(IVerticalizzazioneTipoInstallazioneService.NOME_VERTICALIZZAZIONE,
		IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STAMPE, TecnologiaPaginaEnum.MICROSOFT.toString());
	return TecnologiaPaginaEnum.fromValue(tipologia);
    }

    @Override
    public TecnologiaPaginaEnum paginaStatistiche() {

	String tipologia = this.service.getString(IVerticalizzazioneTipoInstallazioneService.NOME_VERTICALIZZAZIONE,
		IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STATISTICHE, TecnologiaPaginaEnum.JAVA.toString());
	return TecnologiaPaginaEnum.fromValue(tipologia);
    }

    @Override
    public TecnologiaPaginaEnum paginaStampeDocTipo() {

	String tipologia = this.service.getString(IVerticalizzazioneTipoInstallazioneService.NOME_VERTICALIZZAZIONE,
		IVerticalizzazioneTipoInstallazioneService.PAR_STAMPE_DOCTIPO, TecnologiaPaginaEnum.JAVA.toString());
	return TecnologiaPaginaEnum.fromValue(tipologia);
    }

    @Override
    public TipoInstallazioneEnum tipo() {

	String tipologia = this.service.getString(IVerticalizzazioneTipoInstallazioneService.NOME_VERTICALIZZAZIONE,
		IVerticalizzazioneTipoInstallazioneService.PAR_TIPO, TipoInstallazioneEnum.ENTERPRISE.toString() );
	return TipoInstallazioneEnum.fromValue(tipologia);
    }

    private void ThrowExceptionVerticalizzazioneNonAttiva() {

	throw new IllegalArgumentException(
		"La verticalizzazione " + IVerticalizzazioneTipoInstallazioneService.NOME_VERTICALIZZAZIONE + " non è attiva.");
    }

}
