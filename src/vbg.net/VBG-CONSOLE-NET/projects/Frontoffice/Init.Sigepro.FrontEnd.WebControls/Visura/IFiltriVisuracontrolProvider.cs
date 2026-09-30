using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
	internal interface IFiltriVisuraControlProvider
	{
		int IdCodiceIstanza { get; }
		int IdAnnoIstanza { get; }
		int IdMeseIstanza { get; }
		int IdOggetto { get; }
		int IdCivico { get; }
		int IdNumeroAutorizzazione { get; }
		int IdNumProtocollo { get; }
		int IdStradario { get; }
		int IdStatoIstanza { get; }
		int IdDataProtocollo { get; }
		int IdDatiCatasto { get; }
		int IdRichiedente { get; }
		int IdIntervento { get; }

		int ListaIdOperatore { get; }
		int ListaIdRichiedente { get; }
		int ListaIdTipoprocedura { get; }
		int ListaIdOggetto { get; }
		int ListaIdParticella { get; }
		int ListaIdSubalterno { get; }
		int ListaIdProgressivo { get; }
		int ListaIdLocalizzazione { get; }
		int ListaIdCodicearea { get; }
		int ListaIdFoglio { get; }
		int ListaIdNumeroistanza { get; }
		int ListaIdDatapresentazione { get; }
		int ListaIdTipointervento { get; }
		int ListaIdStato { get; }
		int ListaIdNumeroprotocollo { get; }
		int ListaIdDataprotocollo { get; }
		int ListaIdCivico { get; }
		int ListaIdTipocatasto { get; }
        int ListaIdRagioneSociale { get; }

		CampoVisuraFrontoffice[] GetCampiFiltro(string idComune, string software);
		CampoVisuraFrontoffice[] GetCampiTabella(string idComune, string software);
	}
}
