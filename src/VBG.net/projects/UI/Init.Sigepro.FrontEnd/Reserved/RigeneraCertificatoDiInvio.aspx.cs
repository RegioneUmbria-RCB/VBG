using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneCertificatoInvio;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.STC;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class RigeneraCertificatoDiInvio : ReservedBasePage
	{
		private static class Constants
		{
			public const string ParametroIdDomanda = "IdPresentazione";
			public const string ParametroIdDomandaBackoffice = "IdDomandaBackoffice";
		}

		private int IdDomanda
		{
			get { return Convert.ToInt32(Request.QueryString[ Constants.ParametroIdDomanda ]); }
		}

		private string IdDomandaBackoffice
		{
			get { return Request.QueryString[ Constants.ParametroIdDomandaBackoffice ]; }
		}

		[Inject]
		protected CertificatoDiInvioService CertificatoDiInvioService { get; set; }
		[Inject]
		protected ISalvataggioDomandaStrategy SalvataggioDomandaStrategy { get; set; }
		[Inject]
		protected IStcService StcService { get; set; }


		protected void Page_Load(object sender, EventArgs e)
		{
			var domanda = SalvataggioDomandaStrategy.GetById( IdDomanda );

			if (!StcService.PraticaEsisteNelBackend(this.IdDomandaBackoffice))
				throw new Exception("La pratica " + this.IdDomandaBackoffice + " non esiste nel backend");

			var cert = this.CertificatoDiInvioService.GeneraCertificatoDiInvio(domanda, this.IdDomandaBackoffice);

			Response.Clear();
			Response.ContentType = "text/html";
			Response.AddHeader("content-disposition", "attachment;filename=riepilogodomanda.html");
			Response.BinaryWrite(cert.RawData);
			Response.End();
		}
	}
}