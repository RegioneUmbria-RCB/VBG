using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Bari.TARES;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class BenvenutoTares : ReservedBasePage
	{
		[Inject]
		public IConfigurazione<ParametriMenu> _configurazioneMenu { get; set; }

		[Inject]
		public BariTaresService _servizioTares { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				VerificaAppartenenzaACaf();

				DataBind();
			}
		}

		private void VerificaAppartenenzaACaf()
		{
			if (!_servizioTares.OperatoreAppartieneACaf(UserAuthenticationResult.DatiUtente.Codicefiscale))
				ErroreAccessoPagina.Mostra(IdComune, Software, ErroreAccessoPagina.TipoErroreEnum.AccessoNegato);
		}

		public override void DataBind()
		{
			this.Title = _configurazioneMenu.Parametri.TitoloPagina;
			this.descrizionePagina.Text = _configurazioneMenu.Parametri.DescrizionePagina;

			this.rptDescrizioneVociMenu.DataSource = _configurazioneMenu.Parametri.VociMenu.Where(x => !String.IsNullOrEmpty(x.DescrizioneEstesa));
			this.rptDescrizioneVociMenu.DataBind();
		}
	}
}