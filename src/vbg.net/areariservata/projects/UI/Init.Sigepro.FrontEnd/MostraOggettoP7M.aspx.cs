using System;
using System.IO;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;

namespace Init.Sigepro.FrontEnd
{
	public partial class MostraOggettoP7M : Ninject.Web.PageBase
	{
		[Inject]
		public IOggettiService _oggettiService { get; set; }
		[Inject]
		public FileConverterService _fileConverterService { get; set; }
		[Inject]
		public IFirmaDigitaleMetadataService _firmaDigitaleService { get; set; }
		[Inject]
		public IIstanzePresentateRepository _istanzePresentateRepository { get; set; }

		ILog _log = LogManager.GetLogger(typeof(MostraOggettoP7M));


		string IdComune
		{
			get
			{
				string idComune = Request.QueryString["idComune"];

				if (String.IsNullOrEmpty(idComune))
					throw new Exception("Id comune non valido");

				return idComune;
			}
		}

		string Software
		{
			get
			{
				string software = Request.QueryString["software"];

				if (FromStc && String.IsNullOrEmpty(software))
					throw new Exception("il parametro software è obbligatorio per le istanze di tipo STC");

				return software;
			}
		}

		bool FromStc
		{
			get
			{
				string fromstc = Request.QueryString["STC"];

				return !String.IsNullOrEmpty(fromstc);
			}
		}

		string CodiceOggetto
		{
			get { return Request.QueryString["CodiceOggetto"]; }
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				VerificaFirma();
		}

		private void VerificaFirma()
		{
			try
			{
				var oggetto = CaricaOggetto();

				if (oggetto == null)
					throw new Exception("Oggetto " + CodiceOggetto + "non trovato");

				var datiCert = _firmaDigitaleService.EstraiDatiFirma(oggetto);

				this.rptEsitiVerificaFirma.DataSource = datiCert.DettagliFirme;
				this.rptEsitiVerificaFirma.DataBind();
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in MostraOggettoP7M.VerificaFirma: {0}", ex.ToString());

				Response.ContentType = "text/plain";
				Response.Write("Si è verificato un errore durante il caricamento del documento. Se il problema persiste contattare il servizio di assistenza");
				Response.End();
			}
		}


		// Scarica il file firmato
		protected void lnkDownloadFile_Click(object sender, EventArgs e)
		{
			try
			{
				var oggetto = CaricaOggetto();

				Response.Clear();
				Response.AddHeader("content-disposition", "attachment;filename=\"" + oggetto.FileName + "\"");
				Response.BinaryWrite(oggetto.FileContent);
				Response.End();
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in MostraOggettoP7M.lnkDownloadFile_Click: {0}", ex.ToString());

				Response.ContentType = "text/plain";
				Response.Write("Si è verificato un errore durante il caricamento del documento. Se il problema persiste contattare il servizio di assistenza");
				Response.End();
			}
		}

		// Scarica il file non firmato
		protected void lnkDownloadFileNoFirma_Click(object sender, EventArgs e)
		{
			var oggetto = CaricaOggetto();
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
			ClientScript.RegisterStartupScript(this.GetType(), "startup", "self.close();", true);
		}

		private BinaryFile CaricaOggetto()
		{
			return FromStc ? CaricaOggettoSTC() : CaricaOggettoVbg();
			
		}

		private BinaryFile CaricaOggettoVbg()
		{
			return _oggettiService.GetById(Convert.ToInt32(CodiceOggetto));
		}

		private BinaryFile CaricaOggettoSTC()
		{
			return _istanzePresentateRepository.GetDocumentoPratica(IdComune, Software, CodiceOggetto);
		}
	}
}
