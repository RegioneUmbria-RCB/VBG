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

	public class FiltriVisuraControlProvider : IFiltriVisuraControlProvider
	{
		[Inject]
		public ICampiRicercaVisuraRepository _campiRicercaVisuraRepository { get; set; }

		public FiltriVisuraControlProvider()
		{
			FoKernelContainer.Inject(this);
		}

		#region IFiltriVisuraControlProvider Members

		public int IdCodiceIstanza { get { return 41; } }
		public int IdAnnoIstanza { get { return 43; } }
		public int IdMeseIstanza { get { return 44; } }
		public int IdOggetto { get { return 48; } }
		public int IdCivico { get { return 62; } }
		public int IdNumeroAutorizzazione { get { return 66; } }
		public int IdNumProtocollo { get { return 59; } }
		public int IdStradario { get { return 46; } }
		public int IdStatoIstanza { get { return 45; } }
		public int IdDataProtocollo { get { return 60; } }
		public int IdDatiCatasto { get { return 70; } }
		public int IdRichiedente { get { return 47; } }
		public int IdIntervento { get { return 42; } }

		
		public int ListaIdOperatore { get { return 18; } }
		public int ListaIdRichiedente { get { return 19; } }
		public int ListaIdTipoprocedura { get { return 21; } }
		public int ListaIdOggetto { get { return 22; } }
		public int ListaIdParticella { get { return 23; } }
		public int ListaIdSubalterno { get { return 32; } }
		public int ListaIdProgressivo { get { return 28; } }
		public int ListaIdLocalizzazione { get { return 25; } }
		public int ListaIdCodicearea { get { return 26; } }
		public int ListaIdFoglio { get { return 24; } }
		public int ListaIdNumeroistanza { get { return 27; } }
		public int ListaIdDatapresentazione { get { return 17; } }
		public int ListaIdTipointervento { get { return 20; } }
		public int ListaIdStato { get { return 31; } }
		public int ListaIdNumeroprotocollo { get { return 55; } }
		public int ListaIdDataprotocollo { get { return 56; } }
		public int ListaIdCivico { get { return 64; } }
		public int ListaIdTipocatasto { get { return 72; } }
        public int ListaIdRagioneSociale{ get { return 78; } }


		public CampoVisuraFrontoffice[] GetCampiFiltro(string idComune, string software)
		{
			return _campiRicercaVisuraRepository.GetFiltriVisuraFrontoffice(idComune, software);
		}


		public CampoVisuraFrontoffice[] GetCampiTabella(string idComune, string software)
		{
			return _campiRicercaVisuraRepository.GetCampiTabellaVisura(idComune, software);
		}

		#endregion
	}
}
