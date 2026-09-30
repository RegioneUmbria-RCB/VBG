package it.gruppoinit.pal.gp.core.features.sistema;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

@Service
public class TipologiaFunzionalitaServiceImpl implements ITipologiaFunionalitaService {

    private IVerticalizzazioneTipoInstallazioneService verticalizzazioneTipoInstallazioneService;
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setVerticalizzazioneTipoInstallazioneService(IVerticalizzazioneTipoInstallazioneService verticalizzazioneTipoInstallazioneService) {

	this.verticalizzazioneTipoInstallazioneService = verticalizzazioneTipoInstallazioneService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    public TecnologiaPaginaEnum getTecnologiaPagina(String paginaDaVerificare) {

	if (!this.verticalizzazioneTipoInstallazioneService.isAttiva()
		&& paginaDaVerificare.equalsIgnoreCase(IVerticalizzazioneTipoInstallazioneService.PAR_STAMPE_DOCTIPO)) {
	    return TecnologiaPaginaEnum.JAVA;
	}
	if (!this.verticalizzazioneTipoInstallazioneService.isAttiva()) {
	    return TecnologiaPaginaEnum.MICROSOFT;
	}
	String valore = this.verticalizzazioniService.getString(IVerticalizzazioneTipoInstallazioneService.NOME_VERTICALIZZAZIONE,
		paginaDaVerificare);
	if (StringUtils.isBlank(valore)) {
	    return this.verticalizzazioneTipoInstallazioneService.tipo().equals(TipoInstallazioneEnum.ENTERPRISE) ? TecnologiaPaginaEnum.MICROSOFT
		    : TecnologiaPaginaEnum.JAVA;
	}
	if (paginaDaVerificare.equalsIgnoreCase(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_ONERI)) {
	    return this.verticalizzazioneTipoInstallazioneService.paginaOneri();
	}
	if (paginaDaVerificare.equalsIgnoreCase(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_SCHEDEDINAMICHE)) {
	    return this.verticalizzazioneTipoInstallazioneService.paginaSchedeDinamiche();
	}
	if (paginaDaVerificare.equalsIgnoreCase(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STAMPE)) {
	    return this.verticalizzazioneTipoInstallazioneService.paginaStampe();
	}
	if (paginaDaVerificare.equalsIgnoreCase(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STATISTICHE)) {
	    return this.verticalizzazioneTipoInstallazioneService.paginaStatistiche();
	}
	if (paginaDaVerificare.equalsIgnoreCase(IVerticalizzazioneTipoInstallazioneService.PAR_STAMPE_DOCTIPO)) {
	    return this.verticalizzazioneTipoInstallazioneService.paginaStampeDocTipo();
	}
	throw new IllegalArgumentException("Non è possibile determinare la tipologia della funzionalità " + paginaDaVerificare);
    }
}
