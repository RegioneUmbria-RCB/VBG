using System;
using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Init.Sigepro.FrontEnd.WebControls;
using Ninject;



namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class MostraOggettoFoFirmato : Ninject.Web.PageBase, IIdComunePage
	{
		[Inject]
		public FileConverterService FileConverterService { get; set; }
		[Inject]
		public IDatiDomandaFoRepository _datiDomandaFoRepository { get; set; }
		[Inject]
		public IFirmaDigitaleMetadataService _firmaDigitaleService { get; set; }
		[Inject]
		public IAllegatiDomandaFoRepository _allegatiDomandaFoRepository { get; set; }


		int IdPresentazione
		{
			get
			{
				string idp = Request.QueryString["IdPresentazione"];
				return String.IsNullOrEmpty(idp) ? -1 : Convert.ToInt32(idp);
			}
		}

		public string Referrer
		{
			get { object o = this.ViewState["Referrer"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["Referrer"] = value; }
		}



		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				Referrer = Request.UrlReferrer.ToString();
				VerificaFirma();
			}
		}

		private void VerificaFirma()
		{
			string codiceOggetto = Request.QueryString["CodiceOggetto"];

			var oggetto = _allegatiDomandaFoRepository.LeggiAllegato(IdPresentazione, Convert.ToInt32(codiceOggetto)); 

			if (oggetto == null)
			{
				Response.ContentType = "text/plain";
				Response.Write("Errore: Oggetto " + codiceOggetto + "non trovato");
				Response.End();
				return;
			}

			var datiCert = _firmaDigitaleService.EstraiDatiFirma(oggetto);

			this.rptEsitiVerificaFirma.DataSource = datiCert.DettagliFirme;
			this.rptEsitiVerificaFirma.DataBind();
		}


		// Scarica il file firmato
		protected void lnkDownloadFile_Click(object sender, EventArgs e)
		{
			string codiceOggetto = Request.QueryString["CodiceOggetto"];


			var oggetto = _allegatiDomandaFoRepository.LeggiAllegato(IdPresentazione, Convert.ToInt32(codiceOggetto)); 

			Response.Clear();
			Response.ContentType = oggetto.MimeType;
			Response.AddHeader("content-disposition", "attachment;filename=\"" + oggetto.FileName + "\"");
			Response.BinaryWrite(oggetto.FileContent);
			Response.End();
		}

		// Scarica il file non firmato
		protected void lnkDownloadFileNoFirma_Click(object sender, EventArgs e)
		{
			string codiceOggetto = Request.QueryString["CodiceOggetto"];

			var oggetto = _allegatiDomandaFoRepository.LeggiAllegato(IdPresentazione, Convert.ToInt32(codiceOggetto)); 
			var fileInchiaro = _firmaDigitaleService.GetFileInChiaro(oggetto);

			Response.Clear();

			try
			{
				Response.ContentType = fileInchiaro.MimeType;
				Response.AddHeader("content-disposition", "attachment;filename=\"" + fileInchiaro.FileName + "\"");
				Response.BinaryWrite(fileInchiaro.FileContent);
			}
			catch (Exception ex)
			{
				Response.Write(ex.Message);
			}

			Response.End();

		}

		protected void cmdClose_Click(object sender, EventArgs e)
		{
			if (!String.IsNullOrEmpty(Referrer))
				Response.Redirect(Referrer);
			else
			{
				ClientScript.RegisterStartupScript(this.GetType(), "startup", "self.close();", true);
			}
		}

		#region IIdComunePage Members

		public string IdComune
		{
			get { return Master.IdComune; }
		}

		#endregion
	}
}
