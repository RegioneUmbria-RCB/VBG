namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.EditOggetti
{
	using System;
	using System.Web;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiEndoprocedimenti;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
	using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
	using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
	using Ninject;

	/// <summary>
	/// Summary description for Upload
	/// </summary>
	public class Upload : Ninject.Web.HttpHandlerBase, IHttpHandler
	{
		[Inject]
		public AllegatiInterventoService _allegatiInterventoService { get; set; }
		[Inject]
		public AllegatiEndoprocedimentiService _allegatiEndoprocedimentiService { get; set; }
		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

		[Inject]
		public IOggettiService _oggettiService{ get; set; }

		private HttpContext _context;

		private int IdDomanda
		{
			get { return Convert.ToInt32(this._context.Request.QueryString[PathUtils.UrlParameters.IdPresentazione]); }
		}

		private int IdAllegato
		{
			get { return Convert.ToInt32(this._context.Request.QueryString[PathUtils.UrlParameters.IdAllegato]); }
		}

		private int? CodiceOggetto
		{
			get
			{
				var x = this._context.Request.QueryString[PathUtils.UrlParameters.CodiceOggetto];

				if (String.IsNullOrEmpty(x))
					return (int?)null;

				return Convert.ToInt32(x);
			}
		}

		private string TipoAllegato
		{
			get { return this._context.Request.QueryString[PathUtils.UrlParameters.TipoAllegato]; }
		}

		private string NomeFile
		{
			get { return this._context.Request.QueryString[PathUtils.UrlParameters.NomeFile]; }
		}

		protected override void DoProcessRequest(HttpContext context)
		{
			this._context = context;

			if (context.Request.Files.Count == 0)
			{
				throw new Exception("Non sono stati inviati files");
			}

			var file = new BinaryFile(context.Request.Files[0], this._validPostedFileSpecification);

			if (!string.IsNullOrEmpty(this.NomeFile))
			{
				file.FileName = this.NomeFile;
			}

			if (CodiceOggetto.HasValue)
			{
				this._oggettiService.AggiornaOggetto(CodiceOggetto.Value, file.FileContent);
			}
			else
			{
				if (this.TipoAllegato == PathUtils.UrlParametersValues.TipoAllegatoIntervento)
				{
					this.AggiungiAllegatoAIntervento(file);
				}

				if (this.TipoAllegato == PathUtils.UrlParametersValues.TipoAllegatoEndo)
				{
					this.AggiungiAllegatoAEndo(file);
				}
			}

			context.Response.ContentType = "text/plain";
			context.Response.Write("OK");
		}

		private void AggiungiAllegatoAEndo(BinaryFile file)
		{
			this._allegatiEndoprocedimentiService.AggiungiAllegatoAEndo(this.IdDomanda, this.IdAllegato, file);
		}

		private void AggiungiAllegatoAIntervento(BinaryFile file)
		{
			this._allegatiInterventoService.Salva(this.IdDomanda, this.IdAllegato, file);
		}

		public override bool IsReusable
		{
			get { return false; }
		}
	}
}