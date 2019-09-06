using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.DenunceTares;
using Ninject;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TaresBariDenunce
{
	public partial class VerificaEsistenzaContribuente : IstanzeStepPage
	{
		[Inject]
		protected DenunceTaresBariService _service { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{

		}

		public override bool CanEnterStep()
		{
			try
			{
				return !this._service.VerificaEsistenzaContribuente(IdDomanda, UserAuthenticationResult.DatiUtente);
			}
			catch (Exception ex)
			{
				this.Errori.Add(ex.Message);

				this.Master.MostraBottoneAvanti = false;

				return true;
			}
		}
	}
}