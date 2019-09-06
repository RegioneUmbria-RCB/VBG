using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.STC;
using Ninject;
using System;

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
            Response.ContentType = cert.MimeType;
			Response.AddHeader("content-disposition", "attachment;filename=\"" + cert.FileName + "\"");
			Response.BinaryWrite(cert.FileContent);
			Response.End();
		}
	}
}