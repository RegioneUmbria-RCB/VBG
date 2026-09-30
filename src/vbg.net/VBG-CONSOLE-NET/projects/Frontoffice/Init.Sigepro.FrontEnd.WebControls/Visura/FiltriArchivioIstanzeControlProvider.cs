using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.WebControls.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Ninject;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
	internal class FiltriArchivioIstanzeControlProvider : IFiltriVisuraControlProvider
	{
		[Inject]
		public ICampiRicercaVisuraRepository _campiRicercaVisuraRepository { get; set; }

		public FiltriArchivioIstanzeControlProvider()
		{
			FoKernelContainer.Inject(this);
		}

		#region IFiltriVisuraControlProvider Members

		public int IdCodiceIstanza { get { return 33; } }
		public int IdAnnoIstanza { get { return 35; } }
		public int IdMeseIstanza { get { return 36; } }
		public int IdOggetto { get { return 40; } }
		public int IdCivico { get { return 61; } }
		public int IdNumeroAutorizzazione { get { return 65; } }
		public int IdNumProtocollo { get { return 57; } }
		public int IdStradario { get { return 38; } }
		public int IdStatoIstanza { get { return 37; } }
		public int IdDataProtocollo { get { return 58; } }
		public int IdDatiCatasto { get { return 69; } }
		public int IdRichiedente { get { return 39; } }
		public int IdIntervento { get { return 34; } }

		public int ListaIdOperatore { get { return 2; } }
		public int ListaIdRichiedente { get { return 3; } }
		public int ListaIdTipoprocedura { get { return 5; } }
		public int ListaIdOggetto { get { return 6; } }
		public int ListaIdParticella { get { return 8; } }
		public int ListaIdSubalterno { get { return 9; } }
		public int ListaIdProgressivo { get { return 13; } }
		public int ListaIdLocalizzazione { get { return 16; } }
		public int ListaIdCodicearea { get { return 15; } }
		public int ListaIdFoglio { get { return 7; } }
		public int ListaIdNumeroistanza { get { return 14; } }
		public int ListaIdDatapresentazione { get { return 1; } }
		public int ListaIdTipointervento { get { return 4; } }
		public int ListaIdStato { get { return 10; } }
		public int ListaIdNumeroprotocollo { get { return 53; } }
		public int ListaIdDataprotocollo { get { return 54; } }
		public int ListaIdCivico { get { return 63; } }
		public int ListaIdTipocatasto { get { return 71; } }
        public int ListaIdRagioneSociale { get { return 78; } }

		public CampoVisuraFrontoffice[] GetCampiFiltro(string idComune, string software)
		{
			return _campiRicercaVisuraRepository.GetFiltriArchivioIstanzeFrontoffice(idComune, software);
		}

		public CampoVisuraFrontoffice[] GetCampiTabella(string idComune, string software)
		{
			return _campiRicercaVisuraRepository.GetCampiTabellaArchivioIstanze(idComune, software);
		}

		#endregion
	}
}
