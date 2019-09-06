using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneEndoPresenti_Allegati : IstanzeStepPage
	{

		public class AllegatiEndoPresentiBindingItem
		{
			public int Id { get; set; }
			public string Descrizione { get; set; }
			public bool RichiedeFirmaDigitale { get; set; }
			public bool HaFile { get { return CodiceOggetto.HasValue; } }
			public string LinkDownloadFile { get; set; }
			public string NomeFile { get; set; }
			public string NumeroDocumento { get; set; }
			public int? CodiceOggetto { get; set; }
			public bool MostraBottoneFirma { get { return RichiedeFirmaDigitale && CodiceOggetto.HasValue && !FirmatoDigitalmente; } }
			public bool FirmatoDigitalmente { get; set; }
			public bool Obbligatorio { get; set; }
			public string RiferimentiDocumento { get { return GetRiferimenti(); } }
			public string TipoDocumento { get; set; }
			public string DataDocumento { get; set; }
			public string DocumentoRilasciatoDa { get; set; }

			private string GetRiferimenti()
			{
				var sb = new StringBuilder(TipoDocumento);

				if (!String.IsNullOrEmpty(NumeroDocumento))
					sb.AppendFormat(" numero {0} ", NumeroDocumento);

				if (!String.IsNullOrEmpty(DataDocumento))
					sb.AppendFormat(" del {0} ", DataDocumento);

				if (!String.IsNullOrEmpty(DocumentoRilasciatoDa))
					sb.AppendFormat(" rilasciato da {0} ", DocumentoRilasciatoDa);

				return sb.ToString();
			}
		}

		[Inject]
		public IEndoprocedimentiService _endoprocedimentiService { get; set; }
		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        public RedirectService _redirectService { get; set; }

        #region Parametri dello step

        public bool VerificaFirmeDigitaliAllegatiSeRichiesto
		{
			get { object o = this.ViewState["VerificaFirmeDigitaliAllegatiSeRichiesto"]; return o == null ? true : (bool)o; }
			set { this.ViewState["VerificaFirmeDigitaliAllegatiSeRichiesto"] = value; }
		}

		#endregion


		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public override bool CanEnterStep()
		{
			return ReadFacade
					.Domanda
					.Endoprocedimenti
					.Acquisiti
					.CheRichiedonoAllegato(this._endoprocedimentiService)
					.Count() > 0;
		}

		public override bool CanExitStep()
		{
			var filesRichiesti = ReadFacade
									.Domanda
									.Endoprocedimenti
									.Acquisiti
									.CheRichiedonoAllegato(this._endoprocedimentiService);


			var filesNonAllegati = filesRichiesti
										.ConAllegatoObbligatorio(this._endoprocedimentiService)
										.Where(x => x.Riferimenti.Allegato == null);

			var tuttiIFilesSonoAllegati = filesNonAllegati.Count() == 0;

			if (!tuttiIFilesSonoAllegati)
			{
				foreach (var endoSenzafile in filesNonAllegati)
				{
					Errori.Add(String.Format("Per poter proseguire è necessario allegare il file comprovante il possesso del titolo: {0}", endoSenzafile.Descrizione));
				}

				return false;
			}

			var filesSenzaFirma = filesRichiesti.Where(x => x.Riferimenti.Allegato != null && x.RichiedeFirmaDigitale(this._endoprocedimentiService) && !x.Riferimenti.Allegato.FirmatoDigitalmente);
				
			var tuttiIFilesSonoFirmati = filesSenzaFirma.Count() == 0;

			if (VerificaFirmeDigitaliAllegatiSeRichiesto && !tuttiIFilesSonoFirmati)
			{
				Errori.AddRange( filesSenzaFirma.Select( x => String.Format("Per poter proseguire è necessario firmare digitalmente il file \"{0}\"", x.Riferimenti.Allegato.NomeFile) ) );

				return false;
			}

			return true;
		}

		public override void DataBind()
		{
			var allegatiEndo = from r in ReadFacade.Domanda.Endoprocedimenti.Acquisiti.CheRichiedonoAllegato(this._endoprocedimentiService)
							   select new AllegatiEndoPresentiBindingItem
							   {
								   Id = r.Codice,
								   Descrizione = r.Descrizione,
								   RichiedeFirmaDigitale = VerificaFirmeDigitaliAllegatiSeRichiesto && r.RichiedeFirmaDigitale( this._endoprocedimentiService ),
								   Obbligatorio = r.HaAllegatoObbligatorio(this._endoprocedimentiService),
								   LinkDownloadFile = String.Empty,
								   NomeFile = r.Riferimenti.Allegato == null ? String.Empty : r.Riferimenti.Allegato.NomeFile,
								   CodiceOggetto = r.Riferimenti.Allegato == null ? (int?)null : r.Riferimenti.Allegato.CodiceOggetto,
								   FirmatoDigitalmente = r.Riferimenti.Allegato == null ? false : r.Riferimenti.Allegato.FirmatoDigitalmente,
								   TipoDocumento = r.Riferimenti.TipoTitolo.Descrizione,
								   DataDocumento = r.Riferimenti.DataAtto.HasValue ? r.Riferimenti.DataAtto.Value.ToString("dd/MM/yyyy") : String.Empty,
								   DocumentoRilasciatoDa = r.Riferimenti.RilasciatoDa,
								   NumeroDocumento = r.Riferimenti.NumeroAtto
							   };

			gvAllegati.DataSource = allegatiEndo;
			gvAllegati.DataBind();
		}

		protected void OnRowCommand(object sender, GridViewCommandEventArgs e)
		{
			if (e.CommandName == "Firma")
			{
				var codiceOggetto = Convert.ToInt32(e.CommandArgument);

				this._redirectService.ToFirmaDigitale(IdDomanda, codiceOggetto);
			}

		}

		protected void OnRowUpdating(object sender, GridViewUpdateEventArgs e)
		{
			try
			{
				var codiceInventario = Convert.ToInt32(e.Keys[0]);
				var postedFile = (FileUpload)gvAllegati.Rows[e.RowIndex].FindControl("EditPostedFile");
				var file = new BinaryFile(postedFile, this._validPostedFileSpecification);

				_endoprocedimentiService.AllegaFileAEndoPresente(IdDomanda, codiceInventario, file, false);

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add("Si è verificato un errore durante il caricamento del file: " + ex.Message);
			}
		}

		protected void OnRowDeleting(object sender, GridViewDeleteEventArgs e)
		{
			DataBind();

			try
			{
				var codiceInventario = Convert.ToInt32(e.Keys[e.RowIndex]);

				_endoprocedimentiService.RimuoviAllegatoDaEndo(IdDomanda, codiceInventario);

				DataBind();

			}
			catch (Exception ex)
			{
				Errori.Add("Si è verificato un errore durante la rimozione del file: " + ex.Message);
			}
		}
	}


	public static class EndoprocedimentiExtensions
	{
		public static IEnumerable<Endoprocedimento> CheRichiedonoAllegato(this IEnumerable<Endoprocedimento> listaEndo, IEndoprocedimentiService svc)
		{
			return listaEndo.Where(endo =>
			{
				var tipoTitolo = svc.GetTipoTitoloById(endo.Riferimenti.TipoTitolo.Codice);
				return tipoTitolo.Flags.RichiedeAllegato;
			});
		}

		public static IEnumerable<Endoprocedimento> ConAllegatoObbligatorio(this IEnumerable<Endoprocedimento> listaEndo, IEndoprocedimentiService svc)
		{
			return listaEndo.Where(endo =>
			{
				var tipoTitolo = svc.GetTipoTitoloById(endo.Riferimenti.TipoTitolo.Codice);
				return tipoTitolo.Flags.AllegatoObbligatorio;
			});
		}

		public static bool RichiedeFirmaDigitale(this Endoprocedimento endo, IEndoprocedimentiService svc)
		{
			var tipoTitolo = svc.GetTipoTitoloById(endo.Riferimenti.TipoTitolo.Codice);

			return tipoTitolo.Flags.VerificaFirmaAllegato;
		}

		public static bool HaAllegatoObbligatorio(this Endoprocedimento endo, IEndoprocedimentiService svc)
		{
			var tipoTitolo = svc.GetTipoTitoloById(endo.Riferimenti.TipoTitolo.Codice);

			return tipoTitolo.Flags.AllegatoObbligatorio;
		}

	}
}