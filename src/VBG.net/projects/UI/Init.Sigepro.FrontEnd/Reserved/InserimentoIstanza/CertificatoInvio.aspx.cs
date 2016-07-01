using System;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneCertificatoInvio;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;


namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class CertificatoInvio : ReservedBasePage
	{
		private static class Constants
		{
			public const int IdViewRiepilogo = 0;
			public const int IdViewnoriepilogo = 1;
		}


		[Inject]
		public CertificatoDiInvioService _certificatoDiInvioService { get; set; }
		[Inject]
		public IConfigurazione<ParametriAspetto> _parametriConfigurazione { get; set; }
		/// <summary>
		/// Id univoco dell'istanza nel backend
		/// </summary>
		string Id
		{
			get { return Request.QueryString["Id"]; }
		}


		/// <summary>
		/// Testo da visualizzare in testa al certificato di invio
		/// </summary>
		public string TestoFineSottoscrizione
		{
			get { object o = this.ViewState["CertificatoFineSottoscrizione"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["CertificatoFineSottoscrizione"] = value; }
		}

		public string UrlDownloadRiepilogo { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
			this.Title = "Certificato di invio";

			if (!IsPostBack)
				DataBind();
		}


		public override void DataBind()
		{
			ltrDescrizione.Text = this._parametriConfigurazione.Parametri.IntestazioneCertificatoInvio;

			var codiceOggetto = this._certificatoDiInvioService.GetCodiceOggettoCertificatoDiInvioDaIdDomandaBackoffice(Id);

			if (!codiceOggetto.HasValue)
			{
				Redirect("~/Reserved/DettaglioIstanzaV2.aspx", qs => qs.Add("Id" , Id));
				return;
			}

			this.UrlDownloadRiepilogo = GeneraUrlDownloadRiepilogo(codiceOggetto);
		}

		public string GeneraUrlDownloadRiepilogo(int? codiceOggetto)
		{
			if (!codiceOggetto.HasValue)
				return String.Empty;

			return ResolveClientUrl( String.Format("~/MostraOggetto.ashx?idcomune={0}&CodiceOggetto={1}", IdComune, codiceOggetto.Value));
		}

		protected void cmdDettagli_Click(object sender, EventArgs e)
		{
			Redirect("~/Reserved/DettaglioIstanzaEx.aspx", qs => qs.Add("Id", Id));
		}

		protected void cmdStampa_Click(object sender, EventArgs e)
		{
			Response.Clear();
			
			var fileCertificato = _certificatoDiInvioService.GetCertificatoDaIdDomandaBackoffice(Id);

			Response.ContentType = fileCertificato.MimeType;
			Response.AddHeader("content-disposition", "attachment;filename=\"CertificatoDiInvio.pdf\"");
			Response.BinaryWrite(fileCertificato.FileContent);
			Response.End();
		}
	}
}
